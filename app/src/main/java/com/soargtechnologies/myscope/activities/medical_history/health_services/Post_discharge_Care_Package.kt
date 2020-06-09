package com.soargtechnologies.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.activity_post_discharge__care__package.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.services_updated.*

class Post_discharge_Care_Package : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_discharge__care__package)

        activitiesToolbar()
        header!!.text = "Post Discharge Care Package"
    }
}
