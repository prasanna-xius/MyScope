package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.activity_post_discharge__care__package.*
import kotlinx.android.synthetic.main.services_updated.*

class Post_discharge_Care_Package : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_discharge__care__package)

        btn_Post_discharge_care_package.setOnClickListener {

            checkbox_Post_discharge_care_package.setChecked(true)
            finish()
        }
    }
}
