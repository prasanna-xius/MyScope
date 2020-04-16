package com.soargtechnologies.myscope.activities.prescription

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.activities.services.ServiceBuilder1
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_prescription_addimages_recyclerview.*
import kotlinx.android.synthetic.main.app_bar_main.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class Prescription_AddImage_PDF :BaseActivity() {

    var model_name: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_addimages_recyclerview)

        activitiesToolbar()

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        model_name = sharedpreferences!!.getString("model_name",null)!!
        header1!!.text = "Prescription-" + model_name
        val fab = findViewById<View>(R.id.fab_addimages) as FloatingActionButton
        fab.setOnClickListener {
            showUploadDialog()
        }
    }

    private fun showUploadDialog() {

        val pictureDialog = AlertDialog.Builder(this,R.style.Alert_Dialogue_Background)
        pictureDialog.setTitle("Select Action")

        val pictureDialogItems = arrayOf(
                "Select photo from gallery",
                "Capture photo from camera",
                "Select pdf file from folder"
        )
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {

                0-> choosephotofromgallery()
                1 -> choosephotofromcamera()
                2 -> selectfilechooser()


            }
        }
        pictureDialog.show()
    }

    private fun selectfilechooser() {


        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
    }

    private fun choosephotofromcamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)

    }

    private fun choosephotofromgallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)

    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED) {
             return
         }*/
        if (requestCode == GALLERY) {
            if (data != null) {
                val contentURI: Uri?
                contentURI = data.data
                try {
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(this@Prescription_AddImage_PDF, "Image Saved!", Toast.LENGTH_SHORT).show()
//                    iv!!.setImageBitmap(bitmap)
                    uploadImage(path)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@Prescription_AddImage_PDF, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?
//            iv!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@Prescription_AddImage_PDF, "Image Saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
        }
    }
    private fun uploadImage(path: String) {

        val imgname = Calendar.getInstance().timeInMillis.toString()
        val retrofit = Retrofit.Builder()
                .baseUrl(ServiceBuilder1.URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        //Create a file object using file path
        val file = File(path)
        // Parsing any Media type file
        val requestBody = RequestBody.create(MediaType.parse("*/*"), file)
        val fileToUpload = MultipartBody.Part.createFormData("filename", file.name, requestBody)
        val filename = RequestBody.create(MediaType.parse("text/plain"), imgname)

        val getResponse = retrofit.create(PrescriptionInterface::class.java)

        val call = getResponse.uploadImage(fileToUpload, filename)
        Log.d("assss", "asss")
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("mullllll", response.body().toString())
                try {
                    val jsonObject = JSONObject(response.body().toString())
                    Toast.makeText(applicationContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show()
                    jsonObject.toString().replace("\\\\", "")
                    if (jsonObject.getString("status") == "true") {
                        val dataArray = jsonObject.getJSONArray("data")
//                        var url = ""
                        for (i in 0 until dataArray.length()) {
                            val dataobj = dataArray.getJSONObject(i)
//                            url = dataobj.optString("pathToFile")
                        }
//                        Picasso.get().load(url).into(imageView)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("gttt", call.toString())
            }
        })
    }

    private fun saveImage(myBitmap: Bitmap?): String {
        val bytes = ByteArrayOutputStream()
        myBitmap!!.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        try {
            val f = File(wallpaperDirectory, Calendar.getInstance()
                    .timeInMillis.toString() + ".jpg")
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this, arrayOf(f.path), arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::---&gt;" + f.absolutePath)
            return f.absolutePath
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return ""
    }
    companion object {
        private const val IMAGE_DIRECTORY = "/myfiles"

    }
}
