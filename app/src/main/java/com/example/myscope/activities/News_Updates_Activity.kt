package com.soargtechnologies.myscope.activities

import android.os.Bundle
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*

class News_Updates_Activity :BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.comingsoon_main)

        activitiesToolbar()
        header!!.text = "News & Updates"

    }

}
