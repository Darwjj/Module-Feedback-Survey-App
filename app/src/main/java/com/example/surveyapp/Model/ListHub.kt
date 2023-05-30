package com.example.surveyapp.Model

import android.content.Context

class ListHub(context: Context) {

    private val questionList: ArrayList<Questions>
    private val surveyList: ArrayList<Survey>
    private val dbHelper: DataBaseHelper = DataBaseHelper(context)

    init {
        questionList = dbHelper.getAllQ()
        surveyList = dbHelper.getAllS()
    }

    fun get_QuestionList(): ArrayList<Questions> {
        return questionList
    }

    fun get_SurveyList(): ArrayList<Survey> {
        return surveyList
    }

    fun get_SurveyName(SurveyID:Int): String {
        var surveyName = "Not found"
        for (e in surveyList)
            if (e.SID == SurveyID) {
                surveyName = e.title
                break
            }
        return surveyName
    }
}