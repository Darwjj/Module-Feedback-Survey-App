package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivityAdminEditQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin_edit_questions)
    }

    fun btnBack(view: View) {
        val intent = Intent(this, MainActivityAdminEditSurvey::class.java)
        startActivity(intent)
    }

    fun btnUpdate(view: View) {

        val intent = Intent(this, MainActivityAdmin::class.java)
        startActivity(intent)
    }
}