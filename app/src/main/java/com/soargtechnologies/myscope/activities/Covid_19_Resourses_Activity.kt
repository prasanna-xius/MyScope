package com.soargtechnologies.myscope.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.helpers.Prescription_ImageAdapter
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_prescription_image_list.*
import kotlinx.android.synthetic.main.covid_resourses_main.*
import kotlinx.android.synthetic.main.educational_blog_main.header1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class Covid_19_Resourses_Activity : BaseActivity() {

    //    var personNames: ArrayList<*> = ArrayList(Arrays.asList("Person 1"))

    var covidImages: ArrayList<*> = ArrayList(Arrays.asList(R.drawable.carona_image))

    private val pdflist: MutableList<covid_pdflist> = ArrayList()
    private var mAdapter: CustomAdapter_Pdf? = null

    val pageUrl_Covidtracker = "https://www.covid19india.org/"

    val pageUrl_CovidMap = "https://coronavirus.jhu.edu/map.html"

    val pageUrl_Wiley_Library = "https://novel-coronavirus.onlinelibrary.wiley.com/"

    val pageUrl_Centers_Cdc = "https://www.cdc.gov/mmwr/Novel_Coronavirus_Reports.html"

    val pageUrl_NewEngland_Journal_Medicine = "https://www.nejm.org/coronavirus"

    val pageUrl_CovidJama_Network = "https://jamanetwork.com/journals/jama/pages/coronavirus-alert"

    val pageUrl_Covid_Lancet = "https://www.thelancet.com/coronavirus"

    val pageUrl_Covid_Cell = "https://www.cell.com/2019-nCOV"

    val pageUrl_Covid_British_Medical = "https://www.bmj.com/coronavirus"

    val pageUrl_Covid_Sciencemag = "https://www.sciencemag.org/collections/coronavirus"

    val pageUrl_Elsevier = "https://www.elsevier.com/connect/coronavirus-information-center"

    val pageUrl_Covid_Oxward_Acadamic = "https://academic.oup.com/journals/pages/coronavirus"

    val pageUrl_Covid_Nature = "https://www.nature.com/collections/hajgidghjb"

    var recyclerView_image: RecyclerView? = null
    var customAdapter_image: CustomAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_resourses_main)

        activitiesToolbar()
        header1!!.text = "COVID-19 Resourses"

//        covid19_tracker.setOnClickListener(this)
//        map_covid19.setOnClickListener(this)
//
//        wiley_library_covid19.setOnClickListener(this)
//        cdc_centers_covid19.setOnClickListener(this)
//        newengland_medicine_covid19.setOnClickListener(this)
//        jama_network_covid19.setOnClickListener(this)
//        lancet_covid19.setOnClickListener(this)
//        cell_covid19.setOnClickListener(this)
//        medical_journey_covid19.setOnClickListener(this)
//        science_covid19.setOnClickListener(this)
//        elsevier_network_covid19.setOnClickListener(this)
//        oxward_acadamics_covid19.setOnClickListener(this)
//        nature_covid19.setOnClickListener(this)

         recyclerView_image = findViewById(R.id.recycler_view_images) as RecyclerView

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_image!!.layoutManager = layoutManager




        loadImages()




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

    private fun loadImages() {

        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)


        val requestCall = service.getCovidImageDetails()

        requestCall.enqueue(object: Callback<MutableList<PrescriptionDataClass>> {

            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {


                    val imageList = response.body()!!
                    val adapter= CustomAdapter(imageList)
                    recyclerView_image!!.adapter = adapter
//                    pres_recycler_view.adapter?.notifyDataSetChanged()



                } else if(response.code() == 401) {
                    Toast.makeText(this@Covid_19_Resourses_Activity,
                            "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@Covid_19_Resourses_Activity, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<MutableList<PrescriptionDataClass>>, t: Throwable) {

                Toast.makeText(this@Covid_19_Resourses_Activity, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
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

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.covid19_tracker -> {
//                val intent = Covid19_Tracker_WebActivity.newIntent(this, pageUrl_Covidtracker)
//                startActivity(intent)
//            }
//            R.id.map_covid19 -> {
//                val intent = Map_Covid19_WebActivity.newIntent1(this, pageUrl_CovidMap)
//                startActivity(intent)
//            }
//            R.id.wiley_library_covid19 -> {
//                val intent = Willey_Library_Covid19_WebActivity.newIntent1(this, pageUrl_Wiley_Library)
//                startActivity(intent)
//            }
//            R.id.cdc_centers_covid19 -> {
//                val intent = CDC_Centers_Covid19_WebActivity.newIntent1(this, pageUrl_Centers_Cdc)
//                startActivity(intent)
//            }
//            R.id.newengland_medicine_covid19 -> {
//                val intent = Newzeland_Medicine_Covid19_WebActivity.newIntent1(this, pageUrl_NewEngland_Journal_Medicine)
//                startActivity(intent)
//            }
//            R.id.jama_network_covid19 -> {
//                val intent = Jama_Network_Covid19_WebActivity.newIntent1(this, pageUrl_CovidJama_Network)
//                startActivity(intent)
//            }
//            R.id.lancet_covid19 -> {
//                val intent = Lancet_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_Lancet)
//                startActivity(intent)
//            }
//            R.id.cell_covid19 -> {
//                val intent = Cell_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_Cell)
//                startActivity(intent)
//            }
//            R.id.medical_journey_covid19 -> {
//                val intent = Medical_Journey_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_British_Medical)
//                startActivity(intent)
//            }
//            R.id.science_covid19 -> {
//                val intent = Science_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_Sciencemag)
//                startActivity(intent)
//            }
//            R.id.elsevier_network_covid19 -> {
//                val intent = Elsevier_Network_Covid19_WebActivity.newIntent1(this, pageUrl_Elsevier)
//                startActivity(intent)
//            }
//            R.id.oxward_acadamics_covid19 -> {
//                val intent = Oxward_Acadamic_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_Oxward_Acadamic)
//                startActivity(intent)
//            }R.id.nature_covid19 -> {
//            val intent = Nature_Covid19_WebActivity.newIntent1(this, pageUrl_Covid_Nature)
//            startActivity(intent)
//        }

        }
//    }
//}
