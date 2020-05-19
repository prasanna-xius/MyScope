package com.soargtechnologies.myscope.helpers

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.services.PrescriptionInterface
import com.soargtechnologies.myscope.services.ServiceBuilder
import kotlinx.android.synthetic.main.list_doc_discharge.view.*
import kotlinx.android.synthetic.main.list_doc_education.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Document_EducationAdapter(private val imglist: MutableList<PrescriptionDataClass>): RecyclerView.Adapter<Document_EducationAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: Document_EducationAdapter.ViewHolder, position: Int) {
        holder.imageDetails = imglist[position]
        holder.savedDate.text = imglist[position].document_education_saved_on
        val imgls = imglist[position]
        id = imgls.document_education_id
        var encodedString: String = imgls.downloadfile.toString()
        var pureBase64Encoded = encodedString.substring(encodedString.indexOf(",") + 1);

        var decodedBytes = Base64.decode(pureBase64Encoded, Base64.DEFAULT)

        holder.uploadsno.text = imgls.document_education_id.toString()

        if (imgls.document_education_type.equals("image")) {
            Glide.with(holder.itemView.context)
                    .load(decodedBytes)
                    .into(holder.itemview.education_img)


        } else {

            Glide.with(holder.itemView.context)
                    .load(R.drawable.pdf)
                    .into(holder.itemview.education_img)


        }
        holder.imageView.setOnClickListener { v: View? ->

            val context = v?.context

            if (imgls.document_education_type.equals("pdf")) {


                val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode
                val editor: SharedPreferences.Editor = pref.edit()
                var s = imglist[position].downloadfile
                editor.putString("buffer", s)
                editor.commit()
//                val intent = Intent(context, PDFopenfile::class.java)
//                context.startActivity(intent)
            }
            if (imgls.upload_type.equals("image")) {
                dialog = Dialog(context!!)
                dialog!!.setContentView(R.layout.custom_dialog)
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
        holder.deletebutton.setOnClickListener { v: View? ->

            val context = v?.context
            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode
            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("educationid", imglist[position].document_education_id!!)
            editor.commit()

            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure you want to delete?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                //Toast.makeText(applicationContext, "clicked yes", Toast.LENGTH_LONG).show()


                val deleteService = ServiceBuilder.buildService(PrescriptionInterface::class.java)
                val body = PrescriptionDataClass()
                body.document_education_id = imglist[position].document_education_id!!



                Log.d("Tag::::::", " " + deleteService.toString())


                val requestCall = deleteService.deleteEducationDetails(body)

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

    private var removedPosition: Int = 0
    //private var removedItem: PrescriptionDataClass? = null

    var removeButton: ImageView? = null
    var id = 0
    //var images = intArrayOf(R.drawable.pdf)
    var images: Array<Int>? = null
    var dialog: Dialog? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_doc_education, parent, false)

        var viewHolder: ViewHolder = ViewHolder(view);
        // dialog!!.setContentView(R.layout.custom_dialog)
        //return ViewHolder(view)
        return viewHolder


    }

    override fun getItemCount() = imglist.size




    class ViewHolder(val itemview: View) : RecyclerView.ViewHolder(itemview) {

        val deletebutton: ImageView = itemview.findViewById(R.id.educationdlt)
        val imageView: ImageView = itemview.findViewById(R.id.education_img)
        val savedDate: TextView = itemView.findViewById(R.id.educationdate)
        var imageDetails: PrescriptionDataClass? = null
        var uploadsno: TextView = itemview.findViewById(R.id.educationid)
    }



}
