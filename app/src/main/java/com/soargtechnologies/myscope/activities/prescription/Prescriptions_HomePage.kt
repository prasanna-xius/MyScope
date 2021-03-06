package com.soargtechnologies.myscope.activities.prescription

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.medical_history_main.*

class Prescriptions_HomePage : BaseActivity() {

    var images = intArrayOf(R.drawable.helopathy, R.drawable.homeopathy, R.drawable.ayurveda)
   var names = arrayOf("Allopathy", "Homeopathy", "Ayurveda")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescriptions_drug_main)
        home_grid_view!!.adapter = CustomAdapter(applicationContext, names, images)

        activitiesToolbar()

        header!!.text = "Prescription"
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
                    val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

                    val editor: SharedPreferences.Editor = pref.edit()

                    editor.putString("model_name","Allopathy")
                    editor.commit()
                    addPrescriptionListDialog()

//                    navigateToActivity(Intent(applicationContext, Disease_History::class.java))
                    //   showPictureDialog()

                } else if (position == 1) {
//                    navigateToActivity(Intent(applicationContext, Family_History::class.java))
                    val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

                    val editor: SharedPreferences.Editor = pref.edit()
                    editor.putString("model_name","Homeopathy")
                    editor.commit()

                    addPrescriptionListDialog()

                } else if (position == 2) {
//                    navigateToActivity(Intent(applicationContext, Social_History::class.java))
                    val pref = context!!.getSharedPreferences("MyPref", Context.MODE_PRIVATE) // 0 - for private mode

                    val editor: SharedPreferences.Editor = pref.edit()
                    editor.putString("model_name","Ayurveda")
                    editor.commit()

                    addPrescriptionListDialog()
                }
            }
//            editor.commit()
            return rowView
        }
        init { // TODO Auto-generated constructor stub
            inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
    }

    private fun addPrescriptionListDialog() {

        val pictureDialog = AlertDialog.Builder(this,R.style.Alert_Dialogue_Background)
        pictureDialog.setTitle("Select Action")

        val pictureDialogItems = arrayOf(
                "Add Images /PDF Files",
                "Manual Entry")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {

                0-> showAddImage()
                1 -> showAddFilemanual()

            }
        }
        pictureDialog.show()
    }

    private fun showAddFilemanual() {

        navigateToActivity(Intent(applicationContext, Prescription_ManualDoctorDialog::class.java))

    }

    private fun showAddImage() {

        navigateToActivity(Intent(applicationContext, Prescription_AddImage_PDF::class.java))

    }

}
