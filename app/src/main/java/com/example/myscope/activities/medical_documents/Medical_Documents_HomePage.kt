package com.example.myscope.activities.medical_documents

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medicaldocuments_homepage_main.*

class Medical_Documents_HomePage : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medicaldocuments_homepage_main)
        medical_documents_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)
        header!!.text = "Medical Documents"
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
                }
            }
            return rowView
        }

        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    var images = intArrayOf(R.drawable.discharge1,
            R.drawable.dental1,
            R.drawable.immunization_history_logo,
            R.drawable.health_insurance,
            R.drawable.diet,
            R.drawable.education1,
            R.drawable.other_documents
           )

    var names = arrayOf("Discharge summary",
            "Dental Records",
            "Immunization",
            "Health Insurance",
            "Diet Chart",
            "Education Material",
            "Other document"
            )
}
