package com.example.myscope.activities.prescription

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.PrescriptionInterface
import com.example.myscope.services.ImageApiService
import com.example.myscope.services.ServiceBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_navigation_blogs.*
import kotlinx.android.synthetic.main.activity_prescription_image_list.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*
import kotlin.collections.ArrayList

class Prescription_AddImage_PDF : AppCompatActivity() {
    var file: File? = null
    var uri: Uri? = null
    // var mobile_no:String?=null
    private var mImageUrl = ""
    var p_uploadid: Int = 0
    var recyclerView: RecyclerView? = null
    var imageAdapter: Prescription_ImageAdapter? = null
    lateinit var sharedpreferences: SharedPreferences

    var imglist: MutableList<PrescriptionDataClass>? = null
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    var p_upload: MultipartBody.Part? = null
    //var byte:byte[]?= null
    internal var mobile_no = RequestBody.create(MediaType.parse("text/plain"), "8142529582")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_image_list)
        //val toolbar = findViewById<View>(R.id.toolbar_imageuploader) as Toolbar
        //setSupportActionBar(toolbar)
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        p_uploadid = sharedpreferences.getInt("uploadid", 0)

        recyclerView = findViewById(R.id.pres_recycler_view)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        val list = listOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)

        val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)

        val view: View = layoutInflater.inflate(
                R.layout.list_item_prescription_image, // Custom view/ layout
                activity_pres, // Root layout to attach the view
                false)


        val fab = findViewById<View>(R.id.fab_addimages) as FloatingActionButton
        fab.setOnClickListener {
            //showUploadDialog()
            initViews()
        }
    }

    override fun onResume() {
        super.onResume()

        loadDestinations()

    }

    private fun loadDestinations() {

        val service = ServiceBuilder.buildService(ImageApiService::class.java)


        val requestCall = service.getImageDetails()

        requestCall.enqueue(object : Callback<MutableList<PrescriptionDataClass>> {
            //PrescriptionInterface().getImageDetails().enqueue(object: Callback<List<PrescriptionDataClass>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val imageList = response.body()!!


                    //val llm = LinearLayoutManager(applicationContext)
                    // llm.orientation = LinearLayoutManager.VERTICAL
                    // pres_recycler_view.setLayoutManager(llm)


                    val adapter = Prescription_ImageAdapter(imageList)
                    recyclerView!!.adapter = adapter

                    //pres_recycler_view.adapter = Prescription_ImageAdapter(imageList)


                    imageAdapter = Prescription_ImageAdapter(imageList)

                    pres_recycler_view.adapter?.notifyDataSetChanged()


                } else if (response.code() == 401) {
                    Toast.makeText(this@Prescription_AddImage_PDF,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@Prescription_AddImage_PDF, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<MutableList<PrescriptionDataClass>>, t: Throwable) {

                Toast.makeText(this@Prescription_AddImage_PDF, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun initViews() {
        val pictureDialog = AlertDialog.Builder(this, R.style.Alert_Dialogue_Background)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf(
                "Select photo from gallery",
                "Capture photo from camera",
                "Select pdf file from folder"
        )
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> {

                    choosephotofromgallery()
                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                     // managePermissions.checkPermissions()
                }


                1 -> {

                    choosephotofromcamera()
                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    //managePermissions.checkPermissions()
                }

                2 -> {

                    selectfilechooser()
                    //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    //managePermissions.checkPermissions()
                }
            }
        }
        pictureDialog.show()
    }

    private fun selectfilechooser() {

        val intent = Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), 102);


    }

    /*private fun selectfilechooser() {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
    }*/
    private fun choosephotofromcamera() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 101)

    }

    private fun choosephotofromgallery() {
        //val intent = Intent(Intent.ACTION_GET_CONTENT)
        val Intent = Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/jpeg"
        intent.data = Uri.parse(mImageUrl)
        startActivityForResult(Intent, 100, null)
        //startActivity(intent)
        //startActivity(intent)
        /*fun workingORnot(){
    }*/
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data!!)
                    val stream: ByteArrayOutputStream = ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

//                    val `is` = contentResolver.openInputStream(data?.data!!)
                    uploadImage(stream.toByteArray(), requestCode, null)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        if (requestCode == 101) {
            if (resultCode == Activity.RESULT_OK) {
                try {

                    val thumbnail = data!!.extras!!.get("data") as Bitmap
                    val stream: ByteArrayOutputStream = ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    uploadImage(stream.toByteArray(), 101, null)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {

                val uri = data?.getData();

                val uriString = uri.toString();
                val myFile = File(uriString);
                val `is` = contentResolver.openInputStream(data?.data!!)
//                uploadImage( outputStream.toByteArray(),102)
                uploadImage(getBytes(`is`!!), 102, uri)
//              val file = data!!.extras!!.get("data") as File
//      val fis: FileInputStream = FileInputStream(file);
//      val bos : ByteArrayOutputStream = ByteArrayOutputStream();
//                uploadImage(fis.readBytes())
            }
        }
    }

    @Throws(IOException::class)
    fun getBytes(`is`: InputStream): ByteArray {
        val byteBuff = ByteArrayOutputStream()
        val buffSize = 1024
        val buff = ByteArray(buffSize)
        var len = 0
        /*while ((len = `is`.read(buff)) != -1) {
        byteBuff.write(buff, 0, len)
    }*/
        while (`is`.read(buff).also { len = it } != -1) {
        }
        return byteBuff.toByteArray()
    }

    //    private fun uploadImage(imageBytes: ByteArray,code:Int) {
    private fun uploadImage(imageBytes: ByteArray, code: Int, uri: Uri?) {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        var date1 = day.toString() + "/" + (month + 1) + "/" + year
        var codevalue: Int = code

        val requestFile: RequestBody
        val body: MultipartBody.Part

//    val fis: FileInputStream = FileInputStream(myFile);
//      val bos : ByteArrayOutputStream = ByteArrayOutputStream();
        var date = RequestBody.create(MediaType.parse("text/plain"), date1)
        val destinationService = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        if (codevalue.equals(102)) {
            val uriString = uri.toString();
            val myFile = File(uriString);
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), (uriString))
            body = MultipartBody.Part.createFormData("p_upload", "example.pdf", requestFile)

        } else {
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
            body = MultipartBody.Part.createFormData("p_upload", "image.jpg", requestFile)

        }
        val call = destinationService.uploadImage(body, mobile_no, date)
        //mProgressBar!!.visibility = View.VISIBLE
        call.enqueue(object : Callback<PrescriptionDataClass> {
            override fun onResponse(call: Call<PrescriptionDataClass>, response: retrofit2.Response<PrescriptionDataClass>) {
                //mProgressBar!!.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    //mBtImageShow!!.visibility = View.VISIBLE
                    //mImageUrl = URL + responseBody!!.path
                    //Snackbar.make(findViewById(R.id.content), responseBody.message!!, Snackbar.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "image successful", Toast.LENGTH_LONG).show()

                } else {
                    val errorBody = response.errorBody()
                    val gson = Gson()
                    try {
//                        val errorResponse = gson.fromJson<PrescriptionDataClass>(errorBody!!.string(), Response::class.java!!)
                        Toast.makeText(applicationContext, "image uploaded in else part", Toast.LENGTH_LONG).show()
                        //Snackbar.make(findViewById(R.id.content), errorResponse.message!!, Snackbar.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {
                //mProgressBar!!.visibility = View.GONE
                //Log.d(TAG, "onFailure: " + t.localizedMessage)
                Toast.makeText(applicationContext, "image uploaded in failure part", Toast.LENGTH_LONG).show()

            }
        })
    }

    companion object {
        val TAG = Prescription_AddImage_PDF::class.java!!.getSimpleName()
        private val INTENT_REQUEST_CODE = 100
        //String mobile_no  = "8142529582";
        //public static final String URL = "http://10.0.2.2:8080";
        val URL = "http://10.0.2.2:8484/common/myscope/"
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                        .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    toast("Permissions granted.")
                } else {
                    toast("Permissions denied.")
                }
                return
            }
        }
    }


    // Extension function to show toast message
    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}