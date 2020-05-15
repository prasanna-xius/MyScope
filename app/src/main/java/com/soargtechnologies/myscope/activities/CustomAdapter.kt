package com.soargtechnologies.myscope.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.activities.prescription.Prescription_ImageAdapter
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import kotlinx.android.synthetic.main.rowlayout.view.*
import java.util.*

class CustomAdapter(private val covidImages: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rowlayout, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }
    override fun getItemCount() = covidImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageDetails = covidImages[position]

        val imgls = covidImages[position]

        var encodedString: String = imgls.education_image.toString()

        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);
        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)
        val buf: ByteArray = decodedBytes
        val s: String = String(buf);

        val uri: Uri = Uri.parse(s)

        // set the data in items
//        holder.name.setText(personNames.get(position).toString());

//        holder.image.setImageResource(covidImages[position].hashCode())
        Glide.with(holder.itemView.context)
                .load(decodedBytes)
                .into(holder.itemview.image_covid)

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener {v: View? ->

            val context = v?.context
            val intent = Intent(Intent.ACTION_VIEW, decodedBytes)
            context!!.startActivity(intent)

//            // open another activity on item click
//            val intent = Intent(context, Full_Image_Activity::class.java)
//            intent.putExtra("image", covidImages[position].hashCode())
//            // put image data in Intent
//            context!!.startActivity(intent)
            // start Intent
        }
    }


    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

//        val deletebutton: ImageView = itemview.findViewById(R.id.iv_pres_dlt)
        val imageView: ImageView = itemview.findViewById(R.id.image_covid)
        var imageDetails: PrescriptionDataClass? = null

    }
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        // init the item view's
//        var name: TextView
//        var image: ImageView
//
//        init {
//            // get the reference of item view's
//            name = itemView.findViewById<View>(R.id.name) as TextView
//            image = itemView.findViewById<View>(R.id.image) as ImageView
//
//        }
//    }

}