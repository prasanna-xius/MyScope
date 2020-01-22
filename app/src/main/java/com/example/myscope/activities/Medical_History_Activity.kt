package com.example.myscope.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.myscope.R
import com.example.myscope.activities.medical_history.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medical_history_main.*

class Medical_History_Activity : BaseActivity() {

    var images = intArrayOf(R.drawable.medical_history_navigation, R.drawable.family_history_logo, R.drawable.social_history_logo, R.drawable.diet_logo, R.drawable.allergies_logo, R.drawable.immunization_history_logo, R.drawable.medication_history_logo, R.drawable.surgery_history_logo, R.drawable.adverse_drug_reaction_logo)
    var names = arrayOf("Medical Histroy", "Family History", "Social History", "Diet", "Allergies", "Immuzination History", "Medication History", "Surgery History", "Adverse Drug Reaction")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medical_history_main)
        home_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)
        showToolbar()
        setStatusBarTopColor()
        header!!.text = "Medical History"
    }

    private inner class CustomAdapter(var context: Context, var result: Array<String>, var imageId: IntArray) : BaseAdapter() {
        private var inflater: LayoutInflater? = null
        override fun getCount(): Int { // TODO Auto-generated method stub
            return result.size
        }

        override fun getItem(position: Int): Any { // TODO Auto-generated method stub
            return position
        }

        override fun getItemId(position: Int): Long { // TODO Auto-generated method stub
            return position.toLong()
        }

        inner class Holder {
            var os_text: TextView? = null
            var os_image: ImageView? = null
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View { // TODO Auto-generated method stub
            val holder = Holder()
            val rowView: View
            rowView = inflater!!.inflate(R.layout.medical_history_grid_item, null)
            holder.os_text = rowView.findViewById<View>(R.id.home_item_textView) as TextView
            holder.os_image = rowView.findViewById<View>(R.id.home_item_image_view) as ImageView
            holder.os_text!!.text = result[position]
            holder.os_image!!.setImageResource(imageId[position])
            rowView.setOnClickListener {
                // TODO Auto-generated method stub
                if (position == 0) {
                    val i = Intent(applicationContext, Medical_History_BlogActivity::class.java)
                    startActivity(i)
                } else if (position == 1) {
                    val i = Intent(applicationContext, Family_History_Activity::class.java)
                    startActivity(i)
                } else if (position == 2) {
                    val i = Intent(applicationContext, Social_History_Activity::class.java)
                    startActivity(i)
                } else if (position == 3) {
                    val i = Intent(applicationContext, Diet_Activity::class.java)
                    startActivity(i)
                } else if (position == 4) {
                    val i = Intent(applicationContext, Allergies_Activity::class.java)
                    startActivity(i)
                } else if (position == 5) {
                    val i = Intent(applicationContext, Immunization_History_Activity::class.java)
                    startActivity(i)
                } else if (position == 6) {
                    val i = Intent(applicationContext, Medication_History_Activity::class.java)
                    startActivity(i)
                } else if (position == 7) {
                    val i = Intent(applicationContext, Surgery_History_Activity::class.java)
                    startActivity(i)
                } else if (position == 8) {
                    val i = Intent(applicationContext, Adverse_Drug_Reaction_Activity::class.java)
                    startActivity(i)
                }
                Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_SHORT).show()
            }
            return rowView
        }

        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }
}