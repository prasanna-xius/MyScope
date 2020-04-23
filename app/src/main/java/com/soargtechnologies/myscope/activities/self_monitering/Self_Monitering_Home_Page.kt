package com.soargtechnologies.myscope.activities.self_monitering

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import com.soargtechnologies.myscope.activities.medical_history.*
import kotlinx.android.synthetic.main.activity_self__monitering__home__page.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medical_history_main.*

class Self_Monitering_Home_Page : BaseActivity() {



    var images = intArrayOf(R.drawable.medical_history_navigation, R.drawable.family_history_logo, R.drawable.social_history_logo, R.drawable.diet_logo, R.drawable.allergies_logo, R.drawable.immunization_history_logo)
    var names = arrayOf("Blood Glucose", "Blood Pressure", "Cholesterol", "Bmi", "Exercise Tracker", "Emotional State")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self__monitering__home__page)
        home_grid_view_self!!.adapter = CustomAdapter(applicationContext, names, images)

        activitiesToolbar()
        header!!.text = "Self Monitering"

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
            rowView = inflater!!.inflate(R.layout.gridview_item_main, null)
            holder.os_text = rowView.findViewById<View>(R.id.home_item_textView) as TextView
            holder.os_image = rowView.findViewById<View>(R.id.home_item_image_view) as ImageView
            holder.os_text!!.text = result[position]
            holder.os_image!!.setImageResource(imageId[position])
            rowView.setOnClickListener {
                // TODO Auto-generated method stub
                if (position == 0) {
                    navigateToActivity(Intent(applicationContext,  Blood_Glucose_recyclerView::class.java))
                } else if (position == 1) {
                    navigateToActivity(Intent(applicationContext, Blood_Pressure_recyclerView::class.java))
                } else if (position == 2) {
                    navigateToActivity(Intent(applicationContext, Cholestrol_recyclerView::class.java))
                } else if (position == 3) {
                    navigateToActivity(Intent(applicationContext, Bmi_recyclerView::class.java))
                } else if (position == 4) {
                    navigateToActivity(Intent(applicationContext, Exercise_tracker::class.java))
                } else if (position == 5) {
                    navigateToActivity(Intent(applicationContext, Emotional_state_recyclerView::class.java))
                }
            }
            return rowView
        }

        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

}
