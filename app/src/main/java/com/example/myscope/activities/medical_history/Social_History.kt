package com.example.myscope.activities.medical_history

import android.os.Bundle

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.social_history.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*

class Social_History : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_history)

        showToolbar1()
        header!!.text = "Social History"


//        var spinnersmoking = findViewById<Spinner>(R.id.spinner_smoking)
//        val adapter = ArrayAdapter.createFromResource(this, R.array.smoking_arrays, R.layout.spinner_item)
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
//        spinnersmoking.setAdapter(adapter)

//        var spinnerdrinking = findViewById<Spinner>(R.id.spinner_drinking)
//        val adapter1 = ArrayAdapter.createFromResource(this, R.array.drinking_arrays, R.layout.spinner_item)
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
//        spinnerdrinking.setAdapter(adapter1)

        spinnerSet(spinner_smoking,resources.getStringArray(R.array.smoking_arrays))
        spinnerSet(spinner_drinking,resources.getStringArray(R.array.drinking_arrays))


        btn_social.setOnClickListener {

//            if (spinner_smoking.selectedItem.equals("Select status")){
//
//
//            }
//            else {
//                if (spinner_drinking.selectedItem.equals("Select status")) {
//
//                    Toast.makeText(applicationContext, "error2", Toast.LENGTH_LONG).show()
//                } else {
//
//                    intent= Intent(applicationContext,Diet::class.java)
//                    startActivity(intent)
//
//                }
//
//            }





        }
    }

}
