package com.soargtechnologies.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import kotlinx.android.synthetic.main.covid_resourses_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1
import java.util.*
import kotlin.collections.ArrayList


class Covid_19_Resourses_Activity : BaseActivity(), View.OnClickListener {
    //    var personNames: ArrayList<*> = ArrayList(Arrays.asList("Person 1"))
    var covidImages: ArrayList<*> = ArrayList(Arrays.asList(R.drawable.carona_image))

    private val pdflist: MutableList<covid_pdflist> = ArrayList()
    private var mAdapter: CustomAdapter_Pdf? = null

    val pageUrl_Covidtracker = "https://www.covid19india.org/"

    val pageUrl_CovidMap = "https://coronavirus.jhu.edu/map.html"

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

        val recyclerView_pdf = findViewById(R.id.recycler_view_pdf) as RecyclerView
        mAdapter = CustomAdapter_Pdf(pdflist)
        recyclerView_pdf.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false) // set LayoutManager to RecyclerView

        recyclerView_pdf!!.itemAnimator = DefaultItemAnimator()
        recyclerView_pdf!!.adapter = mAdapter

        recyclerView_pdf!!.addOnItemTouchListener(RecyclerTouchListener(applicationContext, recyclerView_pdf!!, object : RecyclerTouchListener.ClickListener {
            override fun onClick(view: View?, position: Int) {
                val mIntent: Intent
                when (position) {
                    0 -> {
                        navigateToActivity(Intent(applicationContext, Covid_19_Update_Activity::class.java))

                    }
                    1 -> {
                        navigateToActivity(Intent(applicationContext, Covid_19_Guidelines_HomeQuarantine::class.java))

                    }
                    2 -> {
                        navigateToActivity(Intent(applicationContext, Covid_19_IFMSA_Library_Overview_Converted::class.java))

                    }
                    3 -> {
                        navigateToActivity(Intent(applicationContext, Covid_19_Who_China_Joint_Mission_Final_Reported_Activity::class.java))

                    }
                }
            }

            override fun onLongClick(view: View?, position: Int) {}


        }))
        pdffileslist()

    }

    private fun pdffileslist() {

        var pdf_file = covid_pdflist(R.drawable.covidview_pdf, "Covid-19 Update 12March")
        pdflist.add(pdf_file)
        pdf_file = covid_pdflist(R.drawable.covidview_pdf, "Guidelines for Home Quarantine")
        pdflist.add(pdf_file)
        pdf_file = covid_pdflist(R.drawable.covidview_pdf, "IFMSA Covid-19 Library Overview Converted")
        pdflist.add(pdf_file)
        pdf_file = covid_pdflist(R.drawable.covidview_pdf, "WHO China Joint Mission on Covid-19 final report")
        pdflist.add(pdf_file)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.covid19_tracker -> {
                val intent = Covid19_Tracker_WebActivity.newIntent(this, pageUrl_Covidtracker)

                startActivity(intent)
            }
            R.id.map_covid19 -> {
                val intent = Map_Covid19_WebActivity.newIntent1(this, pageUrl_CovidMap)

                startActivity(intent)
            }

        }
    }
}
