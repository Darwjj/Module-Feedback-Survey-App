package com.example.surveyapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivityUserResults : AppCompatActivity() {

    private lateinit var resultsTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user_results)

        resultsTextView = findViewById(R.id.textView2)

        val results = intent.getStringArrayExtra("results")

        resultsTextView.text = "Results: $results"
    }

    fun backhbtn(view: View) {
        val intentBack = Intent(this, MainActivityUser::class.java)
        startActivity(intentBack)
    }
}