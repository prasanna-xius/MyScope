package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R

class CholestrolAdaptor(private val cholestrolList: List<Self_dataClass>) : RecyclerView.Adapter<CholestrolAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_cholestrol, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return cholestrolList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cholestroldestination = cholestrolList[position]
        holder.cholestrolSno.text = cholestrolList[position].cholestrol_id.toString()
        holder.cholestrolTitle.text = cholestrolList[position].total_cholestrol + "mg/dl"
        holder.cholestrolDate.text = cholestrolList[position].cholestrol_save_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, CholestrolUpdate::class.java)

            intent.putExtra(CholestrolUpdate.ARG_ITEM_ID, holder.cholestroldestination!!.mobile_no)

            intent.putExtra("position" , position)

  //          Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cholestrolSno: TextView = itemView.findViewById(R.id.cholestrol_sno)
        val cholestrolTitle: TextView = itemView.findViewById(R.id.cholestrol_title)  //item_list tv_id
        val cholestrolDate: TextView = itemView.findViewById(R.id.cholestrol_date)  //item_list tv_id
        var cholestroldestination: Self_dataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${cholestrolTitle.text}''${cholestrolSno.text}'"""
        }
    }
}