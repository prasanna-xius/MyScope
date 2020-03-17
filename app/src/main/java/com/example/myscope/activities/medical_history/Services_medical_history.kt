package com.example.myscope.activities.medical_history

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import com.example.myscope.activities.services.Disease_service
import com.example.myscope.activities.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_services_medical_history.*
import retrofit2.Call
import retrofit2.Response



class Services_medical_history : BaseActivity() {



    lateinit var descText: TextView
    lateinit var descText1: TextView
    lateinit var descText2: TextView
    lateinit var descText3: TextView
    lateinit  var descText4: TextView
    lateinit var plus: ImageButton
    lateinit var minus: ImageButton
    lateinit var plus1: ImageButton
    lateinit  var minus1: ImageButton
    lateinit var plus2: ImageButton
    lateinit  var minus2: ImageButton
    lateinit  var plus3: ImageButton
    lateinit  var minus3: ImageButton
    lateinit var plus4: ImageButton
    lateinit  var minus4: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services_medical_history)


        descText = findViewById(R.id.description_text)
        plus = findViewById(R.id.plus)
        minus = findViewById(R.id.minus)

        descText1 = findViewById(R.id.description_text1)
        plus1 = findViewById(R.id.plus1)
        minus1 = findViewById(R.id.minus1)

        descText2 = findViewById(R.id.description_text2)
        plus2 = findViewById(R.id.plus2)
        minus2 = findViewById(R.id.minus2)

        descText3 = findViewById(R.id.description_text3)
        plus3 = findViewById(R.id.plus3)
        minus3 = findViewById(R.id.minus3)

        descText4 = findViewById(R.id.description_text4)
        plus4 = findViewById(R.id.plus4)
        minus4 = findViewById(R.id.minus4)

        plus.setOnClickListener  {
            plus.visibility = View.GONE
            minus.visibility = View.VISIBLE
            descText.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus1.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
            plus1.visibility = View.VISIBLE
            plus2.visibility = View.VISIBLE
            plus3.visibility = View.VISIBLE
            plus4.visibility = View.VISIBLE
        }

        minus.setOnClickListener {
            minus.visibility = View.GONE
            plus.visibility = View.VISIBLE
            descText.maxLines = 0
        }

        plus1.setOnClickListener {
            plus1.visibility = View.GONE
            minus1.visibility = View.VISIBLE
            descText1.maxLines = Integer.MAX_VALUE
            descText.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
            plus.visibility = View.VISIBLE
            plus2.visibility = View.VISIBLE
            plus3.visibility = View.VISIBLE
            plus4.visibility = View.VISIBLE
        }

        minus1.setOnClickListener {
            minus1.visibility = View.GONE
            plus1.visibility = View.VISIBLE
            descText1.maxLines = 0
        }

        plus2.setOnClickListener {
            plus2.visibility = View.GONE
            minus2.visibility = View.VISIBLE
            descText2.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText.maxLines = 0
            descText3.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus1.visibility = View.GONE
            minus3.visibility = View.GONE
            minus4.visibility = View.GONE
            plus1.visibility = View.VISIBLE
            plus.visibility = View.VISIBLE
            plus3.visibility = View.VISIBLE
            plus4.visibility = View.VISIBLE
        }

        minus2.setOnClickListener {
            minus2.visibility = View.GONE
            plus2.visibility = View.VISIBLE
            descText2.maxLines = 0
        }

        plus3.setOnClickListener {
            plus3.visibility = View.GONE
            minus3.visibility = View.VISIBLE
            descText3.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText.maxLines = 0
            descText4.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus1.visibility = View.GONE
            minus4.visibility = View.GONE
            plus1.visibility = View.VISIBLE
            plus2.visibility = View.VISIBLE
            plus.visibility = View.VISIBLE
            plus4.visibility = View.VISIBLE
        }

        minus3.setOnClickListener {
            minus3.visibility = View.GONE
            plus3.visibility = View.VISIBLE
            descText3.maxLines = 0
        }

        plus4.setOnClickListener {
            plus4.visibility = View.GONE
            minus4.visibility = View.VISIBLE
            descText4.maxLines = Integer.MAX_VALUE
            descText1.maxLines = 0
            descText2.maxLines = 0
            descText3.maxLines = 0
            descText.maxLines = 0
            minus.visibility = View.GONE
            minus2.visibility = View.GONE
            minus3.visibility = View.GONE
            minus1.visibility = View.GONE
            plus1.visibility = View.VISIBLE
            plus2.visibility = View.VISIBLE
            plus3.visibility = View.VISIBLE
            plus.visibility = View.VISIBLE
        }

        minus4.setOnClickListener {
            minus4.visibility = View.GONE
            plus4.visibility = View.VISIBLE
            descText4.maxLines = 0
        }




        checkbox_services.setOnClickListener{



            CheckBoxvalues()
        }

    }

    private fun CheckBoxvalues() {
        val checkboxvalue = ServiceBuilder.buildService(Disease_service::class.java)

        val requestCall = checkboxvalue.getdisease("8142529582" )



        requestCall.enqueue(object : retrofit2.Callback<List<Diseases>>{


            override fun onResponse(call: Call<List<Diseases>>, response: Response<List<Diseases>>) {

                val length = response.body()?.size

                showLongToast(length.toString())



                if (length!! > 0){

                    showLongToast("sussefull")

                }
                else{
                    showLongToast("add Disease History")

                    val  intent = Intent(this@Services_medical_history,Disease_recyclerView::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<List<Diseases>>, t: Throwable) {

                showLongToast("add Disease History")
            }
        })
    }
}
