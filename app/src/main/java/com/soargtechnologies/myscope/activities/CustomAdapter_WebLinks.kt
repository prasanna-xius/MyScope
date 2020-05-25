package com.soargtechnologies.myscope.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import kotlinx.android.synthetic.main.covidpdf_main.view.*

class CustomAdapter_WebLinks(private val covid_weblinklist: List<PrescriptionDataClass>) : RecyclerView.Adapter<CustomAdapter_WebLinks.ViewHolder>() {


    var id = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.covid_weblinks_layout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun getItemCount() = covid_weblinklist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.webLinkDetails = covid_weblinklist[position]
        val imgls = covid_weblinklist[position]
        id = imgls.education_websno


        holder.web_title.text = imgls.education_weblink.toString()
        holder.web_link.text = imgls.education_webtitle.toString()



        // implement setOnClickListener event on item view.
        holder.web_link.setOnClickListener { v: View? ->

            val context = v?.context


            val intent = Intent(context, Weblink_Activity::class.java)
            context!!.startActivity(intent)

        }
    }


    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        //
        val web_title: TextView = itemview.findViewById(R.id.title_web)
        val web_link: TextView = itemview.findViewById(R.id.covid19_link)
        var webLinkDetails: PrescriptionDataClass? = null

    }
}