package com.example.myscope.activities

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import com.example.myscope.R
import kotlinx.android.synthetic.main.covid_tracker_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1


class Covid19_Tracker_WebActivity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_tracker_main)

        activitiesToolbar()
        header1!!.text = "COVID-19 Tracker"
        val webSettings: WebSettings = webView1.getSettings()
        webSettings.javaScriptEnabled = true

        webView1.loadUrl("http://www.covid19india.org/")


    }

}
