package com.example.myscope.fragments

import java.util.*

object ExpandableListDataPump {
    val data: HashMap<String, List<String>>
        get() {
            val expandableListDetail = LinkedHashMap<String, List<String>>()
            val dashboard: List<String> = ArrayList()
            val educationalblog: List<String> = ArrayList()
            val appointments: List<String> = ArrayList()
            val services: List<String> = ArrayList()
            val contactus: List<String> = ArrayList()
            val medicalhistory: MutableList<String> = ArrayList()
            medicalhistory.add("Medical History")
            medicalhistory.add("Family History")
            medicalhistory.add("Social History")
            medicalhistory.add("Diet")
            medicalhistory.add("Allergies")
            medicalhistory.add("Immuzination History")
            medicalhistory.add("Medication History")
            medicalhistory.add("Surgery History")
            medicalhistory.add("Adverse Drug Reaction")
            val prescriptions: MutableList<String> = ArrayList()
            prescriptions.add("Prescriptions")
            prescriptions.add("Antibiotic")
            prescriptions.add("Ayurvedic")
            val medicaldocuments: MutableList<String> = ArrayList()
            medicaldocuments.add("Discharge Summery")
            medicaldocuments.add("Dental Recards")
            medicaldocuments.add("Immuzination")
            medicaldocuments.add("Health Insurance")
            medicaldocuments.add("Allergies")
            medicaldocuments.add("Diet Chart")
            medicaldocuments.add("Education Material")
            medicaldocuments.add("Other Documets")
            val labreports: MutableList<String> = ArrayList()
            labreports.add("Blood Reports")
            labreports.add("Urine Report")
            labreports.add("Ultra Sound")
            labreports.add("X-Ray")
            labreports.add("CT-Scan")
            labreports.add("MRI")
            labreports.add("ECG")
            labreports.add("ECHO")
            labreports.add("Sress Test")
            labreports.add("SonoGraphy")
            labreports.add("Colono Scopy")
            labreports.add("Others")
            val selfmonitoring: MutableList<String> = ArrayList()
            selfmonitoring.add("Blood Glucose  Monitoring")
            selfmonitoring.add("Blood pressure")
            selfmonitoring.add("Cholostrol")
            selfmonitoring.add("Weight")
            selfmonitoring.add("Exercise Tracker")
            selfmonitoring.add("SpO2")
            expandableListDetail["DashBoard"] = dashboard
            expandableListDetail["Medical History"] = medicalhistory
            expandableListDetail["Prescriptions"] = prescriptions
            expandableListDetail["Medical Documents"] = medicaldocuments
            expandableListDetail["Lab Reports"] = labreports
            expandableListDetail["Self Monitoring"] = selfmonitoring
            expandableListDetail["Educational Blog"] = educationalblog
            expandableListDetail["Appointments"] = appointments
            expandableListDetail["Services"] = services
            expandableListDetail["ContactUs"] = contactus
            return expandableListDetail
        }
}