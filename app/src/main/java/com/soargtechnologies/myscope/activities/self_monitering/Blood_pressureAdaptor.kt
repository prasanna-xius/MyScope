package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R

class Blood_pressureAdaptor(private val pressureList: List<Self_dataClass>) : RecyclerView.Adapter<Blood_pressureAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_pressure, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return pressureList.size


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.pressuredestination = pressureList[position]
        holder.pressureSno.text = pressureList[position].pressure_id.toString()
        holder.pressureTitle.text = pressureList[position].systolic + "/" +  pressureList[position].diastrlic + "(mmHg)"
        holder.pressureDate.text = pressureList[position].pressure_save_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, Blood_pressureUpdate::class.java)

            intent.putExtra(Blood_pressureUpdate.ARG_ITEM_ID, holder.pressuredestination!!.mobile_no)

            intent.putExtra("position" , position)

   //         Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val pressureSno: TextView = itemView.findViewById(R.id.pressure_sno)
        val pressureTitle: TextView = itemView.findViewById(R.id.pressure_title)
        val pressureDate: TextView = itemView.findViewById(R.id.pressure_date)  //item_list tv_id
        var pressuredestination: Self_dataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${pressureTitle.text}''${pressureSno.text}'"""
        }
    }
}