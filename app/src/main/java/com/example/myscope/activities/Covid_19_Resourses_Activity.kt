package com.example.myscope.activities

import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.educational_blog_main.*

class Covid_19_Resourses_Activity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_resourses_main)

        activitiesToolbar()
        header1!!.text = "COVID-19 Resourses"





    }

}
