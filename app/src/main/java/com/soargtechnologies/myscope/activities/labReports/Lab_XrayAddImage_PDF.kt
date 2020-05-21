package com.soargtechnologies.myscope.activities.labReports

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.prescription.ManagePermissions
import com.soargtechnologies.myscope.helpers.Lab_Blood_ImageAdapter
import com.soargtechnologies.myscope.helpers.Lab_Xray_ImageAdapter
import com.soargtechnologies.myscope.helpers.Prescription_ImageAdapter
import com.soargtechnologies.myscope.services.LabReportsService

import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_lab_blood_image_list.*
import kotlinx.android.synthetic.main.activity_lab_xray_image_list.*
import kotlinx.android.synthetic.main.activity_prescription_image_list.*
import kotlinx.android.synthetic.main.activity_prescription_image_list.itemsswipetorefresh
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.util.*

class Lab_XrayAddImage_PDF : BaseActivity() {
    var file: File? = null
    var uri: Uri? = null
    // var mobile_no:String?=null
    private var mImageUrl = ""
    var p_uploadid: Int = 0
    var recyclerView: RecyclerView? = null
    var labAdapter: Lab_Xray_ImageAdapter? = null
    lateinit var sharedpreferences: SharedPreferences
    var model_name: String? = null




    var imageList: MutableList<Lab_ReportDataClass>? = null
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    var p_upload: MultipartBody.Part? = null
    var swipeCount = 0
   // var   carItemList:List<PrescriptionDataClass>? = null
    //var byte:byte[]?= null
    internal var mobile_no = RequestBody.create(MediaType.parse("text/plain"), "8142529582")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab_xray_image_list)
        //val toolbar = findViewById<View>(R.id.toolbar_imageuploader) as Toolbar
        //setSupportActionBar(toolbar)

        activitiesToolbar()

        header!!.text = "Lab Reports"
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        p_uploadid = sharedpreferences.getInt("uploadid", 0)
       // model_name = sharedpreferences!!.getString("model_name",null)!!

        recyclerView = findViewById(R.id.lab_xray_recycler_view)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {


            swipeCount += 1
            if (swipeCount >= 0) {
                loadDestinations()
            }
            labAdapter!!.notifyDataSetChanged()



            itemsswipetorefresh.setRefreshing(false);

        }
        val list = listOf<String>(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
        )
        managePermissions = ManagePermissions(this, list, PermissionsRequestCode)

        val layoutInflater: LayoutInflater = LayoutInflater.from(applicationContext)

        val view: View = layoutInflater.inflate(
                R.layout.list_item_lab_image, // Custom view/ layout
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

        val service = ServiceBuilder.buildService(LabReportsService::class.java)


        val requestCall = service.getXrayDetails()

        requestCall.enqueue(object : Callback<MutableList<Lab_ReportDataClass>> {
            //PrescriptionInterface().getImageDetails().enqueue(object: Callback<List<PrescriptionDataClass>> {

            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<MutableList<Lab_ReportDataClass>>, response: Response<MutableList<Lab_ReportDataClass>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val imageList = response.body()!!


                    //val llm = LinearLayoutManager(applicationContext)
                    // llm.orientation = LinearLayoutManager.VERTICAL
                    // pres_recycler_view.setLayoutManager(llm)


                    labAdapter = Lab_Xray_ImageAdapter(imageList!!)
                    recyclerView!!.adapter = labAdapter

                    lab_xray_recycler_view.adapter?.notifyDataSetChanged()
                } else if (response.code() == 401) {
                    Toast.makeText(this@Lab_XrayAddImage_PDF,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else {
                    // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@Lab_XrayAddImage_PDF, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<MutableList<Lab_ReportDataClass>>, t: Throwable) {

                Toast.makeText(this@Lab_XrayAddImage_PDF, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
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


        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
        browseStorage.type = "application/pdf"
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(
                Intent.createChooser(browseStorage, "Select PDF"), 102
        )
    }

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
    }

    @SuppressLint("MissingSuperCall", "NewApi")
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
                val selectedPdfFromStorage :Uri = data?.data!!
                val `is` = contentResolver.openInputStream(selectedPdfFromStorage)!!.readBytes()
                uploadImage(`is`!!, 102, selectedPdfFromStorage)
            }
        }
    }

    private fun uploadImage(imageBytes: ByteArray, code: Int, uri: Uri?) {
        val c = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)
        var date1 = day.toString() + "/" + (month + 1) + "/" + year
        var codevalue: Int = code

        val requestFile: RequestBody
        val body: MultipartBody.Part
        var type:RequestBody

        var date = RequestBody.create(MediaType.parse("text/plain"), date1)
       // var model = RequestBody.create(MediaType.parse("text/plain"), model_name)
        val destinationService = ServiceBuilder.buildService(LabReportsService::class.java)
        if (codevalue.equals(102)) {
            requestFile = RequestBody.create(MediaType.parse("application/pdf"),imageBytes)
            body = MultipartBody.Part.createFormData("lab_xray_file", "Rx", requestFile)
            type  = RequestBody.create(MediaType.parse("text/plain"), "pdf")

        } else {
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
            body = MultipartBody.Part.createFormData("lab_xray_file", "image.jpg", requestFile)
            type = RequestBody.create(MediaType.parse("text/plain"), "image")

        }
        val call = destinationService.uploadXrayDetails(body, mobile_no, date,type)
        //mProgressBar!!.visibility = View.VISIBLE
        call.enqueue(object : Callback<Lab_ReportDataClass> {
            override fun onResponse(call: Call<Lab_ReportDataClass>, response: retrofit2.Response<Lab_ReportDataClass>) {
                //mProgressBar!!.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
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

            override fun onFailure(call: Call<Lab_ReportDataClass>, t: Throwable) {
                //mProgressBar!!.visibility = View.GONE
                //Log.d(TAG, "onFailure: " + t.localizedMessage)
                Toast.makeText(applicationContext, "image uploaded in failure part", Toast.LENGTH_LONG).show()

            }
        })
    }

    companion object {
        val TAG = Lab_XrayAddImage_PDF::class.java!!.getSimpleName()
        private val INTENT_REQUEST_CODE = 100
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