package com.example.myscope.activities.medical_history


import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

import com.example.myscope.R
import com.example.myscope.activities.BaseActivity
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.family_history.*

class Family_History : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.family_history)

        showToolbar()
        header!!.text = "Family History"

        var spinner_family = findViewById(R.id.spinner_family) as Spinner
        val adapter = ArrayAdapter.createFromResource(this, R.array.Relationship_arrays, R.layout.spinner_item)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        spinner_family.setAdapter(adapter)

        errorRemove(et_family_condition)
    }
}
