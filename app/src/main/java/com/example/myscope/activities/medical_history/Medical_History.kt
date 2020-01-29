package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R
import kotlinx.android.synthetic.main.medical_history.*

class Medical_History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.medical_history)
        var edit_text1=findViewById<EditText>(R.id.edit_text1)

        var spinner_disease = findViewById(R.id.spinner_disease) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.disease_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinner_disease.setAdapter(adapter)
        }

    }
