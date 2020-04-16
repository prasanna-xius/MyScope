package com.soargtechnologies.myscope.activities.medical_history

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_history.Adverse_Drug_Reaction_Update.Companion.ARG_ITEM_ID

class AdverseDrugAdaptor (private val adverseDrugList: List<Diseases>) : RecyclerView.Adapter<AdverseDrugAdaptor.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_adversedrug, parent, false)
        return ViewHolder(view)
    }


    override fun getItemCount(): Int {

        return adverseDrugList.size

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.adverseDrugConditiondestination = adverseDrugList[position]

        holder.adverseDrug.text = adverseDrugList[position].drugname
        holder.adversedate.text = adverseDrugList[position].adverse_save_on
        holder.adversesno.text = adverseDrugList[position].adverse_id.toString()


        // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

            val  context = v?.context

            Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, Adverse_Drug_Reaction_Update::class.java)

            intent.putExtra(ARG_ITEM_ID, holder.adverseDrugConditiondestination!!.mobile_no)

            intent.putExtra("position" , position)

            Toast.makeText(context,"holder data"+v, Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val adverseDrug: TextView = itemView.findViewById(R.id.adversedrugTitle)
        val adversesno: TextView = itemView.findViewById(R.id.adverse_sno)
        val adversedate: TextView = itemView.findViewById(R.id.adverse_date)//item_list tv_id
        var adverseDrugConditiondestination: Diseases? = null


        override fun toString(): String {
            return """${super.toString()} '${adverseDrug.text}'"""
        }
    }

}