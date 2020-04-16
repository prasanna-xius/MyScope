package com.soargtechnologies.myscope.activities

import android.os.Bundle
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.pdf_view_main.*

class Covid_19_Guidelines_HomeQuarantine :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pdf_view_main)

        activitiesToolbar()
        header1!!.text = "Covid-19 Update"

        pdfView.fromAsset("guidelines_for_homequarantine.pdf").load()
    }
}
