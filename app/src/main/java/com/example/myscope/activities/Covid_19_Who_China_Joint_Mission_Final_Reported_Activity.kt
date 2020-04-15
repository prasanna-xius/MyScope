package com.example.myscope.activities

import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.pdf_view_main.*

class Covid_19_Who_China_Joint_Mission_Final_Reported_Activity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_main)

        activitiesToolbar()
        header1!!.text = "Covid-19 Update"

        pdfView.fromAsset("who_china_joint_mission_on_covid_19_final_report.pdf").load()
    }
}
