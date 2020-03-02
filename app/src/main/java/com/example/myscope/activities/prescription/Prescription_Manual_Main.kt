package com.example.myscope.activities.prescription

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.prescribed_main.*
import java.util.*

class Prescription_Manual_Main : BaseActivity() {

    var nameTxt: EditText? = null
    var posTxt: EditText? = null
    var nameTxt1: EditText? = null
    var doctor_layout_user: LinearLayout? = null
    var hosp_layout_user: LinearLayout? = null
    var pharmacy_layout_user: LinearLayout? = null
    var is_prescribed_user: Spinner? = null

    var rv: RecyclerView? = null
    var adapter: MyAdapter? = null
    var players = ArrayList<Player>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescriptionmanual_recyclerview)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {

            navigateToActivity(Intent(applicationContext,Prescription_manual::class.java))

            //                Intent intent=new Intent(getApplicationContext(),Prescription_manual.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//            showDialog()
        }
        //recycler
        rv = findViewById<View>(R.id.myRecycler) as RecyclerView
        //SET ITS PROPS
        rv!!.layoutManager = LinearLayoutManager(this)
        rv!!.itemAnimator = DefaultItemAnimator()
        //ADAPTER
        adapter = MyAdapter(this, players)

        //        retrieve();
    }

//    private fun showDialog() {
//        val d = Dialog(this)
//        //NO TITLE
//        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        //layout of dialog
//        d.setContentView(R.layout.prescribed_main)
//
//        nameTxt = d.findViewById<View>(R.id.doctor_name) as EditText
//        posTxt = d.findViewById<View>(R.id.hosp_name) as EditText
//        nameTxt1 = d.findViewById<View>(R.id.pharmacy_name) as EditText
//
//        doctor_layout_user = d.findViewById<View>(R.id.doctor_layout) as LinearLayout
//        hosp_layout_user = d.findViewById<View>(R.id.hosp_layout) as LinearLayout
//        pharmacy_layout_user = d.findViewById<View>(R.id.pharmacy_layout) as LinearLayout
//
//        is_prescribed_user = d.findViewById<View>(R.id.is_prescribed) as Spinner
//
//        val savebtn = d.findViewById<View>(R.id.saveBtn) as Button
//        val retrieveBtn = d.findViewById<View>(R.id.retrieveBtn) as Button
//        val isprescribedadapter = ArrayAdapter(this,
//                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.is_prescribed))
//        is_prescribed_user!!.adapter = isprescribedadapter
//
//        is_prescribed_user?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent!!.getItemAtPosition(position).toString()
//
//                if (selectedItem.equals("None")) {
//                    doctor_layout_user!!.setVisibility(View.GONE);
//                    hosp_layout_user!!.setVisibility(View.GONE);
//                    pharmacy_layout_user!!.setVisibility(View.GONE);
//                    // do your stuff
//                } else if (selectedItem.equals("Over the counter (OTC)")) {
//                    doctor_layout_user!!.setVisibility(View.GONE);
//                    hosp_layout_user!!.setVisibility(View.GONE);
//                    pharmacy_layout_user!!.setVisibility(View.GONE);
//                    // do your stuff
//                } else if (selectedItem.equals("Prescribed")) {
//
//                    doctor_layout_user!!.setVisibility(View.VISIBLE);
//                    hosp_layout_user!!.setVisibility(View.VISIBLE);
//                    pharmacy_layout_user!!.setVisibility(View.GONE);
//
//                    // do your stuff
//                } else if (selectedItem.equals("Prescribed OTC")) {
//                    doctor_layout_user!!.setVisibility(View.GONE);
//                    hosp_layout_user!!.setVisibility(View.GONE);
//                    pharmacy_layout_user!!.setVisibility(View.VISIBLE);
//                    // do your stuff
//                }
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                doctor_layout_user!!.setVisibility(View.GONE);
//                hosp_layout_user!!.setVisibility(View.GONE);
//                pharmacy_layout_user!!.setVisibility(View.GONE);
//
//            }
//        }
//
//
////ONCLICK LISTENERS
//        savebtn.setOnClickListener {
//            save(nameTxt!!.text.toString(), posTxt!!.text.toString())
//        }
//        retrieveBtn.setOnClickListener {
//            retrieve();
//        }
//
////SHOW DIALOG
//        d.show()
//    }

//    //SAVE
//    private fun save(name: String, pos: String) {
//        val db = DBAdapter(this)
//        //OPEN
//        db.openDB()
//        //INSERT
//        val result = db.add(name, pos)
//        if (result > 0) {
//            nameTxt!!.setText("")
//            posTxt!!.setText("")
//        } else {
//            Snackbar.make(nameTxt!!, "Unable To Insert", Snackbar.LENGTH_SHORT).show()
//        }
//        //CLOSE
//        db.close()
//        //refresh
//        retrieve();
//    }

    //RETRIEVE
    private fun retrieve() {
        val db = DBAdapter(this)
        //OPEN
        db.openDB()
        players.clear()
        //SELECT
        val c = db.allPlayers
        //LOOP THRU THE DATA ADDING TO ARRAYLIST
        while (c.moveToNext()) {
            val id = c.getInt(0)
            val name = c.getString(1)
            val pos = c.getString(2)
            //CREATE PLAYER
            val p = Player(name, pos,null,null, id)
            //ADD TO PLAYERS
            players.add(p)
        }
        //SET ADAPTER TO RV
        if (players.size >= 1) {
            rv!!.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
                retrieve();
    }
}