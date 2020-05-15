package com.soargtechnologies.myscope.activities

import android.content.Context
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
import kotlinx.android.synthetic.main.educationimagerow.view.*

class CustomAdapter(private val covidImages: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    var context:Context? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.educationimagerow, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { // set the data in items
//        holder.name.setText(personNames.get(position).toString());
//        hol = imglist[position]
//        holder.savedDate.text = imglist[position].upload_saved_on
        holder.educationimage = covidImages[position]
        val educovid = covidImages[position]
        holder.name.text =  educovid.education_imagetitle
        var encodedString: String = educovid.educationdownloadfile.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

        var decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        Glide.with(holder.itemView.context)
                .load(decodedBytes)
                .into(holder.itemView.educovid)

        // implement setOnClickListener event on item view.
//        holder.itemView.setOnClickListener {
//            // open another activity on item click
//
//            val intent = Intent(context, Full_Image_Activity::class.java)
//            intent.putExtra("image", covidImages[position].hashCode()) // put image data in Intent
//            context!!.startActivity(intent) // start Intent
//        }
    }

    override fun getItemCount(): Int {
        return covidImages.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // init the item view's

            // get the reference of item view's
           var name = itemView.findViewById<View>(R.id.educovidtitle) as TextView
            var educationimage: PrescriptionDataClass? = null
           var image = itemView.findViewById<View>(R.id.educovid) as ImageView

    }

}