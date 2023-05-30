package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat

class MainActivityAdminEditSurvey : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_edit_survey)

        val Id = intent.getIntExtra("id", 0).toInt()
        val survey = intent.getStringExtra("Selected Item").toString()
        val StartDate = intent.getStringExtra("StartDate").toString()
        val EndDate = intent.getStringExtra("EndDate").toString()


        var idSet =   findViewById<TextView>(R.id.textViewId)
        var sDateSet = findViewById<EditText>(R.id.editTextEditStartDate)
        var eDateSet = findViewById<EditText>(R.id.editTextEditEndDate)
        var title = findViewById<TextView>(R.id.textViewEditTitle)


        idSet.text = Id.toString()
        title.text = survey
        sDateSet.setText(StartDate)
        eDateSet.setText(EndDate)



    }

    fun nextBtn(view: View) {
        val startDate = findViewById<EditText>(R.id.editTextEditStartDate).text.toString()
        val endDate = findViewById<EditText>(R.id.editTextEditEndDate).text.toString()
        val intent = Intent(this, MainActivityAdminEditQuestions::class.java)
        intent.putExtra("Start", startDate)
        intent.putExtra("End", endDate)
        // Set the date format to use for parsing
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        try {
            // Parse the start and end dates
            val start = dateFormat.parse(startDate)
            val end = dateFormat.parse(endDate)

            // Check if the end date is after the start date
            if (end.after(start)) {
                startActivity(intent)
            } else {
                // Show an error message if the end date is not after the start date
                Toast.makeText(this, "End date must be after start date", Toast.LENGTH_SHORT).show()
            }
        } catch (e: ParseException) {
            // Show an error message if the date format is invalid
            Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show()
        }

    }

    fun backBtn(view: View) {
        val intentBack = Intent(this, MainActivityAdmin::class.java)
        startActivity(intentBack)
    }
}