package com.example.myscope.activities.prescription

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myscope.R
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    var img: ImageView? = null

    var nameTxt: EditText? = null
    var posTxt: EditText? = null

    var updateBtn: Button? = null
    var deleteBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail1)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val i = intent
        val name = i.extras!!.getString("NAME")
        val pos = i.extras!!.getString("POSITION")
        val id = i.extras!!.getInt("ID")

//        updateBtn = findViewById<View>(R.id.prescription_update) as Button
//        deleteBtn = findViewById<View>(R.id.prescription_delete) as Button
        nameTxt = findViewById<View>(R.id.doctor_name1) as EditText
        posTxt = findViewById<View>(R.id.hosp_name1) as EditText

        //ASSIGN DATA TO THOSE VIEWS
        nameTxt!!.setText(name)
        posTxt!!.setText(pos)
        //update
//        updateBtn!!.setOnClickListener { update(id, nameTxt!!.text.toString(), posTxt!!.text.toString()) }
        //DELETE
//update
//        deleteBtn!!.setOnClickListener { delete(id) }
    }

    private fun update(id: Int, newName: String, newPos: String) {
        val db = DBAdapter(this)
        db.openDB()
        val result = db.UPDATE(id, newName, newPos,null,null)
        if (result > 0) {
            nameTxt!!.setText(newName)
            posTxt!!.setText(newPos)
            Snackbar.make(nameTxt!!, "Updated Sucesfully", Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(nameTxt!!, "Unable to Update", Snackbar.LENGTH_SHORT).show()
        }
        db.close()
    }

    //DELETE
    private fun delete(id: Int) {
        val db = DBAdapter(this)
        db.openDB()
        val result = db.Delete(id)
        if (result > 0) {
            finish()
        } else {
            Snackbar.make(nameTxt!!, "Unable to Update", Snackbar.LENGTH_SHORT).show()
        }
        db.close()
    }
}