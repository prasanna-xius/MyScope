package com.soargtechnologies.myscope.activities

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
import com.soargtechnologies.myscope.activities.medical_history.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.educational_blog_main.*
import kotlinx.android.synthetic.main.medical_history_main.*

class Educational_Blog_Activity : BaseActivity() {

    var images = intArrayOf(R.drawable.course_training,
            R.drawable.guidelines,
            R.drawable.corona_virus,
            R.drawable.news_updates)
    var names = arrayOf("Courses & Training", "Guidelines", "COVID-19 Resourses", "News & Updates")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.educational_blog_main)

        educational_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)

        activitiesToolbar()
        header1!!.text = "Educational Blog"

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
                    navigateToActivity(Intent(applicationContext, Courses_Training_Activity::class.java))
                } else if (position == 1) {
                    navigateToActivity(Intent(applicationContext, Guidelines_Activity::class.java))
                } else if (position == 2) {
                    navigateToActivity(Intent(applicationContext, Covid_19_Resourses_Activity::class.java))
                } else if (position == 3) {
                    navigateToActivity(Intent(applicationContext, News_Updates_Activity::class.java))
                }
            }
            return rowView
        }

        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

}
