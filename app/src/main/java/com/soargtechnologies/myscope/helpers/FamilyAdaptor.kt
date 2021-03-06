package com.soargtechnologies.myscope.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_history.Diseases
import com.soargtechnologies.myscope.activities.medical_history.FamilyHistoryUpdated

class FamilyAdaptor(private val familyList: List<Diseases>) : RecyclerView.Adapter<FamilyAdaptor.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_familycondition, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.familyConditiondestination = familyList[position]
        holder.familySno.text = familyList[position].family_id.toString()
        holder.familyHistory.text = familyList[position].family_condition
        holder.familyDate.text = familyList[position].family_save_on



        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

   //         Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, FamilyHistoryUpdated::class.java)

            intent.putExtra(FamilyHistoryUpdated.ARG_ITEM_ID, holder.familyConditiondestination!!.mobile_no)

            intent.putExtra("position" , position)

   //         Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }


    }

    override fun getItemCount(): Int {
        return familyList.size
    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val familySno: TextView = itemView.findViewById(R.id.family_sno)
        val familyHistory: TextView = itemView.findViewById(R.id.familyHistoryTitle)  //item_list tv_id
        val familyDate: TextView = itemView.findViewById(R.id.family_date)
        var familyConditiondestination: Diseases? = null


        override fun toString(): String {
            return """${super.toString()} '${familyHistory.text}' '${familySno.text}"""
        }
    }


}
