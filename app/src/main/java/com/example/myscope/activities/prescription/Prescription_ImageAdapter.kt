package com.example.myscope.activities.prescription

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide


import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.Bitmap

import android.graphics.BitmapFactory

import java.nio.ByteBuffer
import android.graphics.Bitmap.CompressFormat
import android.text.TextUtils.replace
import android.util.Base64
import java.io.ByteArrayOutputStream
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.R.attr.path
import android.R.attr.path
import android.net.Uri
import android.os.Build
import android.widget.Button
import android.widget.ImageView
import com.example.myscope.R
import com.example.myscope.helpers.AllergyAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import java.net.URI
import java.nio.file.Files
import java.nio.file.Files.readAllBytes
import java.nio.file.Paths


class Prescription_ImageAdapter(private val imglist: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<Prescription_ImageAdapter.ViewHolder>() {

    private var removedPosition: Int = 0
    //private var removedItem: PrescriptionDataClass? = null

    var removeButton: ImageView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val view= LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_prescription_image, parent, false)
        return ViewHolder(view)



    }

    override fun getItemCount() = imglist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageDetails=imglist[position]

        //holder.deletebutton.text=imglist[position]
        val imgls = imglist[position]
        var encodedString: String = imgls.downloadfile.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)


        val buf: ByteArray = decodedBytes
        val s: String = String(buf);
        val uri: Uri = Uri.parse(s)
        //
        // val imgbytes = android.util.Base64.decode(imgls.p_upload.toString(),android.util.Base64.DEFAULT)

        //imageupload.setImageBitmap(bitmap)
//        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0,decodedBytes.size)

        //val bitmap = BitmapFactory.decodeFile("/path/images/image.jpg")
        //val blob = ByteArrayOutputStream()
        // bitmap.compress(CompressFormat.PNG, 0 /* Ignored for PNGs */, blob)
        //val bitmapdata = blob.toByteArray()
        //imageupload.setImageBitmap(bitmap)


        Glide.with(holder.itemView.context)
                .load(decodedBytes)
                .into(holder.itemview.iv_pres)

        holder.itemView.setOnClickListener { v: View? ->

            val context = v?.context
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context!!.startActivity(intent)
        }

        holder.deletebutton.setOnClickListener { v: View? ->

           // var removedItem: PrescriptionDataClass=imglist.get(position)

            imglist.removeAt(position)  /* remove the item from list */
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }


    }

    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        val deletebutton: Button = itemview.findViewById(R.id.iv_pres_dlt)
        val imageView: ImageView = itemview.findViewById(R.id.iv_pres)
        var imageDetails: PrescriptionDataClass? = null
    }



    }








