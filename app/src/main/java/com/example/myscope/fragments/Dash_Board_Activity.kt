package com.example.myscope.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity

class Dash_Board_Activity : BaseActivity() {
    var headerTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_screen_main)
        showToolbar()
        setStatusBarTopColor()
        headerTextView = findViewById<View>(R.id.header) as TextView
        headerTextView!!.text = "DashBoard"
    }
}