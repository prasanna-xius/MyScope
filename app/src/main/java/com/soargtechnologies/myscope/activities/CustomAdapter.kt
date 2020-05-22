package com.soargtechnologies.myscope.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import kotlinx.android.synthetic.main.educationimagerow.view.*
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import org.w3c.dom.Text

class CustomAdapter(private val covidImages: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    var id = 0
    var dialog: Dialog? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.educationimagerow, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }
    override fun getItemCount() = covidImages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageDetails = covidImages[position]
        val imgls = covidImages[position]
        id = imgls.education_imagesno

        var encodedString: String = imgls.educationdownloadimage.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

        var decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        holder.imageTiltle.text = imgls.education_imagetitle.toString()

        Glide.with(holder.itemView.context)
                .load(decodedBytes)
                .into(holder.itemview.educovid)

        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener {v: View? ->

            val context = v?.context

            dialog = Dialog(context!!)
            dialog!!.setContentView(R.layout.custom_dialog_edu)
            var img: ImageView = dialog!!.findViewById(R.id.img)

            var btn_close: Button = dialog!!.findViewById(R.id.btn_close);

            Glide.with(holder.itemView.context)
                    .load(decodedBytes)
                    .into(img)

            dialog!!.show()

            btn_close!!.setOnClickListener((object : View.OnClickListener {

                override fun onClick(v: View?) {
                    dialog!!.dismiss()
                }
            }))
        }
    }


    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

//
        val imageTiltle: TextView = itemview.findViewById(R.id.educovidtitle)
        val imageView: ImageView = itemview.findViewById(R.id.educovid)
        var imageDetails: PrescriptionDataClass? = null

    }

}