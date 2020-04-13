package com.example.myscope.activities

import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*

class Courses_Training_Activity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comingsoon_main)

        activitiesToolbar()
        header!!.text = "Courses & Training"

    }

}
