package com.example.myscope.activities.medical_history



import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R


class SocialAdaptor (private val socialList: List<SocialHabits>) : RecyclerView.Adapter<SocialAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_social, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.socialdestination = socialList[position]

        holder.social_Title.text = socialList[position].smoking

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, DiseaseHistoryUpdate::class.java)

            intent.putExtra(SocialHistoryUpdate.ARG_ITEM_ID, holder.socialdestination!!.mobile_no)

            intent.putExtra("position" , position)

            Toast.makeText(context,"holder data"+v,Toast.LENGTH_LONG).show()

            context?.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return socialList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val social_Title: TextView = itemView.findViewById(R.id.socialTitle)  //item_list tv_id
        var socialdestination: SocialHabits? = null


        override fun toString(): String {
            return """${super.toString()} '${social_Title.text}'"""
        }
    }
}