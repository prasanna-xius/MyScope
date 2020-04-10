package com.example.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import kotlinx.android.synthetic.main.covid_resourses_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1
import java.util.*


class Covid_19_Resourses_Activity : BaseActivity(), View.OnClickListener {
    //    var personNames: ArrayList<*> = ArrayList(Arrays.asList("Person 1"))
    var covidImages: ArrayList<*> = ArrayList(Arrays.asList(R.drawable.carona_image, R.drawable.carona_image, R.drawable.carona_image))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_resourses_main)

        activitiesToolbar()
        header1!!.text = "COVID-19 Resourses"

        covid19_tracker.setOnClickListener(this)
        map_covid19.setOnClickListener(this)
        val recyclerView_image = findViewById(R.id.recycler_view_images) as RecyclerView
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
//        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView_image.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false) // set LayoutManager to RecyclerView

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        val customAdapter_image = CustomAdapter(this@Covid_19_Resourses_Activity, covidImages)
        recyclerView_image.adapter = customAdapter_image // set the Adapter to RecyclerView

//
//        val recyclerView_pdf = findViewById(R.id.recycler_view_images) as RecyclerView
//        // set a GridLayoutManager with default vertical orientation and 2 number of columns
//        // set a GridLayoutManager with default vertical orientation and 2 number of columns
////        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
//        recyclerView_pdf.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false) // set LayoutManager to RecyclerView
//
//        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        //  call the constructor of CustomAdapter to send the reference and data to Adapter
//        val customAdapter_pdf = CustomAdapter_Pdf(this@Covid_19_Resourses_Activity, covidImages)
//        recyclerView_pdf.adapter = customAdapter_pdf // set the Adapter to RecyclerView


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.covid19_tracker -> {
                navigateToActivity(Intent(applicationContext, Covid19_Tracker_WebActivity::class.java))
            }
            R.id.map_covid19 -> {
                navigateToActivity(Intent(applicationContext, Map_Covid19_WebActivity::class.java))
            }

        }
    }
}
