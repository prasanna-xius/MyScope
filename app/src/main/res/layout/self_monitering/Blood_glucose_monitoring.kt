//package com.soargtechnologies.myscope.activities.self_monitering
//
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import android.widget.Spinner
//import com.soargtechnologies.myscope.R
//import com.soargtechnologies.myscope.activities.BaseActivity
//import kotlinx.android.synthetic.main.activity_blood_glucose_monitoring.*
//import kotlinx.android.synthetic.main.app_bar_main.*
//
//class Blood_glucose_monitoring : BaseActivity() {
//
//    var spinner_blood: Spinner? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_blood_glucose_monitoring)
//
//        activitiesToolbar()
//
//        header!!.text = "Blood Glucose Monitoring"
//        spinner_blood = findViewById<Spinner>(R.id.spinner_test)
//
//        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
//                resources.getStringArray(R.array.blood_glucose_dropdown))
//        spinner_blood!!.adapter = adapter
//    }
//    private fun assignValuestoVariable() {
//
//        val date_of_Test = date_of_test.text.toString()
//        val Test = spinner_test!!.selectedItem.toString()
//        val Test_result = test_result.text.toString()
//      //  validateInput(date_of_test, condition)
//        validateInput(test_result , Test_result)
//        validateSpinner(spinner_test!!, Test)
//
//        if ((date_of_Test != "") &&
//                (Test != "None")
//                && (Test_result != "")) {
//            showLongToast("save the details")
//        } else {
//
//            showLongSnackBar("Please fill the required fields")
//
//        }
//    }
//}
