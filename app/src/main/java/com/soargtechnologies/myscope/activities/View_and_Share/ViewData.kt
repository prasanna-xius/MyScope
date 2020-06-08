package com.soargtechnologies.myscope.activities.View_and_Share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.services.ServiceBuilder

import com.soargtechnologies.myscope.models.ViewWindowDataClass
import com.soargtechnologies.myscope.services.ViewWindowInterface

import kotlinx.android.synthetic.main.spinner_dropdown_item_allergy.view.*
import retrofit2.Call
import retrofit2.Response

class ViewData : AppCompatActivity() {
    var position: Int = 1;
    var mobile_no: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_data)

        position = intent.getIntExtra("position", 0)

    }

    override fun onResume() {
        super.onResume()

        // loadDestinations()


        loadDetails(mobile_no.toString(), position!!)

        //initUpdateButton(mobile_no.toString())

        //initDeleteButton(id)
//        }
    }

    private fun loadDetails(id: String, position: Int) {

        val viewWindowService = ServiceBuilder.buildService(ViewWindowInterface::class.java)
        val requestCall = viewWindowService.getViewWindowDetailsByid(mobile_no.toString())

        //      Toast.makeText(applicationContext, "data id ::" + " " + id, Toast.LENGTH_LONG).show()

        //progressbar()

        requestCall.enqueue(object : retrofit2.Callback<List<ViewWindowDataClass>> {


            override fun onResponse(call: Call<List<ViewWindowDataClass>>, response: Response<List<ViewWindowDataClass>>) {


                if (response.isSuccessful) {

                    val viewWindowRes = response.body()

                    if (viewWindowRes!!.equals(null)) {

                    } else {

                        val destination = viewWindowRes?.get(position)

                       // allergyid = destination?.allergy_id!!
                        destination.let {
                           /* et_name_update?.setText(destination!!.name)
                            et_reaction_update?.setText(destination!!.reaction)
                            et_treatment_update?.setText(destination!!.treatment)
                            et_notes_allergies_update?.setText(destination!!.notes)
                            textviewdate_update?.setText(destination!!.date)

                           spinnerAllergy_update.tv_allergy?.setText(destination!!.spnrdata)*/




                            //collapsing_toolbar.title = destination.city
                        }!!


                        //                      Toast.makeText(applicationContext, "sucess::" + " " + destination.toString(), Toast.LENGTH_LONG).show()
                    }

                } else {
                    //             Toast.makeText(this@AllergyUpdate_Activity, "Failed to retrieve details under else", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ViewWindowDataClass>>, t: Throwable) {
                //          Toast.makeText(this@AllergyUpdate_Activity,"Failed to retrieve details " + t.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }


}
