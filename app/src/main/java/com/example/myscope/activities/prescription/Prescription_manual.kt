package com.example.myscope.activities.prescription

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.*
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_prescription_manual.*
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.MultiSelectionSpinner
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.prescribed_main.*
import java.text.DateFormat
import java.util.*


class Prescription_manual : BaseActivity() {

    /* declaring variables */
    internal lateinit var myCalendar: Calendar
    private var startDateOrEndDAte = true
    var formulation: Spinner? = null
    var dose: Spinner? = null
    var howoftenvalue_taken: MultiSelectionSpinner? = null
    var timeSpinner_taken: MultiSelectionSpinner? = null

    var drugname_list: AutoCompleteTextView? = null
//    var brand_name_edit: EditText? = null



    var isprescribed: Spinner? = null
    var datecheck: Boolean? = true
    var hospitalName: String? = null
    var players = ArrayList<Player>()

    var drugname: String? = null
    var doctorName: String? = null
    var brandname: String? = null
    var doseStrength: String? = null
    var medicalCondition: String? = null
    var formulationId: String? = null
    var doseunit: String? = null
    var isprescribedunit: String? = null
    var howoftenvalue: String? = null
    var timeofmedicine: String? = null
    var startDate: String? = null
    var stopDate: String? = null
//    var adapter: MyAdapter? = null
    var adapter1: Druglist_Adapter? = null

    var rv: RecyclerView? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_manual)

//        editbutton.setOnClickListener() {
//            showShortToast("edit button is clicked")
////            if (!parentview.equals(null)) {
////                parentview.getBackground().setAlpha(100);
//
////            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
////            }
//        }

        rv = findViewById<View>(R.id.myRecycler) as RecyclerView
        //SET ITS PROPS
        rv!!.layoutManager = LinearLayoutManager(this)
        rv!!.itemAnimator = DefaultItemAnimator()
        //ADAPTER
//        adapter1 = Druglist_Adapter(this, players)

        isprescribed = findViewById<Spinner>(R.id.is_prescribed)



        // prescribed spinner
        val isprescribedadapter = ArrayAdapter(this,
                R.layout.spinner_dropdown_item, resources.getStringArray(R.array.is_prescribed))
        isprescribed!!.adapter = isprescribedadapter

        isprescribed?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent!!.getItemAtPosition(position).toString()

                if (selectedItem.equals("None")) {
                    doctor_layout!!.setVisibility(View.GONE);
                    hosp_layout!!.setVisibility(View.GONE);
                    pharmacy_layout!!.setVisibility(View.GONE);
                    // do your stuff
                } else if (selectedItem.equals("Over the counter (OTC)")) {
                    doctor_layout!!.setVisibility(View.GONE);
                    hosp_layout!!.setVisibility(View.GONE);
                    pharmacy_layout!!.setVisibility(View.GONE);
                    // do your stuff
                } else if (selectedItem.equals("Prescribed")) {

                    doctor_layout!!.setVisibility(View.VISIBLE);
                    hosp_layout!!.setVisibility(View.VISIBLE);
                    pharmacy_layout!!.setVisibility(View.GONE);

                    // do your stuff
                } else if (selectedItem.equals("Prescribed OTC")) {
                    doctor_layout!!.setVisibility(View.GONE);
                    hosp_layout!!.setVisibility(View.GONE);
                    pharmacy_layout!!.setVisibility(View.VISIBLE);
                    // do your stuff
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                doctor_layout!!.setVisibility(View.GONE);
                hosp_layout!!.setVisibility(View.GONE);
                pharmacy_layout!!.setVisibility(View.GONE);

            }

        }
        prescription_save.setOnClickListener {
            save(doctor_name!!.text.toString(), hosp_name!!.text.toString())
        }
//        retrieveBtn.setOnClickListener {
////            retrieve();
////        }
        add_drug.setOnClickListener {
            showLongToast("clicked")
            showDialog()
        }

    }

    private fun showDialog() {

        val d = Dialog(this)
        //NO TITLE
        d.requestWindowFeature(Window.FEATURE_NO_TITLE)
        //layout of dialog
        d.setContentView(R.layout.prescribed_main)

        /* getting id's by findviewbyid method */
        formulation =d. findViewById<Spinner>(R.id.formulation_id)
        dose =d. findViewById<Spinner>(R.id.dose_unit)


//        dose =d. findViewById<TextView>(R.id.start_date)
//        dose =d. findViewById<TextView>(R.id.stop_date)
//        brandname =d. findViewById<EditText>(R.id.brand_name)
//        dose =d. findViewById<EditText>(R.id.dose_strength)
//        dose =d. findViewById<EditText>(R.id.medical_condition)
        drugname_list =d. findViewById<AutoCompleteTextView>(R.id.drug_name)


        val howoftenvalue_taken = d.findViewById<MultiSelectionSpinner>(R.id.how_often_taken)
        val timeSpinner_taken = d.findViewById<MultiSelectionSpinner>(R.id.time_of_taken)

// Get the array of languages
//        val drugname_list
//                = resources.getStringArray(R.array.drugs_list)
        // Create adapter and add in AutoCompleteTextView
        val adapter
                = ArrayAdapter(this,
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
                start_date.setText(date1)
            } else {
                stop_date.setText(date1)
            }
        }
        /*onclick listener for start date */
        d. start_date.setOnClickListener {
            // TODO Auto-generated method stub
            DatePickerDialog(this@Prescription_manual, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = true
        }

        /*onclick listener for stop date */
        d.stop_date.setOnClickListener {
            DatePickerDialog(this@Prescription_manual, R.style.MyDatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            startDateOrEndDAte = false

        }

        /* Implementing on click listner */
        d.prescription_save_dialog.setOnClickListener()
        {
            /*getting values from edit text views */
//            hospitalName = hosp_name.text.toString()
//            doctorName = doctor_name.text.toString()
            brandname = d.brand_name.text.toString()

            doseStrength = d.dose_strength.text.toString()
            medicalCondition = d.medical_condition.text.toString()

            drugname = d.drug_name!!.text.toString()
            formulationId = d.formulation_id!!.selectedItem.toString()
            doseunit = dose!!.selectedItem.toString()
            isprescribedunit = isprescribed!!.selectedItem.toString()
            howoftenvalue = howoftenvalue_taken!!.selectedItem.toString()
            timeofmedicine = timeSpinner_taken!!.selectedItem.toString()
            startDate =d. start_date.text.toString()
            stopDate = d.stop_date.text.toString()

            /*validating the inputs through function call */
//            validateInput(doctor_name, doctorName!!)
//            validateInput(hosp_name, hospitalName!!)
            validateInput(d.brand_name, brandname!!)
            validateInput(d.dose_strength, doseStrength!!)
            validateInput(d.medical_condition, medicalCondition!!)
            validateInput(d.drug_name, drugname!!)

            validateSpinner(formulation!!, formulationId!!)
            validateSpinner(dose!!, doseunit!!)
            validateSpinner(isprescribed!!, isprescribedunit!!)
            validateSpinner(d.how_often_taken!!, howoftenvalue!!)
            validateSpinner(d.time_of_taken!!, timeofmedicine!!)
            datecheck = validateDate(d.start_date,d.stop_date,true)

            /*checking if the data is empty or not */
            if (
//                    (!doctorName.equals("")) &&
//                    (!hospitalName.equals("")) &&
                    (!brandname.equals("")) &&
                    (!drugname.equals("")) &&

                    (!doseStrength.equals("")) &&
                    (!medicalCondition.equals("") &&
                            (!formulationId.equals("None")) &&
                            (!doseunit.equals("None")) &&
                            (!isprescribedunit.equals("None")) &&
                            (!howoftenvalue.equals("None")) &&
                            (!timeofmedicine.equals("None")) &&
                            (!startDate.equals("")) &&
                            (!stopDate.equals(""))) && datecheck!!.equals(true)) {
                showLongToast("Values are saved....Thank you!! ")
                val PrescriptionManualDataClass = PrescriptionManualDataClass(
//                        doctorName!!, hospitalName!!,
                        brandname!!,drugname!!, doseStrength!!, medicalCondition!!, formulationId!!, doseunit!!,
                        isprescribedunit!!, howoftenvalue!!, timeofmedicine!!, startDate!!, stopDate!!)
                showLongToast(PrescriptionManualDataClass.toString())
            } else {
            }
//            assignValuetoVariable()
            validate(formulation!!)
            validate(dose!!)
            validate(isprescribed!!)
//            save_alert(d.drug_name!!.text.toString(), d.brand_name!!.text.toString())
        }
//        d. prescription_update.setOnClickListener {
//            retrieve();
//        }
        d.show()
    }

    /*private fun save_alert(drugname: String, brandname: String) {

        val db = DBAdapter(this)
        //OPEN
        db.openDB()
        //INSERT
        val result = db.add(drugname, brandname,null,null)
        if (result > 0) {
            drug_name!!.setText("")
           brand_name!!.setText("")
        } else {
            Snackbar.make(drug_name!!, "Unable To Insert", Snackbar.LENGTH_SHORT).show()
        }
        //CLOSE
        db.close()
        //refresh
        retrieve();
    }*/

    private fun save(name: String, pos: String) {
        val db = DBAdapter(this)
        //OPEN
        db.openDB()
        //INSERT
        val result = db.add(name, pos)
        if (result > 0) {
            doctor_name!!.setText("")
            hosp_name!!.setText("")
        } else {
            Snackbar.make(doctor_name!!, "Unable To Insert", Snackbar.LENGTH_SHORT).show()
        }
        //CLOSE
        db.close()
        //refresh
//        retrieve();
    }

    //RETRIEVE
//    private fun retrieve() {
//        val db = DBAdapter(this)
//        //OPEN
//        db.openDB()
//        players.clear()
//        //SELECT
//        val c = db.allPlayers
//        //LOOP THRU THE DATA ADDING TO ARRAYLIST
//        while (c.moveToNext()) {
//            val id = c.getInt(0)
//            val drugname = c.getString(3)
//            val brandname = c.getString(4)
//            //CREATE PLAYER
//            val p = Player(drugname, brandname,null,null,id)
//            //ADD TO PLAYERS
//            players.add(p)
//        }
//        //SET ADAPTER TO RV
//        if (players.size >= 1) {
//            rv!!.adapter = adapter1
//        }

//    }

    override fun onResume() {
        super.onResume()
//        retrieve();
    }
    /* function for checking null values in the required fields*/
    private fun assignValuetoVariable() {


    }
}



