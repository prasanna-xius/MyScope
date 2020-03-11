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


class Prscription_DrugList_Adapter(private val prescriptionDrugList: List<Prescription_AddDoctor>) : RecyclerView.Adapter<Prscription_DrugList_Adapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.model1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //    val str : String  = diseaseList.get(position).toString();

        // diseaseList.add(position,holder)

        holder.prescriptiondestination = prescriptionDrugList[position]

        holder.drug_name.text = prescriptionDrugList[position].drug_name
        holder.timings_text.text = prescriptionDrugList[position].how_often_taken

        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            for (i in 0 until prescriptionDrugList.size) {

                val  context = v?.context
                val intent = Intent(context, PrescriptionDrugListUpdate::class.java)

//            intent.putExtra(PrescriptionDrugListUpdate.ARG_ITEM_ID, holder.prescriptiondestination!!.mobile_no)

                intent.putExtra("position" , position)
                Toast.makeText(context,"holder data"+v,Toast.LENGTH_LONG).show()
                var bundle = Bundle()
                bundle.putInt("position" , position)

                bundle.putString("formulation", prescriptionDrugList[position].formulation)

                bundle.putString("drug_name", prescriptionDrugList[position].drug_name)
                bundle.putString("brand_name", prescriptionDrugList[position].brand_name)
                bundle.putString("dose_strength", prescriptionDrugList[position].dose_strength)

                bundle.putString("dose_unit", prescriptionDrugList[position].dose_unit)
                bundle.putString("how_often_taken", prescriptionDrugList[position].how_often_taken)
                bundle.putString("time", prescriptionDrugList[position].time)


                bundle.putString("start_date", prescriptionDrugList[position].start_date)
                bundle.putString("stop_date", prescriptionDrugList[position].stop_date)


                bundle.putInt("prescription_id", prescriptionDrugList[position].prescription_id!!)
                Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()
                intent.putExtras(bundle)
                context?.startActivity(intent)

            }

        }

    }

    override fun getItemCount(): Int {
        return prescriptionDrugList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val drug_name: TextView = itemView.findViewById(R.id.drug_nametxt)  //item_list tv_id
        val timings_text: TextView = itemView.findViewById(R.id.time_txt)  //item_list tv_id

        var prescriptiondestination: Prescription_AddDoctor? = null


        override fun toString(): String {
            return """${super.toString()} '${drug_name.text}''${timings_text.text}'"""
        }
    }
}
