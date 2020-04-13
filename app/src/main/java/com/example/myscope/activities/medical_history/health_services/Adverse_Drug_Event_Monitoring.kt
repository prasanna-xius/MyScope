package com.example.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.activity_adverse__drug__event__monitoring.*
import kotlinx.android.synthetic.main.services_updated.*

class Adverse_Drug_Event_Monitoring : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse__drug__event__monitoring)

        btn_adverse_drug_event_monitoring.setOnClickListener {

            checkbox_adverse_drug_event.setChecked(true)
            finish()
        }
    }
}
