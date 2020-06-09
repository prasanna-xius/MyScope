package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_adverse__drug__event__monitoring.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.services_updated.*

class Adverse_Drug_Event_Monitoring : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adverse__drug__event__monitoring)

        activitiesToolbar()
        header!!.text = "Adverse Drug Event Monitoring"

    }
}
