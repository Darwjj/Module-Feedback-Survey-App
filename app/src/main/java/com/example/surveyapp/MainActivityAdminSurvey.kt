package com.example.surveyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivityAdminSurvey : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_survey)

        var sp = findViewById<Spinner>(R.id.spinner)
        val codes = arrayOf("CTEC3911", "CTEC3654", "IMAT3104", "CTEC3987", "CTEC3451")
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, codes)
        sp.adapter = arrayAdapter
    }

    fun nextBtn(view: View) {
        val mySp = findViewById<Spinner>(R.id.spinner)
        val svyName = mySp.selectedItem.toString()
        val startDate = findViewById<EditText>(R.id.editTextStartDate).text.toString()
        val endDate = findViewById<EditText>(R.id.editTextEndDate).text.toString()
        val intent = Intent(this, MainActivityAdminQuestions::class.java)
        intent.putExtra("Survey Name", svyName)
        intent.putExtra("Start", startDate)
        intent.putExtra("End", endDate)
        when {

            else -> {
                startActivity(intent)
            }
        }
    }

    fun backBtn(view: View) {
        val intentBack = Intent(this, MainActivityAdmin::class.java)
        startActivity(intentBack)
    }
}