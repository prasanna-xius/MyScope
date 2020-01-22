package com.example.myscope.activities

import android.app.Activity
import android.app.ActivityOptions
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.OperationApplicationException
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.RemoteException
import android.provider.ContactsContract
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


import com.example.myscope.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*

import java.io.File
import java.util.ArrayList

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

    //
    fun showLongSnackBar(message: String) {
        val snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        snackbar.show()
    }

    fun navigateToActivity(intent: Intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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

    fun errorDisplay(editText: EditText) {
        val dr = resources.getDrawable(R.drawable.error)
        dr.setBounds(0, 0, dr.intrinsicWidth, dr.intrinsicHeight)
        editText.setCompoundDrawables(null, null, dr, null)
    }

    fun errorRemove(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                errorDisplay(editText)
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editText.setCompoundDrawables(null, null, null, null)
            }
        })
    }

    fun spinnerSet(spinner: Spinner,array: Array<String>){
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    R.layout.spinner_dropdown_item,array)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                    AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    text1.setText(array[position])
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }


    companion object {

        private val TAG = BaseActivity::class.java.name
    }

}
