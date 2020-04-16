package com.soargtechnologies.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.soargtechnologies.myscope.R

class Splash_Activity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)
        Handler().postDelayed({
            navigateToActivity(Intent(applicationContext,Login_Page::class.java))
            finish()
        }, 2000)
    }
}