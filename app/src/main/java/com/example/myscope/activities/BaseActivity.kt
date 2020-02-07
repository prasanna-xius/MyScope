package com.example.myscope.activities
import android.app.ActivityOptions
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.OperationApplicationException
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.RemoteException
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.IntegerRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myscope.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_prescription_manual.*

import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import kotlinx.android.synthetic.main.view_userdetails_main.*
import java.text.SimpleDateFormat
import java.util.*

open class BaseActivity : AppCompatActivity() {

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
    fun showToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
    }
    fun showBlackToolbar() {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
    }
    fun getContactID(contactHelper: ContentResolver,
                     number: String): Long {
        val contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number))
        val projection = arrayOf(ContactsContract.PhoneLookup._ID)
        var cursor: Cursor? = null
        try {
            cursor = contactHelper.query(contactUri, projection, null, null, null)
            if (cursor!!.moveToFirst()) {
                val personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID)
                return cursor.getLong(personID)
            }
            return -1
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (cursor != null) {
                cursor.close()
                cursor = null
            }
        }
        return -1
    }
    fun deleteContact(contactHelper: ContentResolver, number: String): Boolean {
        val ops = ArrayList<ContentProviderOperation>()
        val args = arrayOf(getContactID(
                contactHelper, number).toString())
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build())
        try {
            contactHelper.applyBatch(ContactsContract.AUTHORITY, ops)
            return true
        } catch (e: RemoteException) {
            e.printStackTrace()
        } catch (e: OperationApplicationException) {
            e.printStackTrace()
        }
        return false
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
    //    public void lunchFragemnt(Fragment fragment){
    //        FragmentManager fragmentManager = getSupportFragmentManager();
    //        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    //        fragmentTransaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
    //        fragmentTransaction.commit();
    //    }
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
        if(spinnertext.equals("None"))
        {
            errorDisplayTextview(spinnername.text1)
        }
    }

    /*Edit text validation */
    fun validateInput(editText:EditText, editvalue:String) {
        if(editvalue.equals(""))
        {

            errorDisplay(editText)
            showLongSnackBar("Please fill the required fields")

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
    fun validateDate(startDate: TextView, stopDate: TextView, boolean: Boolean):Boolean {
        if (!startDate.text.toString().equals("") && !stopDate.text.toString().equals("")) {
            startDate.setCompoundDrawables(null, null, null, null)
            stopDate.setCompoundDrawables(null, null, null, null)
            val startDate1 = SimpleDateFormat("dd-MMM-yyyy").format(Date(startDate.text.toString()))
            val endDate1 = SimpleDateFormat("dd-MMM-yyyy").format(Date(stopDate.text.toString()))
            if (startDate1 > endDate1) {
                // date in text view is current date
                showLongSnackBar("Start date cannot be after end date")
                errorDisplayTextview(startDate)
                return false
            }
        } else {
            errorDisplayTextview(startDate)
            errorDisplayTextview(stopDate)
            return false
        }
        return true
    }

}