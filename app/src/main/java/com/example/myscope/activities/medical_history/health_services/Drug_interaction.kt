package com.example.myscope.activities.medical_history.health_services

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myscope.R
import kotlinx.android.synthetic.main.activity_drug_interaction.*
import kotlinx.android.synthetic.main.activity_services_medical_history.*

class Drug_interaction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drug_interaction)

        btn_drug_interaction.setOnClickListener {

            checkbox_Drug_Interaction.setChecked(true)
            finish()
        }
    }
}
