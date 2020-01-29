package com.example.myscope.activities.medical_history


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R

class Family_History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_history)

        var spinner_family = findViewById(R.id.spinner_family) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.Relationship_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinner_family.setAdapter(adapter)
    }
}
