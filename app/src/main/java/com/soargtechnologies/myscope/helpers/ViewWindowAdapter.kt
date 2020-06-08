package com.soargtechnologies.myscope.helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_history.AllergyUpdate_Activity
import com.soargtechnologies.myscope.models.AllergyDataClass
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity

class ViewWindowAdapter(private val viewWindowlist: List<AllergyDataClass>) : RecyclerView.Adapter<ViewWindowAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_view_window, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.destination = viewWindowlist[position]
        holder.txvDestination.text = viewWindowlist[position].name
        holder.savedDate.text = viewWindowlist[position].allergy_saved_on
        holder.serialNo.text = viewWindowlist[position].allergy_id.toString()

        holder.itemView.setOnClickListener { v ->
            val context = v.context
            val intent = Intent(context, AllergyUpdate_Activity::class.java)
//            intent.putExtra(AllergyUpdate_Activity.ARG_ITEM_ID, holder.destination!!.mobile_no)
            intent.putExtra("position" , position)
            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("allergy_id",viewWindowlist[position].allergy_id!!)
            editor.commit()


            context?.startActivity(intent)


        }
    }

    override fun getItemCount(): Int {
        return viewWindowlist.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination: TextView = itemView.findViewById(R.id.txv_destination)
        val savedDate: TextView = itemView.findViewById(R.id.saveddate)
        val serialNo: TextView = itemView.findViewById(R.id.sn)

        //item_list tv_id
        var destination: AllergyDataClass? = null

        override fun toString(): String {
            return """${super.toString()} '${txvDestination.text}'"""
        }
    }

    /* fun update(modelList:List<MedicalHistoryModelActivity>){
         myList = modelList
         medicalHistoryModelActivityList!!.notifyDataSetChanged()
     }*/
}
