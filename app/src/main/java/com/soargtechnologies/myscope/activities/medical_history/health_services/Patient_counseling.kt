package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_patient_counseling.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.services_updated.*

class Patient_counseling : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_counseling)

        activitiesToolbar()
        header!!.text = "Patient Counseling"

    }
}
