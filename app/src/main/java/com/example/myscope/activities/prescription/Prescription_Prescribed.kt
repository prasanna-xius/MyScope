package com.example.myscope.activities.prescription

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.medical_history.Medical_History_HomePage
import kotlinx.android.synthetic.main.prescribed_main.*

class Prescription_Prescribed : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescribed_main)

        val isprescribedadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.is_prescribed))
//        is_prescribed!!.adapter = isprescribedadapter
//
//        is_prescribed?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent!!.getItemAtPosition(position).toString()
//
//                if (selectedItem.equals("None")) {
//                    doctor_layout.setVisibility(View.GONE);
//                    hosp_layout.setVisibility(View.GONE);
//                    pharmacy_layout.setVisibility(View.GONE);
//                    // do your stuff
//                } else if (selectedItem.equals("Over the counter (OTC)")) {
//                    doctor_layout.setVisibility(View.GONE);
//                    hosp_layout.setVisibility(View.GONE);
//                    pharmacy_layout.setVisibility(View.GONE);
//                    // do your stuff
//                } else if (selectedItem.equals("Prescribed")) {
//
//                    doctor_layout.setVisibility(View.VISIBLE);
//                    hosp_layout.setVisibility(View.VISIBLE);
//                    pharmacy_layout.setVisibility(View.GONE);
//
//                    // do your stuff
//                } else if (selectedItem.equals("Prescribed OTC")) {
//                    doctor_layout.setVisibility(View.GONE);
//                    hosp_layout.setVisibility(View.GONE);
//                    pharmacy_layout.setVisibility(View.VISIBLE);
//                    // do your stuff
//                }
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                doctor_layout.setVisibility(View.GONE);
//                hosp_layout.setVisibility(View.GONE);
//                pharmacy_layout.setVisibility(View.GONE);
//
//            }
//        }
//
//        saveBtn.setOnClickListener(View.OnClickListener {
//
//
//            navigateToActivity(Intent(applicationContext, Prescription_manual::class.java))
//
//        })
    }


}