package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import com.example.surveyapp.Model.CustomAdapter
import com.example.surveyapp.Model.DataBaseHelper

class MainActivityUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)

        val myDatabase = DataBaseHelper(this)

        var surveyTitles = myDatabase.getSvyTitle()
        var startDateList = myDatabase.getSD()
        var endDateList = myDatabase.getED()

        val takeSurvey = findViewById<Button>(R.id.buttonTk)
        takeSurvey.isEnabled = false

        var surveyList = findViewById<ListView>(R.id.myListView)

        surveyList.setOnItemClickListener { parent, view, position, id ->
            takeSurvey.isEnabled = true
        }
        val customAdapter = CustomAdapter(applicationContext, surveyTitles, startDateList, endDateList)
        surveyList!!.adapter = customAdapter
    }
    fun TakeBtn(view: View) {

        var list = findViewById<ListView>(R.id.myListView)

        val selected = list.setOnItemClickListener { parent, view, position, id ->
            list.getItemAtPosition(0)
        }

        val intent = Intent(this, MainActivityUserSurvey::class.java)
        intent.putExtra("Selected Item", selected.toString())
        startActivity(intent)
    }

    fun logoutBtn(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
