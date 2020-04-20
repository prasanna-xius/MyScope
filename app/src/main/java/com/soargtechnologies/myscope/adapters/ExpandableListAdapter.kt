package com.soargtechnologies.myscope.adapters

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.soargtechnologies.myscope.R
import java.util.*

class ExpandableListAdapter(private val context: Context, private val expandableListTitle: List<String>,
                            private val expandableListDetail: HashMap<String, List<String>>) : BaseExpandableListAdapter() {
    override fun getChild(listPosition: Int, expandedListPosition: Int): Any {
        return expandableListDetail[expandableListTitle[listPosition]]!!.get(expandedListPosition)
    }
    override fun getChildId(listPosition: Int, expandedListPosition: Int): Long {
        return expandedListPosition.toLong()
    }
    override fun getChildView(listPosition: Int, expandedListPosition: Int,
                              isLastChild: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val expandedListText = getChild(listPosition, expandedListPosition) as String
        if (convertView == null) {
            val layoutInflater = context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_item, null)
        }
        val expandedListTextView = convertView!!.findViewById<View>(R.id.expandedListItem) as TextView
        expandedListTextView.text = expandedListText
        return convertView
    }
    override fun getChildrenCount(listPosition: Int): Int {
        return expandableListDetail[expandableListTitle[listPosition]]!!.size
    }
    override fun getGroup(listPosition: Int): Any {
        return expandableListTitle[listPosition]
    }
    override fun getGroupCount(): Int {
        return expandableListTitle.size
    }
    override fun getGroupId(listPosition: Int): Long {
        return listPosition.toLong()
    }
    override fun getGroupView(listPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val listTitle = getGroup(listPosition) as String
        if (convertView == null) {
            val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.list_group, null)
        }
        val listTitleTextView = convertView!!.findViewById<View>(R.id.listTitle) as TextView
        listTitleTextView.setTypeface(null, Typeface.NORMAL)
        listTitleTextView.text = listTitle
        val listTitleTextArrowView = convertView.findViewById<View>(R.id.listTitleArrow) as ImageView
        // set icons for menu items
        val listTitleTextIconView = convertView.findViewById<View>(R.id.listTitleIcon) as ImageView
        if (listPosition == Dash_Board) {
            listTitleTextIconView.setImageResource(R.drawable.dashboard)
        } else if (listPosition == Medical_History) {
            listTitleTextIconView.setImageResource(R.drawable.medical_history_navigation)
        } else if (listPosition == Presccriptions) {
            listTitleTextIconView.setImageResource(R.drawable.medical_prescription_navigation)
        } else if (listPosition == Medical_Documents) {
            listTitleTextIconView.setImageResource(R.drawable.medical_documents_navigation)
        } else if (listPosition == Lab_Reports) {
            listTitleTextIconView.setImageResource(R.drawable.lab_report_navigation)
        } else if (listPosition == Self_Monitoring) {
            listTitleTextIconView.setImageResource(R.drawable.self_monitoring_navigation)
        } else if (listPosition == Educational_Blog) {
            listTitleTextIconView.setImageResource(R.drawable.educational_blog_navigation)
        } else if (listPosition == Appointments) {
            listTitleTextIconView.setImageResource(R.drawable.appointments_navigation)
        } else if (listPosition == Services) {
            listTitleTextIconView.setImageResource(R.drawable.services_navigation)
        } else if (listPosition == ContactUs) {
            listTitleTextIconView.setImageResource(R.drawable.contactus_navigation)
        }
        // set arrow icons for relevant items
        if (listPosition == Medical_History || listPosition == Presccriptions || listPosition == Medical_Documents || listPosition == Lab_Reports || listPosition == Self_Monitoring) {
            if (!isExpanded) { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else if (!isExpanded) { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else if (!isExpanded) { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else if (!isExpanded) { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else if (!isExpanded) { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_down_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp))
            } else { //                listTitleTextArrowView.setImageResource(R.drawable.ic_keyboard_arrow_up_black_24dp);
                listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp))
            }
        } else {
            listTitleTextArrowView.setImageDrawable(context.resources.getDrawable(R.drawable.ic_keyboard_arrow_down_white_24dp))
        }
        return convertView
    }
    override fun hasStableIds(): Boolean {
        return false
    }
    override fun isChildSelectable(listPosition: Int, expandedListPosition: Int): Boolean {
        return true
    }

    companion object {
        var Dash_Board = 0
        var Medical_History = 1
        var Presccriptions = 2
        var Medical_Documents = 3
        var Lab_Reports = 4
        var Self_Monitoring = 5
        var Educational_Blog = 6
        var Appointments = 7
        var Services = 8
        var ContactUs = 9

        var Medical_History1 = 0
        var Family_History = 1
        var Social_History = 2
        var Diet = 3
        var Allergies = 4
        var Immunization_History = 5
        var Medication_History = 6
        var Surgery_History = 7
        var Adverse_Drug_Reaction = 8

        var Prescription = 0
        var Antibiotic = 1
        var Ayurveda = 2

        var Discharge_Summery = 0
        var Dental_Recards = 1
        var Immuzination = 2
        var Health_Insurance = 3
        var Diet_Chart = 4
        var Education_Material = 5
        var Other_Document = 6

        var Blood_Reports = 0
        var Urine_Report = 1
        var Ultra_Sound = 2
        var X_Ray = 3
        var CT_Scan = 4
        var MRI = 5
        var ECG = 6
        var ECHO = 7
        var Stress_Test = 8
        var SonoGraphy = 9
        var Colono_Scopy = 10
        var Others = 11

        var Blood_Glucose_Monitoring = 0
        var Blood_Pressure = 1
        var Cholostrol = 2
        var Weight = 3
        var Exercise_Tracker = 4
        var SpO2 = 5
    }
}