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

//import com.example.myscope.activities.medical_history.Disease_History

class Disease_Adaptor(private val diseaseList: List<Diseases>) : RecyclerView.Adapter<Disease_Adaptor.ViewHolder>()  {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_disease, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   //    val str : String  = diseaseList.get(position).toString();

       // diseaseList.add(position,holder)

        holder.diseasedestination = diseaseList[position]
        holder.disease_sno.text = diseaseList[position].disease_id.toString()
        holder.known_condition.text = diseaseList[position].known_condition
        holder.disease_date.text = diseaseList[position].disease_save_on


       // Toast.makeText(this ,"place"+ position , Toast.LENGTH_LONG).show()

        holder.itemView.setOnClickListener { v: View? ->

           val  context = v?.context

            //Toast.makeText(context, "item Clicked" + position, Toast.LENGTH_LONG).show()
            val intent = Intent(context, DiseaseHistoryUpdate::class.java)

            intent.putExtra(DiseaseHistoryUpdate.ARG_ITEM_ID, holder.diseasedestination!!.mobile_no)

            intent.putExtra("position" , position)

            Toast.makeText(context,"holder data"+v,Toast.LENGTH_LONG).show()


            context?.startActivity(intent)

//            Log.d("intent msg", v.toString())

        }


    }

    override fun getItemCount(): Int {
        return diseaseList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val disease_sno: TextView = itemView.findViewById(R.id.disease_sno)
        val known_condition: TextView = itemView.findViewById(R.id.name_tablet)  //item_list tv_id
        val disease_date: TextView = itemView.findViewById(R.id.disease_date)  //item_list tv_id
        var diseasedestination: Diseases? = null


        override fun toString(): String {
            return """${super.toString()} '${known_condition.text}''${disease_sno.text}'"""
        }
    }
}

