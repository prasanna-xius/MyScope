package com.example.curvepicture.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.curvepicture.activities.ImmunizationUpdateActivity
import com.example.curvepicture.activities.MedicationUpdateActivity
import com.example.myscope.R
import com.example.myscope.models.MedicalHistoryModelActivity


class MedicationAdapter (private val medicalHistoryModelActivityList: List<MedicalHistoryModelActivity>) : RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_medication, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.medicationdestination = medicalHistoryModelActivityList[position]
        holder.txvDestination_medication.text = medicalHistoryModelActivityList[position].medicationname

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
        return medicalHistoryModelActivityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_medication: TextView = itemView.findViewById(R.id.txv_destination_medication)  //item_list tv_id
        var medicationdestination: MedicalHistoryModelActivity? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination_medication.text}'"""
        }
    }
}
