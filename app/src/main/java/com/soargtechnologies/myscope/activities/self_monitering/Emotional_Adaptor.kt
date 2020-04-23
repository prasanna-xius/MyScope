package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R

class Emotional_Adaptor(private val emotionalList: List<Self_dataClass>) : RecyclerView.Adapter<Emotional_Adaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_emotional, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return emotionalList.size    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.emotionaldestination = emotionalList[position]
        holder.emotional_sno.text = emotionalList[position].emotional_id.toString()
        holder.emotionalTitle.text = emotionalList[position].emotional_status
        holder.emotional_date.text = emotionalList[position].emotional_saved_on


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, Emotional_state_Update::class.java)

            intent.putExtra(Emotional_state_Update.ARG_ITEM_ID, holder.emotionaldestination!!.mobile_no)

            intent.putExtra("position" , position)

            Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }     }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val emotional_sno: TextView = itemView.findViewById(R.id.emotional_sno)
        val emotionalTitle: TextView = itemView.findViewById(R.id.emotional_title)  //item_list tv_id
        val emotional_date: TextView = itemView.findViewById(R.id.emotional_date)  //item_list tv_id
        var emotionaldestination: Self_dataClass? = null


        override fun toString(): String {
            return """${super.toString()} '${emotionalTitle.text}''${emotional_sno.text}'"""
        }
    }

}
