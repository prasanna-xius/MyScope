package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.self_monitering.BmiUpdate.Companion.ARG_ITEM_ID


class BmiAdaptor (private val bmiList: List<Self_dataClass>) : RecyclerView.Adapter<BmiAdaptor .ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_bmi, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bmiList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bmidestination = bmiList[position]
        holder.bmiSno.text = bmiList[position].bmi_id.toString()
        holder.bmiTitle.text = bmiList[position].bmi
        holder.bmiDate.text = bmiList[position].bmi_save_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, BmiUpdate::class.java)

            intent.putExtra(ARG_ITEM_ID, holder.bmidestination!!.mobile_no)

            intent.putExtra("position" , position)

  //          Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bmiSno: TextView = itemView.findViewById(R.id.bmi_sno)
        val bmiTitle: TextView = itemView.findViewById(R.id.bmi_title)  //item_list tv_id
        val bmiDate: TextView = itemView.findViewById(R.id.bmi_date)  //item_list tv_id
        var bmidestination: Self_dataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${bmiTitle.text}''${bmiSno.text}'"""
        }
    }

}
