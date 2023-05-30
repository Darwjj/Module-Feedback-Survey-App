package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun registerbtn (view: View) {
        val intent2 = Intent(this, RegisterActivity::class.java)
        startActivity(intent2)
    }

    fun lgBtn (view: View) {
        val intent = Intent(this, MainActivityLoginAdminandUser::class.java)
        startActivity(intent)
    }

}