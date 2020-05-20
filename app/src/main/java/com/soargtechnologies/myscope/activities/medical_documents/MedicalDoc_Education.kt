package com.soargtechnologies.myscope.activities.medical_documents

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.prescription.ManagePermissions
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.helpers.Document_DischargeAdapter
import com.soargtechnologies.myscope.helpers.Document_EducationAdapter
import com.soargtechnologies.myscope.helpers.Prescription_ImageAdapter
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_medical_doc__discharge.*
import kotlinx.android.synthetic.main.activity_medical_doc__education.*
import kotlinx.android.synthetic.main.activity_prescription_image_list.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class MedicalDoc_Education: BaseActivity() {
    var file: File? = null
    private var mImageUrl = ""
    var p_uploadid: Int = 0
    var recyclerView: RecyclerView? = null
    var presAdapter: Prescription_ImageAdapter? = null
    lateinit var sharedpreferences: SharedPreferences
    var mobile_no:String = ""
    var model_name:String = ""
    private val PermissionsRequestCode = 123
    private lateinit var managePermissions: ManagePermissions
    var swipeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medical_doc__education)
        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        p_uploadid = sharedpreferences.getInt("educationid", 0)
        mobile_no = sharedpreferences!!.getString("mobile_no",null)!!

        recyclerView = findViewById(R.id.education_recycler_view)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView!!.layoutManager = layoutManager

        itemsswipetorefresh.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(this, R.color.colorPrimary))
        itemsswipetorefresh.setColorSchemeColors(Color.WHITE)
        itemsswipetorefresh.setOnRefreshListener {


            swipeCount += 1
            if (swipeCount >= 0) {
//                loadDestinations()
            }
            presAdapter!!.notifyDataSetChanged()



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
                R.layout.list_doc_education, // Custom view/ layout
                activity_pres, // Root layout to attach the view
                false)


        val fab = findViewById<View>(R.id.fab_educationdocs) as FloatingActionButton
        fab.setOnClickListener {
            //showUploadDialog()
            imagecall()
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {

        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)

        val requestCall = service.getEducationDetails("8142529582")

        requestCall.enqueue(object : Callback<MutableList<PrescriptionDataClass>> {
            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    // Your status code is in the range of 200's
                    val imageList = response.body()!!
                    val adapter= Document_EducationAdapter(imageList)
                    recyclerView!!.adapter = adapter
                    education_recycler_view.adapter?.notifyDataSetChanged()
                } else if (response.code() == 401) {
                    showLongToast("Your session has expired. Please Login again.")
                } else {
                    // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    showLongToast("Failed to retrieve items")
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<MutableList<PrescriptionDataClass>>, t: Throwable) {
                showLongToast("Error Occurred" + t.toString())
            }

        })
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
                    uploadImage(stream.toByteArray(), requestCode)
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
                    uploadImage(stream.toByteArray(), 101)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        if (requestCode == 102) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                val selectedPdfFromStorage : Uri = data?.data!!
                val `is` = contentResolver.openInputStream(selectedPdfFromStorage)!!.readBytes()
                uploadImage(`is`!!, 102)
            }
        }
    }

    private fun uploadImage(imageBytes: ByteArray, code: Int) {
        var codevalue: Int = code

        val requestFile: RequestBody
        val body: MultipartBody.Part
        var type: RequestBody
        var mobile = RequestBody.create(MediaType.parse("text/plain"), "8142529582")


        var date = RequestBody.create(MediaType.parse("text/plain"), datesetvalue())
        val destinationService = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        if (codevalue.equals(102)) {
            requestFile = RequestBody.create(MediaType.parse("application/pdf"),imageBytes)
            body = MultipartBody.Part.createFormData("document_education_file", "Rx", requestFile)
            type  = RequestBody.create(MediaType.parse("text/plain"), "pdf")

        } else {
            requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes)
            body = MultipartBody.Part.createFormData("document_education_file", "image.jpg", requestFile)
            type = RequestBody.create(MediaType.parse("text/plain"), "image")

        }
        val call = destinationService.uploaddocEducation( mobile,body, date,type)
        //mProgressBar!!.visibility = View.VISIBLE
        call.enqueue(object : Callback<PrescriptionDataClass> {
            override fun onResponse(call: Call<PrescriptionDataClass>, response: retrofit2.Response<PrescriptionDataClass>) {
                //mProgressBar!!.visibility = View.GONE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    showLongToast("image successful")
                } else {
                    val errorBody = response.errorBody()
                    val gson = Gson()
                    try {
//                        val errorResponse = gson.fromJson<PrescriptionDataClass>(errorBody!!.string(), Response::class.java!!)
                        showLongToast("image uploaded in else part")
                        //Snackbar.make(findViewById(R.id.content), errorResponse.message!!, Snackbar.LENGTH_SHORT).show()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {
                //mProgressBar!!.visibility = View.GONE
                //Log.d(TAG, "onFailure: " + t.localizedMessage)
                showLongToast("image uploaded in failure part")

            }
        })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionsRequestCode -> {
                val isPermissionsGranted = managePermissions
                        .processPermissionsResult(requestCode, permissions, grantResults)

                if (isPermissionsGranted) {
                    // Do the task now
                    showShortToast("Permissions granted.")
                } else {
                    showShortToast("Permissions denied.")
                }
                return
            }
        }
    }

}


