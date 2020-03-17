package com.example.curvepicture.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.curvepicture.activities.ImmunizationUpdateActivity
import com.example.myscope.R
import com.example.myscope.models.MedicalHistoryModelActivity


class ImmunizationAdapter (private val medicalHistoryModelActivityList: List<MedicalHistoryModelActivity>) : RecyclerView.Adapter<ImmunizationAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_immun, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.immundestination = medicalHistoryModelActivityList[position]
        holder.txvDestination_immu.text = medicalHistoryModelActivityList[position].immuname

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, ImmunizationUpdateActivity::class.java)
            intent.putExtra(ImmunizationUpdateActivity.ARG_ITEM_ID, holder.immundestination!!.mobile_no)
            intent.putExtra("position" , position)

           // Toast.makeText(context,"tag"+holder.toString(),Toast.LENGTH_LONG).show()

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return medicalHistoryModelActivityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_immu: TextView = itemView.findViewById(R.id.txv_destination_immun)  //item_list tv_id
        var immundestination: MedicalHistoryModelActivity? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination_immu.text}'"""
        }
    }
}