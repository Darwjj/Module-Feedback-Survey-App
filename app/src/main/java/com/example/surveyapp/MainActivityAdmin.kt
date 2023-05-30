package com.example.surveyapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import java.util.ArrayList
import androidx.appcompat.app.AppCompatActivity
import com.example.surveyapp.Model.*

class MainActivityAdmin() : AppCompatActivity() {

    private var questionList: ArrayList<Questions> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        val myDatabase = DataBaseHelper(this)
        var surveyTitles = myDatabase.getSvyTitle()
        var startDateList = myDatabase.getSD()
        var endDateList = myDatabase.getED()



        val edit = findViewById<Button>(R.id.buttonEdit)
        edit.isEnabled = false

        var surveyList = findViewById<ListView>(R.id.myListView)

        surveyList.setOnItemClickListener { parent, view, position, id ->
            edit.isEnabled = true
            val itemSelected = true

            val deleteButton = findViewById<Button>(R.id.buttonDelete)
            deleteButton.setOnClickListener {
                if (itemSelected) {
                    surveyTitles = myDatabase.getSvyTitle()
                    startDateList = myDatabase.getSD()
                    endDateList = myDatabase.getED()
                }
            }
        }
        val customAdapter = CustomAdapter(applicationContext, surveyTitles, startDateList, endDateList)
        surveyList!!.adapter = customAdapter
    }



    fun cBtn(view: View) {
        val createIntent = Intent(this, MainActivityAdminSurvey::class.java)
        startActivity(createIntent)
    }

    fun edtBtn(view: View) {

        var list = findViewById<ListView>(R.id.myListView)
        val selected = list.setOnItemClickListener { parent, view, position, id ->
            list.getItemAtPosition(0)
        }

        val intent = Intent(this, MainActivityAdminEditSurvey::class.java)
        intent.putExtra("Selected Item", selected.toString())
        intent.putExtra("questionList", questionList)
        startActivity(intent)
    }


    fun signoutBtn(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }





}