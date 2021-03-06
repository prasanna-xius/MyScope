package com.soargtechnologies.myscope.helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.prescription.PrescriptionDataClass
import com.soargtechnologies.myscope.activities.prescription.Prescription_manualDrugDialog


class Prscription_DoctorList_Adapter(private val prescriptionList: List<PrescriptionDataClass>) : RecyclerView.Adapter<Prscription_DoctorList_Adapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //    val str : String  = diseaseList.get(position).toString();

        // diseaseList.add(position,holder)

        holder.prescriptiondestination = prescriptionList[position]

        holder.doctor_name.text = prescriptionList[position].doctor_name
        holder.doctor_sno.text = prescriptionList[position].prescription_id.toString()
        holder.doctor_date.text = prescriptionList[position].manual_saved_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            val intent = Intent(context, Prescription_manualDrugDialog::class.java)

            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

            val editor: SharedPreferences.Editor = pref.edit()

            editor.putInt("prescription_id",prescriptionList[position].prescription_id!!)

            editor.commit()

//            var bundle = Bundle()
//
//            Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
//
////
////            bundle.putInt("doctorposition" , position)
////            bundle.putString("isPrescribed", prescriptionList[position].is_prescribed )
////            bundle.putString("doctor_name", prescriptionList[position].doctor_name)
////            bundle.putString("hospital_name", prescriptionList[position].hospital_name)
//            bundle.putInt("prescription_id", prescriptionList[position].prescription_id!!)
////            Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()
//            intent.putExtras(bundle)
//
//
            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }

        holder.share_btn.setOnClickListener { v:View? ->

            val  context = v?.context
            val message : String= toString()
            val intent=Intent()
            intent.action=Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,message)
            intent.type="text/plain"
            context?.startActivity(Intent.createChooser(intent,"Please Select App"))
        }

    }

    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val doctor_sno: TextView = itemView.findViewById(R.id.doctor_sno)
        val doctor_name: TextView = itemView.findViewById(R.id.doctorTitle)  //item_list tv_id
        val doctor_date: TextView = itemView.findViewById(R.id.doctor_date)  //item_list tv_id
        val share_btn: ImageView = itemView.findViewById(R.id.share_btn)

        var prescriptiondestination: PrescriptionDataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${doctor_name.text}''${doctor_sno.text}'"""
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
                object: ItemDetailsLookup.ItemDetails<Long>() {

                    override fun getPosition(): Int = adapterPosition
                    override fun getSelectionKey(): Long? {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }
                }
    }

}
