package com.soargtechnologies.myscope.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.soargtechnologies.myscope.R

class Full_Image_Activity : BaseActivity() {
    var selectedImage: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.full_image_main)
        selectedImage = findViewById<View>(R.id.selectedImage) as ImageView // init a ImageView
        val intent = intent // get Intent which was set from adapter of Previous Activity
        selectedImage!!.setImageResource(intent.getIntExtra("image", 0)) // get image from Intent and set it in ImageView
    }
}