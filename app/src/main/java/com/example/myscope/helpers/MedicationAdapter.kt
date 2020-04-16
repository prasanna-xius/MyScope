package com.soargtechnologies.myscope.activities.helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_history.MedicationUpdateActivity
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity
import com.soargtechnologies.myscope.models.MedicationDataClass


class MedicationAdapter (private val medicationList: List<MedicationDataClass>) : RecyclerView.Adapter<MedicationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_medication, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.medicationdestination = medicationList[position]
        holder.txvDestination_medication.text = medicationList[position].medicationname
        holder.savedDateMedication.text = medicationList[position].medication_saved_on
        holder.serialNoMedication.text = medicationList[position].medication_id.toString()


        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, MedicationUpdateActivity::class.java)
            intent.putExtra(MedicationUpdateActivity.ARG_ITEM_ID, holder.medicationdestination!!.mobile_no)
            intent.putExtra("position" , position)

           // Toast.makeText(context,"tag"+holder.toString(),Toast.LENGTH_LONG).show()

            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("medication_id",medicationList[position].medication_id!!)
            editor.commit()


            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return medicationList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_medication: TextView = itemView.findViewById(R.id.txv_destination_medication)
        val savedDateMedication: TextView = itemView.findViewById(R.id.saveddate_medication)
        val serialNoMedication: TextView = itemView.findViewById(R.id.sn_medication)

        //item_list tv_id
        var medicationdestination: MedicationDataClass? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination_medication.text}'"""
        }
    }
}
