package com.example.myscope.activities.medical_history

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

import androidx.appcompat.app.AppCompatActivity

import com.example.myscope.R

class Diet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.diet)
        var spinner_diet = findViewById(R.id.spinner_diet) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.diet_arrays, R.layout.spinner_dropdown_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinner_diet.setAdapter(adapter)

    }
}
