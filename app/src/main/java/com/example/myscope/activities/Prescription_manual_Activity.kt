package com.example.myscope.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.medical_history.Diet
import com.example.myscope.activities.medical_history.Family_History
import com.example.myscope.activities.medical_history.Medical_History
import com.example.myscope.activities.medical_history.Social_History
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medical_history_main.*

class Prescription_manual_Activity : BaseActivity() {

    var images = intArrayOf(R.drawable.helopathy, R.drawable.homeopathy, R.drawable.ayurveda)
    var names = arrayOf("Allopathy", "Homeopathy", "Ayurveda")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescriptions_drug_main)
        home_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)
        activitiesToolbar()
        header!!.text = "Prescriptions"
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
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialog()
                } else if (position == 1) {
//                    navigateToActivity(Intent(applicationContext, Family_History::class.java))
                    showPictureDialog()

                } else if (position == 2) {
//                    navigateToActivity(Intent(applicationContext, Social_History::class.java))
                    showPictureDialog()

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
