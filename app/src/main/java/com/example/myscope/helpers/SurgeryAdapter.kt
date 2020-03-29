package com.example.myscope.helpers

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.medical_history.SurgeryUpdateActivity
import com.example.myscope.models.MedicalHistoryModelActivity

import kotlinx.android.synthetic.main.activity_surgery_list.*

class SurgeryAdapter (private val medicalHistoryModelActivityList: List<MedicalHistoryModelActivity>) : RecyclerView.Adapter<SurgeryAdapter.ViewHolder>() {

    private var recyclerAdapter: SurgeryAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_surgery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.surgerydestination = medicalHistoryModelActivityList[position]           ///show text/name on recyclerview
        holder.txvDestination_surgery.text = medicalHistoryModelActivityList[position].surgeryname

        holder.itemView.setOnClickListener { v ->
            val context = v.context                         // add/bind update activity
            val intent = Intent(context, SurgeryUpdateActivity::class.java)                              ///bind id/mobile_no on the base to retrive data
            intent.putExtra(SurgeryUpdateActivity.ARG_ITEM_ID, holder.surgerydestination!!.mobile_no)
            intent.putExtra("position" , position)

           // Toast.makeText(context,"tag"+holder.toString(),Toast.LENGTH_LONG).show()

            context.startActivity(intent)

            //notifyItemChanged(holder.adapterPosition)

        }
    }

    override fun getItemCount(): Int {
        return medicalHistoryModelActivityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_surgery: TextView = itemView.findViewById(R.id.txv_destination_surgery)  //item_list tv_id
        var surgerydestination: MedicalHistoryModelActivity? = null



        override fun toString(): String {
            return """${super.toString()} '${txvDestination_surgery.text}'"""
        }

    }
    fun update(view:View){
        recyclerAdapter!!.notifyDataSetChanged()
    }
}
