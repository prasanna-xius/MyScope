package com.soargtechnologies.myscope.activities.labReports

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.labreports_homepage_main.*
import kotlinx.android.synthetic.main.medicaldocuments_homepage_main.*

class Lab_Reports_Homepage : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.labreports_homepage_main)
        activitiesToolbar()
        labreports_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)
        activitiesToolbar()
        header!!.text = "Lab Reports"
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
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 1) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 2) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 3) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 4) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 5) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 6) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 7) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 8) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                }else if (position == 9) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 10) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                } else if (position == 11) {
//                    navigateToActivity(Intent(applicationContext, Medical_History::class.java))
                    showPictureDialogReports()
                }
            }
            return rowView
        }
        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }
    var images = intArrayOf(R.drawable.blood_report,
            R.drawable.urine_report,
            R.drawable.ultrasound_icon,
            R.drawable.xray,
            R.drawable.ctscan,
            R.drawable.mri,
            R.drawable.ecg,
            R.drawable.echo,
            R.drawable.stress_test,
            R.drawable.colonoscopy,
            R.drawable.other_reports)
    var names = arrayOf("Blood Reports",
            "Urine Report",
            "UltraSound",
            "X-Ray",
            "CT-Scan",
            "MRI",
            "ECG",
            "ECHO",
            "Stress Test",
            "ColonoScopy",
            "Others")

}
