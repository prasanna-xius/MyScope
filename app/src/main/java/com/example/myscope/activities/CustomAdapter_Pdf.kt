package com.soargtechnologies.myscope.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import java.util.ArrayList

class CustomAdapter_Pdf(private val eventList: List<covid_pdflist>) : RecyclerView.Adapter<CustomAdapter_Pdf.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var covid_pdf: ImageView
        var covid_text: TextView

        init {

            covid_pdf = view.findViewById<View>(R.id.covid_pdf) as ImageView
            covid_text = view.findViewById<View>(R.id.covid_text) as TextView

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.covidpdf_main, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = eventList[position]
        holder.covid_pdf.setImageResource(event.pdf_icon)
        holder.covid_text.text = event.covid_text_data

    }

    override fun getItemCount(): Int {
        return eventList.size
    }


}
