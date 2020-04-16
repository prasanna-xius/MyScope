package com.soargtechnologies.myscope.helpers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.medical_history.SurgeryUpdateActivity
import com.soargtechnologies.myscope.models.MedicalHistoryModelActivity

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
        holder.savedDateSurgery.text =medicalHistoryModelActivityList[position].surgery_saved_on
        holder.serialNoSurgery.text = medicalHistoryModelActivityList[position].surgery_id.toString()

        holder.itemView.setOnClickListener { v ->
            val context = v.context                         // add/bind update activity
            val intent = Intent(context, SurgeryUpdateActivity::class.java)                              ///bind id/mobile_no on the base to retrive data
            intent.putExtra(SurgeryUpdateActivity.ARG_ITEM_ID, holder.surgerydestination!!.mobile_no)
            intent.putExtra("position" , position)

            val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

            val editor: SharedPreferences.Editor = pref.edit()
            editor.putInt("surgery_id",medicalHistoryModelActivityList[position].surgery_id!!)
            editor.commit()


           // Toast.makeText(context,"tag"+holder.toString(),Toast.LENGTH_LONG).show()

            context.startActivity(intent)

            //notifyItemChanged(holder.adapterPosition)

        }
    }

    override fun getItemCount(): Int {
        return medicalHistoryModelActivityList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txvDestination_surgery: TextView = itemView.findViewById(R.id.txv_destination_surgery)
        val savedDateSurgery: TextView = itemView.findViewById(R.id.saveddate_surgery)
        val serialNoSurgery: TextView = itemView.findViewById(R.id.sn_surgery)


        //item_list tv_id
        var surgerydestination: MedicalHistoryModelActivity? = null



        override fun toString(): String {
            return """${super.toString()} '${txvDestination_surgery.text}'"""
        }

    }
    fun update(view:View){
        recyclerAdapter!!.notifyDataSetChanged()
    }
}
