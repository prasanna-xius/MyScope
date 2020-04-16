package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.activity_patient_counseling.*
import kotlinx.android.synthetic.main.services_updated.*

class Patient_counseling : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_counseling)

        btn_patient_counseling.setOnClickListener {

            checkbox_patient_counselling.setChecked(true)
finish()
        }
    }
}
