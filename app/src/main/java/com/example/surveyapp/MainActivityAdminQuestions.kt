package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.Model.DataBaseHelper
import com.example.surveyapp.Model.Questions
import com.example.surveyapp.Model.Survey

class MainActivityAdminQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_questions)
    }

    fun backButtn(view: View) {
        val intentBack = Intent(this, MainActivityAdminSurvey::class.java)
        startActivity(intentBack)
    }

    fun QuestionBtn(view: View) {
        val surveyTitle = intent.getStringExtra("Survey Name").toString()
        val startDate = intent.getStringExtra("Start").toString()
        val endDate = intent.getStringExtra("End").toString()
        val question1 = findViewById<EditText>(R.id.editTextQ1).text.toString()
        val question2 = findViewById<EditText>(R.id.editTextQ2).text.toString()
        val question3 = findViewById<EditText>(R.id.editTextQ3).text.toString()
        val question4 = findViewById<EditText>(R.id.editTextQ4).text.toString()
        val question5 = findViewById<EditText>(R.id.editTextQ5).text.toString()
        val question6 = findViewById<EditText>(R.id.editTextQ6).text.toString()
        val question7 = findViewById<EditText>(R.id.editTextQ7).text.toString()
        val question8 = findViewById<EditText>(R.id.editTextQ8).text.toString()
        val question9 = findViewById<EditText>(R.id.editTextQ9).text.toString()
        val question10 = findViewById<EditText>(R.id.editTextQ10).text.toString()

        when {

            question1.isNullOrBlank() -> Toast.makeText(this, "Please enter q1.", Toast.LENGTH_SHORT).show()
            question2.isNullOrBlank() -> Toast.makeText(this, "Please enter q2.", Toast.LENGTH_SHORT).show()
            question3.isNullOrBlank() -> Toast.makeText(this, "Please enter q3.", Toast.LENGTH_SHORT).show()
            question4.isNullOrBlank() -> Toast.makeText(this, "Please enter q4.", Toast.LENGTH_SHORT).show()
            question5.isNullOrBlank() -> Toast.makeText(this, "Please enter q5.", Toast.LENGTH_SHORT).show()
            question6.isNullOrBlank() -> Toast.makeText(this, "Please enter q6.", Toast.LENGTH_SHORT).show()
            question7.isNullOrBlank() -> Toast.makeText(this, "Please enter q7.", Toast.LENGTH_SHORT).show()
            question8.isNullOrBlank() -> Toast.makeText(this, "Please enter q8.", Toast.LENGTH_SHORT).show()
            question9.isNullOrBlank() -> Toast.makeText(this, "Please enter q9.", Toast.LENGTH_SHORT).show()
            question10.isNullOrBlank() -> Toast.makeText(this, "Please enter q10.", Toast.LENGTH_SHORT).show()

            else -> {
                val newSurvey = Survey(1, surveyTitle, startDate, endDate)
                val newQuestion1 = Questions(1, question1, newSurvey.SID)
                val newQuestion2 = Questions(1, question2, newSurvey.SID)
                val newQuestion3 = Questions(1, question3, newSurvey.SID)
                val newQuestion4 = Questions(1, question4, newSurvey.SID)
                val newQuestion5 = Questions(1, question5, newSurvey.SID)
                val newQuestion6 = Questions(1, question6, newSurvey.SID)
                val newQuestion7 = Questions(1, question7, newSurvey.SID)
                val newQuestion8 = Questions(1, question8, newSurvey.SID)
                val newQuestion9 = Questions(1, question9, newSurvey.SID)
                val newQuestion10 = Questions(1, question10, newSurvey.SID)
                val myDatabase = DataBaseHelper(this)
                val addNewSurvey = myDatabase.addSvy(newSurvey)
                myDatabase.addQ(newQuestion1)
                myDatabase.addQ(newQuestion2)
                myDatabase.addQ(newQuestion3)
                myDatabase.addQ(newQuestion4)
                myDatabase.addQ(newQuestion5)
                myDatabase.addQ(newQuestion6)
                myDatabase.addQ(newQuestion7)
                myDatabase.addQ(newQuestion8)
                myDatabase.addQ(newQuestion9)
                myDatabase.addQ(newQuestion10)

                when(addNewSurvey) {
                    1 -> Toast.makeText(this, "New survey for $surveyTitle has been created.", Toast.LENGTH_SHORT).show()

                    -1 -> Toast.makeText(this, "Error adding survey.", Toast.LENGTH_SHORT).show()
                }


                val intent = Intent(this, MainActivityAdmin::class.java)
                startActivity(intent)
            }
        }
    }
}