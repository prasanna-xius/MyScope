package com.example.myscope.activities.prescription

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.example.myscope.activities.MultiSpinnerTime
import com.example.myscope.services.PrescriptionInterface
import com.example.myscope.activities.services.ServiceBuilder1
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.prescription_multi_item.view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PrescriptionDrugListUpdate : BaseActivity() {

    var formulationid: Spinner? = null
    var time_of_taken_select: MultiSpinnerTime? = null
    var dose_unit_select: Spinner? = null
    var how_often_taken_select: MultiSelectionSpinner? = null

    var brandname: EditText? = null
    var drugname: AutoCompleteTextView? = null
    var dosestrength: EditText? = null
    var startdate: TextView? = null
    var stopdate: TextView? = null
    var howoftentext: TextView? = null
    var drug_id: Int = 0
    var prescription_idupdate :Int = 0

    var prescription_id: Int = 0
    var formulationupdate:Int = 0

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.prescribed_main_view)


        formulationid = findViewById(R.id.formulation_id_update)
        howoftentext = findViewById(R.id.prescription_multi)
//        time_of_taken_select =  findViewById(R.id.time_of_taken_update)
        dose_unit_select = findViewById(R.id.dose_unit_update)
//        how_often_taken_select =findViewById(R.id.how_often_taken_update)
        brandname = findViewById<View>(R.id.brand_name_update) as EditText
        drugname = findViewById<View>(R.id.drug_name_update) as AutoCompleteTextView
        dosestrength = findViewById<View>(R.id.dose_strength) as EditText
        startdate = findViewById<View>(R.id.start_date) as TextView
        stopdate = findViewById<View>(R.id.stop_date) as TextView

        how_often_taken_select = findViewById<MultiSelectionSpinner>(R.id.how_often_taken_update)
        time_of_taken_select = findViewById<MultiSpinnerTime>(R.id.time_of_taken_update)

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
            if (!bundle.equals(null)) {
                drug_id = bundle!!.getInt("drug_id")
            }
        }

        val diseaseService = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        val requestCall = diseaseService.getDrugListbyId(drug_id)

        requestCall.enqueue(object : Callback<List<PrescriptionDataClass>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */
            override fun onResponse(call: Call<List<PrescriptionDataClass>>, resp: Response<List<PrescriptionDataClass>>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body()
                    val prescriptiondrug = newbody?.first()
                    // Use it or ignore it
                    formulationupdate = formulationadapter.getPosition(prescriptiondrug!!.formulation);
                    formulation_id_update.setSelection(formulationupdate);
//                    formulationid!!.text1.setText(prescriptiondrug!!.formulation)
                    prescription_idupdate = prescriptiondrug.prescription_id!!
                    brandname!!.setText(prescriptiondrug!!.brand_name)
                    drugname!!.setText(prescriptiondrug.drug_name)
                    dosestrength!!.setText(prescriptiondrug.dose_strength)
                    startdate!!.setText(prescriptiondrug.start_date)
                    stopdate!!.setText(prescriptiondrug.stop_date)

                    val dose_unit_take = doseunitadapter.getPosition(prescriptiondrug.dose_unit);
                    dose_unit_update.setSelection(dose_unit_take);

                    Toast.makeText(applicationContext, "API success" + prescriptiondrug!!.formulation, Toast.LENGTH_SHORT).show()
                    how_often_taken_select!!.text1?.setText(prescriptiondrug.how_often_taken)
                    time_of_taken_select!!.prescription_multi?.setText(prescriptiondrug!!.time)
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<PrescriptionDataClass>>, t: Throwable) {

                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })
        prescription_update_dialog.setOnClickListener() {


            val item = formulationid!!.text1.text.toString()
            showLongToast((item))
            val timeitem = time_of_taken_select!!.prescription_multi.text.toString()
            showLongToast((timeitem))
            val howoftenitem = how_often_taken_select!!.text1.text.toString()
            showLongToast((howoftenitem))
            val newPrescriptionDrug = PrescriptionDataClass()
            if (!item.equals(null)) {
                newPrescriptionDrug.formulation = item
                val formulation_name = formulationadapter.getPosition(newPrescriptionDrug.formulation);
                formulation_id_update.setSelection(formulation_name);
            } else {
                newPrescriptionDrug.formulation = formulationid?.selectedItem.toString()
                showLongToast(newPrescriptionDrug.formulation!!)

//                val formulation_name = formulationadapter.getPosition(newPrescriptionDrug!!.formulation);
//                formulation_id_update.setSelection(formulation_name);

            }
//            newPrescriptionDrug.formulation = formulation_id?.getSelectedItem().toString()
            newPrescriptionDrug.dose_unit = dose_unit_update?.getSelectedItem().toString()

            if (!timeitem.equals(null)) {
                newPrescriptionDrug.time = timeitem
                time_of_taken_select!!.prescription_multi?.setText(newPrescriptionDrug.time)
            } else {
                newPrescriptionDrug.time = time_of_taken_select!!.selectedItemsAsString
                time_of_taken_select!!.prescription_multi?.setText(newPrescriptionDrug!!.time)
            }
            if (!howoftenitem.equals(null)) {
                    newPrescriptionDrug.how_often_taken = howoftenitem
                    how_often_taken_select!!.text1?.setText(newPrescriptionDrug.how_often_taken)
                } else {
                    newPrescriptionDrug.how_often_taken = how_often_taken_select!!.selectedItemsAsString
                    how_often_taken_select!!.text1?.setText(newPrescriptionDrug.how_often_taken)
                }
//            how_often_taken_select!!.text1?.setText(newPrescriptionDrug.how_often_taken)

                newPrescriptionDrug.drug_name = drugname!!.text.toString().trim()
                newPrescriptionDrug.brand_name = brandname!!.text.toString().trim()
                newPrescriptionDrug.dose_strength = dosestrength!!.text.toString().trim()
                newPrescriptionDrug.start_date = startdate!!.text.toString().trim()
                newPrescriptionDrug.stop_date = stopdate!!.text.toString().trim()
                newPrescriptionDrug.drug_id = drug_id

                val prescriptionDrugservice = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

                //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)

                val requestCall = prescriptionDrugservice.updateDrug(newPrescriptionDrug)

                requestCall.enqueue(object : Callback<PrescriptionDataClass> {
                    /**
                     * Invoked when a network exception occurred talking to the server or when an unexpected
                     * exception occurred creating the request or processing the response.
                     */
                    override fun onResponse(call: Call<PrescriptionDataClass>, resp: Response<PrescriptionDataClass>) {

                        if (resp.isSuccessful) {
                            var newbody = resp.body() // Use it or ignore it
                            Toast.makeText(applicationContext, "Successfully Added" + newbody, Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, Prescription_manualDrugDialog::class.java)
                            val bundle = Bundle()
                            bundle.putInt("prescription_id", prescription_idupdate)
                            intent.putExtras(bundle)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {

                        Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }
    }









