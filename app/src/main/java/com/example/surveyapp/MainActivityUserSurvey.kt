package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import com.example.surveyapp.Model.ListHub
import com.example.surveyapp.Model.Questions
import com.example.surveyapp.Model.Survey

class MainActivityUserSurvey : AppCompatActivity() {

    lateinit var listHub: ListHub
    lateinit var questionList: ArrayList<Questions>
    lateinit var surveyList: ArrayList<Survey>
    private lateinit var questionTextView: TextView
    private lateinit var radioButton1: RadioButton
    private lateinit var radioButton2: RadioButton
    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton4: RadioButton
    private lateinit var radioButton5: RadioButton
    private lateinit var nextButton: Button
    private lateinit var backButton: Button
    private lateinit var submitButton: Button

    private val questions = arrayOf(
        "Q1",
        "Q2",
        "Q3",
        "Q4",
        "Q5",
        "Q6",
        "Q7",
        "Q8",
        "Q9",
        "Q10",
        // Add more questions here
    )

    private var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user_survey)


        val results = intent.getStringArrayExtra("results")

        questionTextView = findViewById(R.id.textViewQuestions)
        radioButton1 = findViewById(R.id.rStronglyAgree)
        radioButton2 = findViewById(R.id.rAgree)
        radioButton3 = findViewById(R.id.rNeitherAgreeorDisagree)
        radioButton4 = findViewById(R.id.rDisagree)
        radioButton5 = findViewById(R.id.rStronglyDisagree)
        nextButton = findViewById(R.id.btnUpdateSurvey2)
        backButton = findViewById(R.id.btnCancel)
        submitButton = findViewById(R.id.btnSubmit)

        questionTextView.text = questions[currentQuestion]

        nextButton.setOnClickListener {
            currentQuestion++
            if (currentQuestion >= questions.size) {
                currentQuestion = questions.size - 1
            }
            questionTextView.text = questions[currentQuestion]
        }

        backButton.setOnClickListener {
            currentQuestion--
            if (currentQuestion < 0) {
                currentQuestion = 0
            }
            questionTextView.text = questions[currentQuestion]
        }

        submitButton.setOnClickListener {
            // Submit the answers to the survey
            val intent = Intent(this, MainActivityUserResults::class.java)
            intent.putExtra("results", results)
            startActivity(intent)
        }

    }

}