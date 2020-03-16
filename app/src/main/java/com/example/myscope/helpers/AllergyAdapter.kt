package com.example.myscope.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.medical_history.AllergyUpdate_Activity
import com.example.myscope.models.MedicalHistoryModelActivity

class AllergyAdapter(private val medicalHistoryModelActivityList: List<MedicalHistoryModelActivity>) : RecyclerView.Adapter<AllergyAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_allergy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.destination = medicalHistoryModelActivityList[position]
        holder.txvDestination.text = medicalHistoryModelActivityList[position].name

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, AllergyUpdate_Activity::class.java)
            intent.putExtra(AllergyUpdate_Activity.ARG_ITEM_ID, holder.destination!!.mobile_no)
            intent.putExtra("position" , position)
            //notifyItemRangeRemoved()
            //notifyDataSetChanged()
            //notifyItemChanged(holder.adapterPosition)
            //holder.getAdapterPosition()
            context?.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return medicalHistoryModelActivityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination: TextView = itemView.findViewById(R.id.txv_destination)  //item_list tv_id
        var destination: MedicalHistoryModelActivity? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination.text}'"""
        }
    }

    /* fun update(modelList:List<MedicalHistoryModelActivity>){
         myList = modelList
         medicalHistoryModelActivityList!!.notifyDataSetChanged()
     }*/
}
