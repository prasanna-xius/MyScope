package com.soargtechnologies.myscope.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PDFopenfile
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import kotlinx.android.synthetic.main.covidpdf_main.view.*
import kotlinx.android.synthetic.main.educationimagerow.view.*
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import java.util.ArrayList

class CustomAdapter_Pdf(private val covid_pdflist: List<PrescriptionDataClass>) : RecyclerView.Adapter<CustomAdapter_Pdf.ViewHolder>() {


    var id = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.covidpdf_main, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }
    override fun getItemCount() = covid_pdflist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pdfDetails = covid_pdflist[position]
        val imgls = covid_pdflist[position]
        id = imgls.education_pdfsno

//        var encodedString: String = imgls.downloadpdf.toString()
//        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
//
//        var decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        holder.imageTiltle.text = imgls.education_pdftitle.toString()

        Glide.with(holder.itemView.context)
                .load(R.drawable.pdf)
                .into(holder.itemview.covid_pdf)
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener {v: View? ->

            val context = v?.context

            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode
            val editor: SharedPreferences.Editor = pref.edit()
            var s = covid_pdflist[position].downloadpdf
            editor.putString("buffer", s)
            editor.commit()
            val intent = Intent(context, PDFopenfile_Edu::class.java)
            context!!.startActivity(intent)

        }
    }


    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        //
        val imageTiltle: TextView = itemview.findViewById(R.id.covid_text)
        val imageView: ImageView = itemview.findViewById(R.id.covid_pdf)
        var pdfDetails: PrescriptionDataClass? = null

    }


}
