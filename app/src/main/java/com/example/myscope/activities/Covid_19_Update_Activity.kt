package com.example.myscope.activities

import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.pdf_view_main.*
import kotlinx.android.synthetic.main.pdf_view_main.header1

class Covid_19_Update_Activity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_main)

        activitiesToolbar()
        header1!!.text = "Covid-19 Update"

        pdfView.fromAsset("covid_19_update_12march.pdf").load()
    }

}
