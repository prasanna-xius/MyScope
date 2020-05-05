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
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.helpers.AllergyAdapter
import com.example.myscope.services.ImageApiService
import com.example.myscope.services.ServiceBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.list_item_prescription_image.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URI
import java.nio.file.Files
import java.nio.file.Files.readAllBytes
import java.nio.file.Paths


class Prescription_ImageAdapter(private val imglist: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<Prescription_ImageAdapter.ViewHolder>() {

    private var removedPosition: Int = 0
    //private var removedItem: PrescriptionDataClass? = null

    var removeButton: ImageView? = null
    var id = 0
     var dialog :Dialog?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {

        val view= LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_prescription_image, parent, false)

        var viewHolder :ViewHolder  =  ViewHolder(view);

       // dialog!!.setContentView(R.layout.custom_dialog)
        //return ViewHolder(view)
        return viewHolder



    }

    override fun getItemCount() = imglist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imageDetails = imglist[position]


        //holder.deletebutton.text=imglist[position]
        val imgls = imglist[position]
        id = imgls.p_uploadid
        var encodedString: String = imgls.downloadfile.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

        val decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        val buf: ByteArray = decodedBytes
        val s: String = String(buf);
        var uri: Uri = Uri.parse(s)
        //
        // val imgbytes = android.util.Base64.decode(imgls.p_upload.toString(),android.util.Base64.DEFAULT)

        //imageupload.setImageBitmap(bitmap)
//        val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0,decodedBytes.size)

        //val bitmap = BitmapFactory.decodeFile("/path/images/image.jpg")
        //val blob = ByteArrayOutputStream()
        // bitmap.compress(CompressFormat.PNG, 0 /* Ignored for PNGs */, blob)
        //val bitmapdata = blob.toByteArray()
        //imageupload.setImageBitmap(bitmap)

        holder.uploadsno.text = imgls.p_uploadid.toString()


        Glide.with(holder.itemView.context)
                .load(decodedBytes)
                .into(holder.itemview.iv_pres)

//        holder.itemView.setOnClickListener { v: View? ->
//
//            val context = v?.context
//            val intent = Intent(context, PDFopenfile::class.java)
//            intent.putExtra("pdf", imgls.downloadfile!![position])
//            context!!.startActivity(intent)
//        }


        holder.imageView.setOnClickListener (object : View.OnClickListener {
            override fun onClick(v: View?) {
                val context = v?.context
                dialog = Dialog(context!!)
                dialog!!.setContentView(R.layout.custom_dialog)

                var img: ImageView= dialog!!.findViewById(R.id.img);
                var pdf  = dialog!!.findViewById<com.github.barteksc.pdfviewer.PDFView>(R.id.pdfView)
                //var tv: TextView  = (TextView) dialog.findViewById(R.id.name);
                var btn_close: Button = dialog!!.findViewById(R.id.btn_close);
//                img.setImageResource(imglist.get(holder.getAdapterPosition()).p_uploadid);
                //name.setText(list.get(viewHolder.getAdapterPosition()).getName());
                pdf.fromBytes(decodedBytes)

//                Glide.with(holder.imageView.context)
//                        .load(decodedBytes)
//                        .into(img)
                dialog!!.show()

                btn_close!!.setOnClickListener( (object : View.OnClickListener {

                    override fun onClick(v: View?) {
                        dialog!!.dismiss()
                    }
                }))

            }
        })

        holder.deletebutton.setOnClickListener { v: View? ->

            // var removedItem: PrescriptionDataClass=imglist.get(position)
//            imglist.p_uploadid.removeAt(position)


            val context = v?.context
            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("uploadid", imglist[position].p_uploadid!!)
            editor.commit()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                //Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()


                val deleteService = ServiceBuilder.buildService(ImageApiService::class.java)
                val body = PrescriptionDataClass()
                body.p_uploadid = imglist[position].p_uploadid!!



                Log.d("Tag::::::", " " + deleteService.toString())


                val requestCall = deleteService.deleteImageDetails(body)

                requestCall.enqueue(object : Callback<PrescriptionDataClass> {
                    //PrescriptionInterface().getImageDetails().enqueue(object: Callback<List<PrescriptionDataClass>> {

                    // If you receive a HTTP Response, then this method is executed
                    // Your STATUS Code will decide if your Http Response is a Success or Error
                    override fun onResponse(call: Call<PrescriptionDataClass>, response: Response<PrescriptionDataClass>) {

                        Log.d("Tag::::::", " " + response.toString()) +
                                Log.e("Tag::::::", " " + response.toString())

                        if (response.isSuccessful()) {
                            // Your status code is in the range of 200's
                            //  val imageList = response.body()!!

                        } else if (response.code() == 401) {
                            Toast.makeText(context,
                                    "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                        } else { // Application-level failure
//                         Your status code is in the range of 300's, 400's and 500's
                            Toast.makeText(context, "Failed to retrieve items", Toast.LENGTH_LONG).show()
                        }
                    }

                    // Invoked in case of Network Error or Establishing connection with Server
                    // or Error Creating Http Request or Error Processing Http Response
                    override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {

                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_LONG).show()
                    }


                })

            }
            builder.setNegativeButton("No"){dialogInterface, which ->

            }

            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()

        }
    }

    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        val deletebutton: ImageView = itemview.findViewById(R.id.iv_pres_dlt)
        val imageView: ImageView = itemview.findViewById(R.id.iv_pres)
        var imageDetails: PrescriptionDataClass? = null
        var uploadsno:TextView = itemview.findViewById(R.id.uploadid)
    }



    }








