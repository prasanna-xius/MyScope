package com.example.myscope.activities.prescription

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myscope.R
import com.example.myscope.activities.*
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import kotlinx.android.synthetic.main.prescribed_main.*
import kotlinx.android.synthetic.main.prescribed_main.brand_name
import kotlinx.android.synthetic.main.prescribed_main.dose_strength
import kotlinx.android.synthetic.main.prescribed_main.drug_name
import kotlinx.android.synthetic.main.prescribed_main.prescription_save_dialog
import kotlinx.android.synthetic.main.prescribed_main.start_date
import kotlinx.android.synthetic.main.prescribed_main.stop_date
import kotlinx.android.synthetic.main.prescribed_main_view.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.util.*


class Prescription_manualDrugDialog : BaseActivity() {

    /* declaring variables */
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var formulation: Spinner? = null
    var dose: Spinner? = null
    var prescription:Int = 0

    var drugname_list: AutoCompleteTextView? = null

    var doctor_name_Txt: EditText? = null
    var hospital_name_Txt: EditText? = null

    var isprescribed: Spinner? = null
    var datecheck: Boolean? = true
    var layout:LinearLayout ?= null

    var drugname: String? = null
    var brandname: String? = null
    var doseStrength: String? = null
    var formulationId: String? = null
    var doseunit: String? = null
    var howoftenvalue: String? = null
    var timeofmedicine: String? = null
    var startDate: String? = null
    var stopDate: String? = null
    var mobile_no:String?=null
    var pos:Int = 0
    var isPrescribed: Spinner? = null


    var rv: RecyclerView? = null
    var prescription_id:Int =0
    var position:Int = 0

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_manual)
        doctor_name_Txt = findViewById<View>(R.id.et_doctor_name) as EditText
        hospital_name_Txt = findViewById<View>(R.id.et_hosp_name) as EditText
        isPrescribed = findViewById(R.id.is_prescribed)as Spinner
        layout = findViewById(R.id.doctor_layout)as LinearLayout


        val isprescribedadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.is_prescribed))
        isPrescribed!!.adapter = isprescribedadapter

        val bundle = intent.extras
            if(!bundle!!.equals(null)) {

               pos = bundle.getInt("doctorposition")

                val doctor_name = bundle!!.getString("doctor_name")
                val hospital_name = bundle!!.getString("hospital_name")
                val isPrescribed = bundle!!.getString("isPrescribed")

                prescription_id = bundle!!.getInt("prescription_id")

                doctor_name_Txt!!.setText(doctor_name)
                hospital_name_Txt!!.setText(hospital_name)
                val prescribedname = isprescribedadapter.getPosition(isPrescribed);
                is_prescribed.setSelection(prescribedname);
                if(prescribedname == 2){
                    layout!!.toggleVisibility()
                    hosp_layout.toggleVisibility()

                } else {
                    layout!!.toggleVisibility()
                    hosp_layout.toggleVisibility()
                }
            }


        add_drug.setOnClickListener {
            showLongToast("clicked")
            showDialog()
        }

    }

    override fun onBackPressed() {

        navigateToActivity(Intent(applicationContext,Prescription_ManualDoctorDialog::class.java))
    }
    private fun showDialog() {

        val d = Dialog(this)
        //NO TITLE
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //layout of dialog
        d.setContentView(R.layout.prescribed_main)

        /* getting id's by findviewbyid method */
        formulation = d.findViewById<Spinner>(R.id.formulation_id)
        dose = d.findViewById<Spinner>(R.id.dose_unit)


        drugname_list = d.findViewById<AutoCompleteTextView>(R.id.drug_name)


        val howoftenvalue_taken = d.findViewById<MultiSelectionSpinner>(R.id.how_often_taken)
        val timeSpinner_taken = d.findViewById<MultiSpinnerTime>(R.id.time_of_taken)

// Get the array of languages

        val adapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.drugs_list))
        drugname_list!!.setAdapter(adapter)

        /*Custom spinner code*/
        howoftenvalue_taken.setItems(resources.getStringArray(R.array.how_often_taken))
        timeSpinner_taken.setItems(resources.getStringArray(R.array.timings))

        // formulation spinner
        val formulationadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.formulation_array))
        formulation!!.adapter = formulationadapter

        // dose unit spinner
        val doseunitadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.dose_unit))
        dose!!.adapter = doseunitadapter
        /*Start date amd stop date validations */
        myCalendar = Calendar.getInstance()

        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val date1 = DateFormat.getDateInstance().format(myCalendar.getTime())
            if (startDateOrEndDAte) {
                d.start_date.setText(date1)
            } else {
                d.stop_date.setText(date1)
            }
        }
        /*onclick listener for start date */
        d.start_date.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@Prescription_manualDrugDialog, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        /*onclick listener for stop date */
        d.stop_date.setOnClickListener {
            DatePickerDialog(this@Prescription_manualDrugDialog, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false

        }

        /* Implementing on click listner */
        d.prescription_save_dialog.setOnClickListener()
        {

            brandname = d.brand_name.text.toString()
            doseStrength = d.dose_strength.text.toString()
            drugname = d.drug_name!!.text.toString()
            formulationId = d.formulation_id!!.selectedItem.toString()
            doseunit = d.dose_unit!!.selectedItem.toString()
            howoftenvalue = howoftenvalue_taken!!.selectedStrings.toString()
            timeofmedicine = timeSpinner_taken!!.selectedStrings.toString()
            startDate = d.start_date.text.toString()
            stopDate = d.stop_date.text.toString()

            validateInput(d.brand_name, brandname!!)
            validateInput(d.dose_strength, doseStrength!!)
            validateInput(d.drug_name, drugname!!)

            validateSpinner(d.formulation_id!!, formulationId!!)
            validateSpinner(d.dose_unit!!, doseunit!!)
            validateSpinner(d.how_often_taken!!, howoftenvalue!!)
            validateSpinner(d.time_of_taken!!, timeofmedicine!!)
            datecheck = validateDate(d.start_date, d.stop_date, true)

            /*checking if the data is empty or not */
            if (
//                    (!doctorName.equals("")) &&
//                    (!hospitalName.equals("")) &&
                    (!brandname.equals("")) &&
                    (!drugname.equals("")) &&
                    (!doseStrength.equals("")) &&
                            (!formulationId.equals("None")) &&
                            (!doseunit.equals("None")) &&
                            (!howoftenvalue.equals("None")) &&
                            (!timeofmedicine.equals("None")) &&
                            (!startDate.equals("")) &&
                            (!stopDate.equals("")) && datecheck!!.equals(true)) {
                showLongToast("Values are saved....Thank you!! ")
//                val PrescriptionDataClass = PrescriptionDataClass(
////                        doctorName!!, hospitalName!!,
//                        brandname!!, drugname!!, doseStrength!!,  formulationId!!, doseunit!!,
//                         howoftenvalue!!, prescription_id,startDate!!, stopDate!!,timeofmedicine)
//                showLongToast(PrescriptionDataClass.toString())
            }
            else {
            }
            validate(formulation!!)
            validate(dose!!)

            val newPrescriptionDrug = PrescriptionDataClass()

            newPrescriptionDrug.formulation = d.formulation_id?.getSelectedItem().toString()
            newPrescriptionDrug.dose_unit = d.dose_unit?.getSelectedItem().toString()
            newPrescriptionDrug.how_often_taken = d.how_often_taken.selectedItemsAsString

            newPrescriptionDrug.drug_name = d.drug_name!!.text.toString().trim()
            newPrescriptionDrug.brand_name = d.brand_name!!.text.toString().trim()
            newPrescriptionDrug.dose_strength = d.dose_strength!!.text.toString().trim()
            newPrescriptionDrug.start_date = d.start_date!!.text.toString().trim()
            newPrescriptionDrug.stop_date = d.stop_date!!.text.toString().trim()
            newPrescriptionDrug.time = d.time_of_taken!!.selectedItemsAsString


            newPrescriptionDrug.prescription_id =prescription_id

            val prescriptionDrugservice = ServiceBuilder1.buildService(PrescriptionInterface::class.java)

            //val requestCall =allergyService.addAllergy(name!!,reaction!!,treatment!!,notes!!,date!!,sprdata!!)

            val requestCall = prescriptionDrugservice.addDrug(newPrescriptionDrug)

            requestCall.enqueue(object : Callback<PrescriptionDataClass> {
                /**
                 * Invoked when a network exception occurred talking to the server or when an unexpected
                 * exception occurred creating the request or processing the response.
                 */
                override fun onResponse(call: Call<PrescriptionDataClass>, resp: Response<PrescriptionDataClass>) {

                    if (resp.isSuccessful) {
                        var newbody = resp.body() // Use it or ignore it
                        Toast.makeText(applicationContext, "Successfully Added" + newbody, Toast.LENGTH_SHORT).show()
                        val intent = intent
                        finish()
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PrescriptionDataClass>, t: Throwable) {
                    //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
                    Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
                }
            })

        }

        d.show()
    }

    override fun onResume() {
        super.onResume()
        loadValues()

//                retrieve();
    }

    private fun loadValues() {

        val destinationService = ServiceBuilder1.buildService(PrescriptionInterface::class.java)
        rv = findViewById<View>(R.id.recyclerview_doctorlist) as RecyclerView

        val filter = HashMap<String, String>()
//        filter["country"] = "India"
//        filter["count"] = "1"

        val requestCall = destinationService.getAllDrugsByPID(prescription_id)          ///service file method called (binding)

        //val requestCall = destinationService.getAllergy(filter)

        requestCall.enqueue(object : Callback<List<PrescriptionDataClass>> {
            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             */


            // If you receive a HTTP Response, then this method is executed
            // Your STATUS Code will decide if your Http Response is a Success or Error
            override fun onResponse(call: Call<List<PrescriptionDataClass>>, response: Response<List<PrescriptionDataClass>>) {


                if (response.isSuccessful()) {

                    val prescriptionList = response.body()!!

                    val linearlayoutmanager = LinearLayoutManager(applicationContext)

                    linearlayoutmanager.orientation = LinearLayoutManager.VERTICAL


                    rv!!.setLayoutManager(linearlayoutmanager)
                    rv!!.adapter = Prscription_DrugList_Adapter(prescriptionList)

                    rv!!.adapter!!.notifyDataSetChanged()


                    Log.e("errpr msg resp succ", response.message())

                } else if (response.code() == 401) {
                    Toast.makeText(this@Prescription_manualDrugDialog, "Your session has expired. Please Login again.", Toast.LENGTH_LONG).show()
                } else { // Application-level failure
                    // Your status code is in the range of 300's, 400's and 500's
                    Toast.makeText(this@Prescription_manualDrugDialog, "Failed to retrieve items123", Toast.LENGTH_LONG).show()
                }
            }

            // Invoked in case of Network Error or Establishing connection with Server
            // or Error Creating Http Request or Error Processing Http Response
            override fun onFailure(call: Call<List<PrescriptionDataClass>>, t: Throwable) {

                Toast.makeText(this@Prescription_manualDrugDialog, "Error Occurred" + t.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

}

private fun LinearLayout.toggleVisibility() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    } else {
        visibility = View.VISIBLE
    }
}

