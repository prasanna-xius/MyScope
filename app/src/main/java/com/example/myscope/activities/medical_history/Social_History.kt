package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R

class Social_History : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.social_history)
        var spinnersmoking = findViewById<Spinner>(R.id.spinner_smoking)
        val adapter = ArrayAdapter.createFromResource(this, R.array.smoking_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnersmoking.setAdapter(adapter)



        var spinnerdrinking = findViewById<Spinner>(R.id.spinner_drinking)
        val adapter1 = ArrayAdapter.createFromResource(this, R.array.drinking_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerdrinking.setAdapter(adapter1)
    }

}
