package com.soargtechnologies.myscope.activities

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.educational_blog_main.header1
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Covid_19_Resourses_Activity : BaseActivity() {

    var recyclerView_image: RecyclerView? = null
    var recyclerView_pdf: RecyclerView? = null
    var recyclerView_link: RecyclerView? = null
    var customAdapter_image: CustomAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.covid_resourses_main)
        activitiesToolbar()
        header1!!.text = "COVID-19 Resourses"
        recyclerView_image = findViewById(R.id.recycler_view_images) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_image!!.layoutManager = layoutManager
        recyclerView_pdf = findViewById(R.id.recycler_view_pdf) as RecyclerView
        val layoutManager_pdf = LinearLayoutManager(this)
        layoutManager_pdf.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_pdf!!.layoutManager = layoutManager_pdf
        recyclerView_link = findViewById(R.id.recycler_view_weblinks) as RecyclerView
        val layoutManager_weblink = LinearLayoutManager(this)
        layoutManager_weblink.orientation = LinearLayoutManager.VERTICAL
        recyclerView_link!!.layoutManager = layoutManager_weblink
        loadlinks()
        loadImages()
        loadPdf()
    }
    private fun loadlinks() {
        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        val requestCall = service.getCovidWebLinkDetails()
        requestCall.enqueue(object : Callback<MutableList<PrescriptionDataClass>> {
            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    val covid_weblinklist = response.body()!!
                    val adapter = CustomAdapter_WebLinks(covid_weblinklist)
                    recyclerView_link!!.adapter = adapter
//                    pres_recycler_view.adapter?.notifyDataSetChanged()
                } else if (response.code() == 401) {
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
    private fun loadPdf() {
        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        val requestCall = service.getCovidPdfDetails()
        requestCall.enqueue(object : Callback<MutableList<PrescriptionDataClass>> {
            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    val covid_pdflist = response.body()!!
                    val adapter = CustomAdapter_Pdf(covid_pdflist)
                    recyclerView_pdf!!.adapter = adapter
//                    pres_recycler_view.adapter?.notifyDataSetChanged()
                } else if (response.code() == 401) {
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
    private fun loadImages() {
        val service = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        val requestCall = service.getCovidImageDetails()
        requestCall.enqueue(object : Callback<MutableList<PrescriptionDataClass>> {
            override fun onResponse(call: Call<MutableList<PrescriptionDataClass>>, response: Response<MutableList<PrescriptionDataClass>>) {
                if (response.isSuccessful()) {
                    val imageList = response.body()!!
                    val adapter = CustomAdapter(imageList)
                    recyclerView_image!!.adapter = adapter
//                    pres_recycler_view.adapter?.notifyDataSetChanged()
                } else if (response.code() == 401) {
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
}