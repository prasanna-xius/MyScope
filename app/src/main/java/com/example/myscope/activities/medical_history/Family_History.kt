package com.example.myscope.activities.medical_history


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import com.google.android.gms.security.ProviderInstaller
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.family_history.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.net.ssl.SSLContext

class Family_History : BaseActivity() {


    var relationshipSpinner: Spinner? = null
    var mobile_no: String? = null
    var sharedpreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_history)

        activitiesToolbar()

        header!!.text = "Family History"

        sharedpreferences = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        mobile_no = sharedpreferences!!.getString("mobile_no", null)
        showLongToast(mobile_no.toString())

        relationshipSpinner = findViewById<Spinner>(R.id.spinner_family)

        val adapter = ArrayAdapter(this, R.layout.spinner_dropdown_item,
                resources.getStringArray(R.array.Relationship_arrays))
        relationshipSpinner!!.adapter = adapter


        btn_family.setOnClickListener {

            assignValuestoVariable()
            validate(relationshipSpinner!!)


        }
    }

        private fun assignValuestoVariable() {

            var familyCondition = et_family_condition.text.toString()
            var relationship = relationshipSpinner!!.selectedItem.toString()
            validateInput(et_family_condition, familyCondition)
            validateSpinner(relationshipSpinner!!, relationship)

            if ((familyCondition != "") &&
                    (relationship != "None")) {

                showLongToast("save the details")

                sucess()

        }

        }

    private fun sucess() {


        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            var sslContext: SSLContext
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        }

        catch (e: Exception) {
            e.printStackTrace();
        }

        val newFamilyCondition = Diseases()
        newFamilyCondition.family_condition = et_family_condition!!.text.toString().trim()
        newFamilyCondition.relationship = spinner_family?.getSelectedItem().toString()
        newFamilyCondition.family_note = relationship_notes!!.text.toString().trim()
        newFamilyCondition.mobile_no = mobile_no!!

        val familyService = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = familyService.addFamilyList(newFamilyCondition)

        requestCall.enqueue(object : Callback<Diseases> {

            override fun onResponse(call: Call<Diseases>, resp: Response<Diseases>) {

                if (resp.isSuccessful) {
                    var newbody = resp.body() // Use it or ignore it

                    Toast.makeText(applicationContext, "Successfully Added"+newbody, Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Failed at else part.", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<Diseases>, t: Throwable) {
                //finish()
//                    Log.d("errormsgfailure ::", t.message)
//                    Log.e("errorunderfailure:", t.message)
                Toast.makeText(applicationContext, "Failed to add item", Toast.LENGTH_SHORT).show()
            }
        })


    }
}

