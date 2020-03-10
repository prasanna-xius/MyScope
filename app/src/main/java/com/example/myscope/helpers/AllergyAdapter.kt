package com.example.myscope.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.medical_history.AllergyUpdate_Activity
import com.example.myscope.models.Allergy

class AllergyAdapter(private val allergyList: List<Allergy>) : RecyclerView.Adapter<AllergyAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_allergy, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.destination = allergyList[position]
        holder.txvDestination.text = allergyList[position].name

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, AllergyUpdate_Activity::class.java)
            intent.putExtra(AllergyUpdate_Activity.ARG_ITEM_ID, holder.destination!!.id)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return allergyList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination: TextView = itemView.findViewById(R.id.txv_destination)  //item_list tv_id
        var destination: Allergy? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination.text}'"""
        }
    }
}
