package com.soargtechnologies.myscope.activities.prescription

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R


class Prscription_DrugList_Adapter(private val prescriptionDrugList: List<PrescriptionDataClass>) : RecyclerView.Adapter<Prscription_DrugList_Adapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.model1, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //    val str : String  = diseaseList.get(position).toString();

        // diseaseList.add(position,holder)

        holder.prescriptiondestination = prescriptionDrugList[position]

        holder.drug_id.text = prescriptionDrugList[position].drug_id.toString()
        holder.brandname.text = prescriptionDrugList[position].brand_name
        holder.startdate.text = prescriptionDrugList[position].drug_saved_on

        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->


                val  context = v?.context
                val intent = Intent(context, PrescriptionDrugListUpdate::class.java)
                Toast.makeText(context,"holder data"+v,Toast.LENGTH_LONG).show()
                var bundle = Bundle()
                bundle.putInt("position" , position)
                bundle.putInt("drug_id" , prescriptionDrugList[position].drug_id)
                intent.putExtras(bundle)
                context?.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return prescriptionDrugList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val brandname: TextView = itemView.findViewById(R.id.drugTitle)  //item_list tv_id
        val startdate: TextView = itemView.findViewById(R.id.drug_date)  //item_list tv_id
        val drug_id: TextView = itemView.findViewById(R.id.drug_sno)  //item_list tv_id


        var prescriptiondestination: PrescriptionDataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${brandname.text}''${startdate.text}''${drug_id.text}'"""
        }
    }
}
