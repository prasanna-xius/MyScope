package com.soargtechnologies.myscope.activities.prescription

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*


class Prescription_ImageAdapter(private val imglist: List<PrescriptionDataClass>): RecyclerView.Adapter<Prescription_ImageAdapter.ImageViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
                LayoutInflater.from(parent.context)
                        .inflate(com.soargtechnologies.myscope.R.layout.list_item_prescription_image, parent, false)
        )
    }

    override fun getItemCount() = imglist.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imgls = imglist[position]
        var encodedString:String = imgls.downloadfile.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",")  + 1);

        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        //
        // val imgbytes = android.util.Base64.decode(imgls.p_upload.toString(),android.util.Base64.DEFAULT)

        //imageupload.setImageBitmap(bitmap)
        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0,decodedBytes.size)

        //val bitmap = BitmapFactory.decodeFile("/path/images/image.jpg")
        //val blob = ByteArrayOutputStream()
        // bitmap.compress(CompressFormat.PNG, 0 /* Ignored for PNGs */, blob)
        //val bitmapdata = blob.toByteArray()
        //imageupload.setImageBitmap(bitmap)




        Glide.with(holder.view.context)
                .load(bitmap)
                .into(holder.view.iv_pres)


        /*val bmp = Bitmap.createBitmap(imageWidth, imageHeight, Bitmap.Config.ARGB_8888)
        val buffer = ByteBuffer.wrap(bitmapdata)
        bmp.copyPixelsFromBuffer(buffer)*/
    }


    class ImageViewHolder(val view: View) : RecyclerView.ViewHolder(view)



}

