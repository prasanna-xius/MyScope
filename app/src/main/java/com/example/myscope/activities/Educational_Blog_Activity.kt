package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import com.example.myscope.R
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*

class Educational_Blog_Activity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educational_blog_main)

        activitiesToolbar()
        header1!!.text = "Educational Blog"

        // Set the images from ImageAdapter.java to GridView
        val gridview = findViewById(R.id.educational_grid_view) as GridView
        gridview.adapter = Educational_Image_Adapter()


        // Listening to GridView item click
        gridview.onItemClickListener = OnItemClickListener { parent, v, position, id ->
            // Sending image id to FullScreenActivity
            val i = Intent(applicationContext, Image_Activity::class.java)
            // passing array index
            i.putExtra("id", position)
            startActivity(i)
        }


}

}
