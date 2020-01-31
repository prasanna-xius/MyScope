package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.diet.*
import kotlinx.android.synthetic.main.spinner_dropdown_item.view.*

class Diet : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diet)
        showToolbar1()
        header!!.text = "Diet"

//        var spinner_diet = findViewById(R.id.spinner_diet) as Spinner
//        val adapter = ArrayAdapter.createFromResource(this, R.array.diet_arrays, R.layout.spinner_item)
//        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
//        spinner_diet.setAdapter(adapter)

        spinnerSet(spinner_diet,resources.getStringArray(R.array.diet_arrays))

        btn_diet.setOnClickListener {

            if (spinner_diet.selectedItem.equals("Select status")){

                Toast.makeText(applicationContext,"cmg",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext,"wfgeug",Toast.LENGTH_LONG).show()
            }
        }

        }

    }
