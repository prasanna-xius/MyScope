package com.soargtechnologies.myscope.activities.prescription

import android.Manifest.permission.CAMERA
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SymbolTable
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_allergy_list.*
import kotlinx.android.synthetic.main.activity_prescription_image_list.*
import kotlinx.android.synthetic.main.list_item_prescription_image.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Multipart
import java.io.*
import java.util.*

class Prescription_AddImage_PDF : AppCompatActivity() {
    var file: File? = null
    var uri: Uri? = null
    private var mImageUrl = ""
    var p_upload:MultipartBody.Part?=null
    //var byte:byte[]?= null
    internal var mobile_no = RequestBody.create(MediaType.parse("text/plain"), "8142529582")
    //    var mobile_no:String = "8142529582"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_image_list)
        //val toolbar = findViewById<View>(R.id.toolbar_imageuploader) as Toolbar
        //setSupportActionBar(toolbar)
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

        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)


        val requestCall = service.getImageDetails()

        requestCall.enqueue(object: Callback<List<PrescriptionDataClass>> {
            //PrescriptionInterface().getImageDetails().enqueue(object: Callback<List<PrescriptionDataClass>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<PrescriptionDataClass>>, response: Response<List<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val imageList = response.body()!!


                    val llm = LinearLayoutManager(applicationContext)
                    llm.orientation = LinearLayoutManager.VERTICAL
                    pres_recycler_view.setLayoutManager(llm)
                    pres_recycler_view.adapter = Prescription_ImageAdapter(imageList)
                    pres_recycler_view.adapter?.notifyDataSetChanged()


                    /// var  bytes :ByteArray = imageList.bytes()
                    //var bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    //iv_pres.setImageBitmap(bitmap)

                } else if(response.code() == 401) {
                    Toast.makeText(this@Prescription_AddImage_PDF,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@Prescription_AddImage_PDF, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<PrescriptionDataClass>>, t: Throwable) {

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
                0 -> choosephotofromgallery()
                1 -> choosephotofromcamera()
                2 -> selectfilechooser()
            }
        }
        pictureDialog.show()
    }

    private fun selectfilechooser() {

        val intent = Intent( Intent.ACTION_PICK,
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
//        intent.type = "image/jpeg"
//        intent.data = Uri.parse(mImageUrl)
        startActivityForResult(Intent, 100)
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
                    uploadImage(stream.toByteArray(),requestCode)
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
                    uploadImage(stream.toByteArray(),requestCode)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == 102) {
            if (resultCode == RESULT_OK) {
                val path:Uri = data?.data!!

                val iStream =   getContentResolver().openInputStream(path);

                uploadImage(getBytes(iStream!!),requestCode)
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

    private fun uploadImage(imageBytes: ByteArray,code:Int) {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        var date1 = day.toString() + "/" + (month + 1) + "/" + year
        var codevalue:Int = code
        val requestFile:RequestBody
        val body:MultipartBody.Part
        var date = RequestBody.create(MediaType.parse("text/plain"), date1)
        val destinationService = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        if(codevalue.equals(102))
        {
            requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), imageBytes)
            body = MultipartBody.Part.createFormData("p_upload", "example.pdf", requestFile)

        } else {
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
            body = MultipartBody.Part.createFormData("p_upload", "image.jpg", requestFile)

        }
        Toast.makeText(applicationContext,body.toString(),Toast.LENGTH_LONG).show()
        val call = destinationService.uploadImage(body, mobile_no,date)
        //mProgressBar!!.visibility = View.VISIBLE
        call.enqueue(object : Callback<PrescriptionDataClass> {
            override fun onResponse(call: Call<PrescriptionDataClass>, response: retrofit2.Response<PrescriptionDataClass>) {
                //mProgressBar!!.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    //mBtImageShow!!.visibility = View.VISIBLE
                    //mImageUrl = URL + responseBody!!.path
                    //Snackbar.make(findViewById(R.id.content), responseBody.message!!, Snackbar.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()
                    val gson = Gson()
                    try {
                        val errorResponse = gson.fromJson<PrescriptionDataClass>(errorBody!!.string(), Response::class.java!!)
                        //Snackbar.make(findViewById(R.id.content), errorResponse.message!!, Snackbar.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {
                //mProgressBar!!.visibility = View.GONE
                //Log.d(TAG, "onFailure: " + t.localizedMessage)
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
}