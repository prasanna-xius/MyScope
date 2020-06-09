package com.soargtechnologies.myscope.activities.View_and_Share

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soargtechnologies.myscope.R
import com.soargtechnologies.myscope.activities.services.ServiceBuilder

import com.soargtechnologies.myscope.models.ViewWindowDataClass
import com.soargtechnologies.myscope.services.PrescriptionInterface
import kotlinx.android.synthetic.main.activity_view_data.*

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

        val viewWindowService = ServiceBuilder.buildService(PrescriptionInterface::class.java)
        val requestCall = viewWindowService.getViewWindowDetails()

        //      Toast.makeText(applicationContext, "data id ::" + " " + id, Toast.LENGTH_LONG).show()

        //progressbar()

        requestCall.enqueue(object : retrofit2.Callback<List<ViewWindowDataClass>> {


            override fun onResponse(call: Call<List<ViewWindowDataClass>>, response: Response<List<ViewWindowDataClass>>) {


                if (response.isSuccessful) {

                    val viewWindowRes = response.body()

                    if (viewWindowRes!!.equals(null)) {

                    } else {

                        val destination = viewWindowRes.indices

                        for(item in destination)
                        {
                           viewWindowRes.get(position).surgeryname
                            var sno = item+1
                            view_medicationhistory.append(""+sno +"--"+ viewWindowRes.get(position).formulation+"--"+
                                                          viewWindowRes.get(position).drugname+"--"+
                                                        viewWindowRes.get(position).strength+"--"+
                                                        viewWindowRes.get(position).doseunit+"--"+
                                                        viewWindowRes.get(position).how_often_taken+"\n");

                            view_diet.setText(viewWindowRes.get(position).diet)

                            view_medicalhistory.append(""+sno +"--"+ viewWindowRes.get(position).known_condition+"--"+
                                    viewWindowRes.get(position).when_started+"--"+
                                    viewWindowRes.get(position).when_ended+"--"+
                                    viewWindowRes.get(position).disease_duration+
                                  "\n")

                            view_family.setText( viewWindowRes.get(position).family_condition+"--"+
                                    viewWindowRes.get(position).relationship+"--"+ viewWindowRes.get(position).family_note)

                            view_socialhistory.setText(viewWindowRes.get(position).alcohol+"--"+
                                    viewWindowRes.get(position).alcohol_duration+"--"+
                                    viewWindowRes.get(position).smoking+"--"+
                                    viewWindowRes.get(position).smoking_duration+"--"+
                                    viewWindowRes.get(position).tobacoo)
                            view_surgery.append( viewWindowRes.get(position).surgeryname+"--"+
                                    viewWindowRes.get(position).surgerydate+"--"+
                                    viewWindowRes.get(position).surgerynotes+
                                    "\n")

                            view_allergy.append( viewWindowRes.get(position).name+"--"+
                                    viewWindowRes.get(position).reaction+"--"+
                                    viewWindowRes.get(position).date+ "--"+ viewWindowRes.get(position).treatment+
                                    "\n")


                            view_adversedrug.append( viewWindowRes.get(position).drugname+"--"+
                                    viewWindowRes.get(position).adverse_reaction+"--"+
                                    viewWindowRes.get(position).date_of_start+ "--"+ viewWindowRes.get(position).treatment_taken+
                                    "\n")

                            view_immun.append( viewWindowRes.get(position).immuname+ "--"+ viewWindowRes.get(position).immudate+
                                    "\n")
                        }
                       // allergyid = destination?.allergy_id!!

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
