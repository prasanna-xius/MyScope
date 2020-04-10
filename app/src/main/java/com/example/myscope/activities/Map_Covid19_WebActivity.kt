package com.example.myscope.activities

import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.covid19_map_mian.*
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1

class Map_Covid19_WebActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid19_map_mian)

        activitiesToolbar()
        header1!!.text = "COVID-19 GlobalMap"

        webView.loadUrl("https://coronavirus.jhu.edu/map.html")

    }

}
