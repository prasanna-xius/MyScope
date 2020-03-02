package com.example.myscope.activities
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myscope.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.regex.Pattern
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
import java.text.SimpleDateFormat
import java.time.Duration
import java.time.Month
import java.time.Year
import java.time.YearMonth
import java.util.*

open class BaseActivity : AppCompatActivity() {

    private val GALLERY = 1
    private val CAMERA = 2
    private val PICK_PDF_REQUEST = 3
    private var filePath: Uri? = null

    fun showShortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    fun showLongToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    fun setStatusBarTopColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.parseColor("#246ce5")
        }
    }
    fun showShortSnackBar(message: String) {
        val snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        snackbar.show()
    }
    fun showLongSnackBar(message: String) {
        val snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    fun navigateToActivity(intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent!!.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

        } else {
            startActivity(intent)
        }
    }
    fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 4
    }
    fun showError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }
    fun getBackButton(): ImageButton {
        return back_btn;
    }
    fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)

      
//        imgToolBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toolbarSearchDialog.dismiss();
//            }
//        });


    }
    fun showToolbar1() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
//        supportActionBar!!.setDisplayShowHomeEnabled(true)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        getBackButton().setOnClickListener {
            finish()
        }

//        imgToolBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toolbarSearchDialog.dismiss();
//            }
//        });


    }
    fun showBlackToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)

    }

    fun getRealPathFromURI(context: Context, contentUri: Uri): String {
        var cursor: Cursor? = null
        try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context.contentResolver.query(contentUri, proj, null, null, null)
            val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            return cursor.getString(column_index)
        } finally {
            cursor?.close()
        }
    }

    fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    /*Edit text error code */
    fun errorDisplay(editText: EditText) {
        val dr = resources.getDrawable(R.drawable.error)
        dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
        editText.setCompoundDrawables(null, null, dr, null)
    }

    /*Text view error code */
    fun errorDisplayTextview(textview: TextView) {
        val dr = resources.getDrawable(R.drawable.error)
        dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
        textview.setCompoundDrawables(null, null, dr, null)
    }
    /*Spinner error code */
     fun validateSpinner(spinnername: Spinner, spinnertext: String) {
        if(spinnertext.equals("None") || spinnertext.equals("") )
        {
            errorDisplayTextview(spinnername.text1)
        }
    }

    /*Edit text validation */
    fun validateInput(editText:EditText, editvalue:String) {
        if(editvalue.equals(""))
        {

            errorDisplay(editText)

        } else {
            editText.setCompoundDrawables(null, null, null, null)
        }
    }

    /* on item click listner of spinner */
    fun validate(spinnername: Spinner) {
        spinnername.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val spinnertext = parent.getItemAtPosition(position).toString()
               if(spinnertext.equals("None")) {
                   errorDisplayTextview(spinnername.text1)
                   showLongSnackBar("Please fill the required fields")
               }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }

        }
    }



    fun bmicalculator(weight: EditText,height: EditText,bmi: TextView)
    {
        if(!height.text.toString().equals("") && !weight.text.toString().equals("")) {
            val height= (height.text.toString()).toFloat()

            val square = height * height
            val weight = (weight.text.toString()).toFloat()

            val bmivalue = weight / square
            bmi.text = bmivalue.toString()

            if (bmivalue < 16) {
                showLongToast("Severely underweight")
            } else if (bmivalue < 18.5) {
                showLongToast("Severely underweight")
            } else if (bmivalue < 25) {
                showLongToast("Normal")
            } else if (bmivalue < 30) {
                showLongToast("Obese")
            }
        } else {
            errorDisplay(weight)
            errorDisplay(height)
            errorDisplayTextview(bmi)
        }

    }



    fun validateDate1( dob: TextView, today :TextView){
        if(!dob.text.toString().equals("") ){
            dob.setCompoundDrawables(null,null,null,null)
            val dob1 = SimpleDateFormat("yyyy").format(Date(dob.text.toString()))
//            val dob2 =  dob1.get(Calendar.YEAR)
            showLongToast(dob1.toString())
            val today1: Int = Calendar.getInstance().get(Calendar.YEAR)
            var age = today1 - dob1.toInt()
                today.text = age.toString()


            if (dob1.toInt() > today1)
            {
                showLongSnackBar("Date of birth should be less than today")
            }
        }
        else
        {
            errorDisplayTextview(dob)
            errorDisplayTextview(today)

        }
    }

    @SuppressLint("SetTextI18n")
    fun duration(startdate: TextView, stopdate :TextView, years : TextView){
        if(!startdate.text.toString().equals("") && !stopdate.text.toString().equals("")) {
            val dob1 = SimpleDateFormat("MM").format(Date(startdate.text.toString()))
            val dob2 = SimpleDateFormat("MM").format(Date(stopdate.text.toString()))
            val dob3 = SimpleDateFormat("YYYY").format(Date(startdate.text.toString()))
            val dob4 = SimpleDateFormat("YYYY").format(Date(stopdate.text.toString()))
            val dob5 = SimpleDateFormat("dd").format(Date(startdate.text.toString()))
            val dob6 = SimpleDateFormat("dd").format(Date(stopdate.text.toString()))

            if (dob3.toInt() < dob4.toInt()){

                  if (dob1.toInt() < dob2.toInt()){
                      val month = dob2.toInt() - dob1.toInt()
                      val year = dob4.toInt() - dob3.toInt()
                      years.text = month.toString() + "month  " + year + "Years"
                  }
                else if (dob1.toInt() == dob2.toInt()){
                      val month = dob4.toInt() - dob3.toInt()
                      years.text = month.toString() + "Years"
                  }
                  else if (dob1.toInt() > dob2.toInt()){

                      val x = 12 - dob1.toInt()
                      val month = x + dob2.toInt()
                      val year = dob4.toInt() - dob3.toInt() -1
                      years.text = month.toString() + "month  " + year + "Years"
                  }
            }
            else if (dob3.toInt() == dob4.toInt()){

                if (dob1.toInt() < dob2.toInt()){
                    val month = dob2.toInt() - dob1.toInt()
                    years.text = month.toString() + "months"
                }
                else if (dob1.toInt() == dob2.toInt()){
                    if (dob6.toInt() > dob5.toInt()) {
                        val days = dob6.toInt() - dob5.toInt()
                        years.text = days.toString() + "Days"
                    }
                    else{
                        years.setText("")
                        errorDisplayTextview(years)
                        showLongSnackBar("Start date cannot be after end date")

                    }

                }
                else if (dob1.toInt() > dob2.toInt()){
                    years.setText("")
                   errorDisplayTextview(years)
                    showLongSnackBar("Start date cannot be after end date")
                }
            }
            else if (dob3.toInt() > dob4.toInt()){
                years.setText("")
                errorDisplayTextview(years)
                showLongSnackBar("Start date cannot be after end date")
            }
        }
        }


   
    fun validateDate(startDate: TextView, stopDate: TextView, boolean: Any ): Boolean {
        if (!startDate.text.toString().equals("") && !stopDate.text.toString().equals("")) {
            startDate.setCompoundDrawables(null, null, null, null)
            stopDate.setCompoundDrawables(null, null, null, null)
            val startDate1 = SimpleDateFormat("MM-yyyy").format(Date(startDate.text.toString()))
            val endDate1 = SimpleDateFormat("MM-yyyy").format(Date(stopDate.text.toString()))
            if (startDate1 > endDate1) {
                // date in text view is current date
                stopDate.setText("")
                showLongSnackBar("Start date cannot be after end date")
                errorDisplayTextview(startDate)
                return false
            }
        } else {
            errorDisplayTextview(startDate)
            errorDisplayTextview(stopDate)
            return false

                return false;
           }
        
        return true
    }
    fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(this,R.style.Alert_Dialogue_Background)
        pictureDialog.setTitle("Select Action")

        val pictureDialogItems = arrayOf(
                "Select photo from gallery",
                "Capture photo from camera",
                "Select pdf file from folder",
                "Manual Entry")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
                2 -> showFileChooser()
                3 -> showFilemanual()

            }
        }
        pictureDialog.show()
    }

    private fun showFilemanual() {

        navigateToActivity(Intent(applicationContext, Prescription_manual::class.java))
    }

    private fun showFileChooser() {

//        DownloadTask(this@Image_Uploader3, URL)

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Pdf"), PICK_PDF_REQUEST)
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    private fun choosePhotoFromGallary() {
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
                    Toast.makeText(this@BaseActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
//                    iv!!.setImageBitmap(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this@BaseActivity, "Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (requestCode == CAMERA) {
            val thumbnail = data!!.extras!!["data"] as Bitmap?
//            iv!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(this@BaseActivity, "Image Saved!", Toast.LENGTH_SHORT).show()
        } else if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data
        }
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

//    fun requestMultiplePermissions() {
//        Dexter.withActivity(this)
//                .withPermissions(
//                        Manifest.permission.CAMERA,
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                .withListener(object : MultiplePermissionsListener {
//                    override fun onPermissionsChecked(report: MultiplePermissionsReport) { // check if all permissions are granted
//                        if (report.areAllPermissionsGranted()) {
//                            Toast.makeText(applicationContext, "All permissions are granted by user!", Toast.LENGTH_SHORT).show()
//                        }
//                        // check for permanent denial of any permission
//                        if (report.isAnyPermissionPermanentlyDenied) { // show alert dialog navigating to Settings
////openSettingsDialog();
//                        }
//                    }
//
//                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
//                        token.continuePermissionRequest()
//                    }
//                }).withErrorListener { Toast.makeText(applicationContext, "Some Error! ", Toast.LENGTH_SHORT).show() }
//                .onSameThread()
//                .check()
//    }

    val EMAIL_ADDRESS = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z]" +
                    "(" +
                    "\\." +
                    "[a-zA-Z]" +
                    ")+"
    )


    companion object {
        private val TAG = BaseActivity::class.java.name
        private const val IMAGE_DIRECTORY = "/myfiles"


    }


}
