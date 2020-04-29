package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R

class Glucose_Adaptor (private val glucoseList: List<Self_dataClass>) : RecyclerView.Adapter<Glucose_Adaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Glucose_Adaptor.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_glucose, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return glucoseList.size
    }

    override fun onBindViewHolder(holder: Glucose_Adaptor.ViewHolder, position: Int) {
        holder.glucosedestination = glucoseList[position]
        holder.glucose_sno.text = glucoseList[position].glucose_id.toString()
        holder.glucoseTitle.text = glucoseList[position].test_result + "mg/dl"
        holder.glucose_date.text = glucoseList[position].glucose_save_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, Blood_GlucoseUpdate::class.java)

            intent.putExtra(Blood_GlucoseUpdate.ARG_ITEM_ID, holder.glucosedestination!!.mobile_no)

            intent.putExtra("position" , position)

 //           Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val glucose_sno: TextView = itemView.findViewById(R.id.glucose_sno)
        val glucoseTitle: TextView = itemView.findViewById(R.id.glucose_title)  //item_list tv_id
        val glucose_date: TextView = itemView.findViewById(R.id.glucose_date)  //item_list tv_id
        var glucosedestination: Self_dataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${glucoseTitle.text}''${glucose_sno.text}'"""
        }
    }
}