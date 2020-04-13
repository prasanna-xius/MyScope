package com.example.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.services_updated.*

class Prescription_Audit : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        btn_prescription_audit.setOnClickListener {

            checkbox_prescription_audit.setChecked(true)
            finish()
        }
    }
}
