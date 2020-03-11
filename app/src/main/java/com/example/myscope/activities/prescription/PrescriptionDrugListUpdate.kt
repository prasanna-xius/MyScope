package com.example.myscope.activities.prescription

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.example.myscope.activities.MultiSelectionSpinnerTime
import kotlinx.android.synthetic.main.prescribed_main.*
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.prescription_multi_item.*
import kotlinx.android.synthetic.main.prescription_multi_item.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.*
import kotlinx.android.synthetic.main.spinner_item.*


class PrescriptionDrugListUpdate: BaseActivity() {

    var formulationid: Spinner? = null
    var time_of_taken_select: MultiSelectionSpinnerTime? = null
    var dose_unit_select: Spinner? = null
    var how_often_taken_select: MultiSelectionSpinner? = null

    var brandname: EditText? = null
    var drugname: AutoCompleteTextView? = null
    var dosestrength: EditText? = null
    var startdate: TextView? = null
    var stopdate: TextView? = null

    var prescription_id:Int =0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescribed_main_view)

        formulationid = findViewById(R.id.formulation_id_update)
//        time_of_taken_select =  findViewById(R.id.time_of_taken_update)
        dose_unit_select=  findViewById(R.id.dose_unit_update)
//        how_often_taken_select =findViewById(R.id.how_often_taken_update)

        brandname = findViewById<View>(R.id.brand_name) as EditText
        drugname = findViewById<View>(R.id.drug_name) as AutoCompleteTextView
        dosestrength = findViewById<View>(R.id.dose_strength) as EditText
        startdate = findViewById<View>(R.id.start_date) as TextView
        stopdate = findViewById<View>(R.id.stop_date) as TextView

        how_often_taken_select = findViewById<MultiSelectionSpinner>(R.id.how_often_taken_update)
        time_of_taken_select = findViewById<MultiSelectionSpinnerTime>(R.id.time_of_taken_update)

// Get the array of languages

        val adapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.drugs_list))
        drugname!!.setAdapter(adapter)

        /*Custom spinner code*/
        how_often_taken_select?.setItems(resources.getStringArray(R.array.how_often_taken))
        time_of_taken_select?.setItems(resources.getStringArray(R.array.timings))

        // formulation spinner
        val formulationadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.formulation_array))
        formulationid!!.adapter = formulationadapter

        // dose unit spinner
        val doseunitadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.dose_unit))
        dose_unit_select!!.adapter = doseunitadapter


        val bundle = intent.extras
        if (bundle != null) {
            if(!bundle.equals(null)) {

                var formulation = bundle!!.getString("formulation")
                val drug_name = bundle!!.getString("drug_name")
                val brand_name = bundle!!.getString("brand_name")

                val dose_strength = bundle!!.getString("dose_strength")
                val dose_unit = bundle!!.getString("dose_unit")
                val how_often_taken = bundle!!.getString("how_often_taken")!!
                val time = bundle!!.getString("time")!!

                val start_date = bundle!!.getString("start_date")
                val stop_date = bundle!!.getString("stop_date")

                val formulation_name = formulationadapter.getPosition(formulation);
                formulation_id_update.setSelection(formulation_name);
                showLongToast(how_often_taken)

//                multi.setText(time)
                val dose_unit_take = doseunitadapter.getPosition(dose_unit);
                dose_unit_update.setSelection(dose_unit_take);

                prescription_id = bundle!!.getInt("prescription_id")

                brandname!!.setText(brand_name)
                drugname!!.setText(drug_name)
                dosestrength!!.setText(dose_strength)
                startdate!!.setText(start_date)
                stopdate!!.setText(stop_date)
                prescription_multi?.setText(how_often_taken)
                multi?.setText(time)
            }
        }
    }

}
