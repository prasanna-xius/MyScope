package com.example.myscope.activities.prescription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R


class Prscription_DoctorList_Adapter(private val prescriptionList: List<Prescription_AddDoctor>) : RecyclerView.Adapter<Prscription_DoctorList_Adapter.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.model, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //    val str : String  = diseaseList.get(position).toString();

        // diseaseList.add(position,holder)

        holder.prescriptiondestination = prescriptionList[position]

        holder.doctor_name.text = prescriptionList[position].doctor_name
        holder.hospital_name.text = prescriptionList[position].hospital_name


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context


            val intent = Intent(context, Prescription_manual::class.java)
            var bundle = Bundle()

            Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()


            bundle.putInt("position" , position)

            bundle.putString("doctor_name", prescriptionList[position].doctor_name)
            bundle.putString("hospital_name", prescriptionList[position].hospital_name)
            bundle.putInt("prescription_id", prescriptionList[position].prescription_id!!)
            Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()
            intent.putExtras(bundle)
//
//
            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }


    }

    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val doctor_name: TextView = itemView.findViewById(R.id.doctor_nametxt)  //item_list tv_id
        val hospital_name: TextView = itemView.findViewById(R.id.hospital_nametxt)  //item_list tv_id

        var prescriptiondestination: Prescription_AddDoctor? = null


        override fun toString(): String {
            return """${super.toString()} '${doctor_name.text}''${hospital_name.text}'"""
        }
    }

}
