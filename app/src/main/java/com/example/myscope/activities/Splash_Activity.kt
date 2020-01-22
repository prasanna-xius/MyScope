package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.myscope.R

class Splash_Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_main)

        Handler().postDelayed({
            val i = Intent(this@Splash_Activity, Login_Page::class.java)
            startActivity(i)
            // close this activity
            finish()
        }, 2000)
    }
}