package com.example.myscope.activities.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.medical_history.MedicationUpdateActivity
import com.example.myscope.models.MedicalHistoryModelActivity
import com.example.myscope.models.MedicationDataClass


class MedicationAdapter (private val medicationList: List<MedicationDataClass>) : RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_medication, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.medicationdestination = medicationList[position]
        holder.txvDestination_medication.text = medicationList[position].medicationname

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, MedicationUpdateActivity::class.java)
            intent.putExtra(MedicationUpdateActivity.ARG_ITEM_ID, holder.medicationdestination!!.mobile_no)
            intent.putExtra("position" , position)

           // Toast.makeText(context,"tag"+holder.toString(),Toast.LENGTH_LONG).show()

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_medication: TextView = itemView.findViewById(R.id.txv_destination_medication)  //item_list tv_id
        var medicationdestination: MedicationDataClass? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination_medication.text}'"""
        }
    }
}
