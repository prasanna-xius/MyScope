package com.example.myscope.activities.prescription

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.myscope.R
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.R.attr.bitmap
import retrofit2.http.Multipart
import java.sql.Blob
import java.io.*

import android.R.attr.data
import android.net.Uri
import android.util.Base64
import com.google.common.reflect.Reflection.getPackageName


class Prescription_ImageAdapter(private val imglist: List<PrescriptionDataClass>): RecyclerView.Adapter<Prescription_ImageAdapter.ImageViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(com.example.myscope.R.layout.list_item_prescription_image, parent, false)
        )
    }
    override fun getItemCount() = imglist.size
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imgls = imglist[position]
        val imageresource: Char = imgls.downloadfile!![position]
//        val intres:Int = res.getI
//        val imageBytes = Base64.decode(imgls.downloadfile!![position].toString(),0)
//        val imageBytes = Base64.decode(imgls.downloadfile!![position], 0)
//        val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
//        val bm:Bitmap = StringToBitMap(imgls.downloadfile!![position])
//        holder.view.iv_pres.setImageResource(Uri.parse(image.toString()))

//        val fos = FileOutputStream(imgls.downloadfile.toString())

        println("image created")
        Glide.with(holder.view.context)
                .load(imageresource)
                .into(holder.view.iv_pres)
    }



    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}