package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_drug_interaction.*
import kotlinx.android.synthetic.main.activity_services_medical_history.*
import kotlinx.android.synthetic.main.app_bar_main.*

class Drug_interaction : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_interaction)

        activitiesToolbar()
        header!!.text = "Drug Interaction"

    }
}
