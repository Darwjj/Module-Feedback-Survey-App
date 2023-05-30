package com.example.surveyapp.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

/* Database Config*/
private val DataBaseName = "applicationDatabase.db"
private val ver : Int = 1

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, DataBaseName, null, ver) {

    // AdminandUser Table
    private val userTableName = "Users"
    private val userColumn_ID = "uID"
    private val userColumn_LoginName = "LoginName"
    private val userColumn_Password = "PassWord"
    private val adminColumn_ID = "AdminID"

    // Answer Table
    private val answerTableName = "Answers"
    private val answerColumn_ID = "AID"
    private val answerColumn_Text = "Text"
    private val Column_UserID = "UserID"
    private val Column_QuestionID ="QuestionID"

    // Survey Table
    private val surveyTableName = "Surveys"
    private val surveyColumn_ID = "SID"
    private val surveyColumn_Title = "title"
    private val surveyColumn_StartDate = "startDate"
    private val surveyColumn_EndDate = "endDate"

    // Questions Table
    private val questionTableName = "Questions"
    private val questionColumn_ID = "QID"
    private val questionColumn_Text = "questionText"
    private val questionColumn_ForeignSurveyID = "SurveyID"



    /*************************/
// This is called the first time a database is accessed
    // Create the database
    override fun onCreate(db: SQLiteDatabase?) {

        try {
            val sqlCreateStatementUser: String =
                "CREATE TABLE IF NOT EXISTS" + userTableName + " ( " + userColumn_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + userColumn_LoginName + " TEXT NOT NULL UNIQUE, " +
                        userColumn_Password + " TEXT NOT NULL, " + adminColumn_ID + " INTEGER NOT NULL DEFAULT 0 )"

            val sqlCreateStatementSurvey: String =
                "CREATE TABLE IF NOT EXISTS" + surveyTableName + " ( " + surveyColumn_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + surveyColumn_Title + " TEXT NOT NULL, " +
                        surveyColumn_StartDate + " TEXT NOT NULL, " + surveyColumn_EndDate + " TEXT )"

            val sqlCreateStatementQuestions: String =
                "CREATE TABLE IF NOT EXISTS" + questionTableName + " ( " + questionColumn_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + questionColumn_Text + " TEXT, " + questionColumn_ForeignSurveyID +
                    " INTEGER, " + "FOREIGN KEY ("+questionColumn_ForeignSurveyID+") REFERENCES "+surveyTableName+"("+surveyColumn_ID+") "

            val sqlCreateStatementAnswers: String =
                "CREATE TABLE IF NOT EXISTS" + answerTableName + " ( " + answerColumn_ID +
                        " INTEGER PRIMARY KEY AUTOINCREMENT, " + answerColumn_Text + " TEXT NOT NULL, " + Column_UserID +
                        " INTEGER NOT NULL DEFAULT 0," + Column_QuestionID + " INTEGER NOT NULL DEFAULT 0)"

            db?.execSQL(sqlCreateStatementUser)
            db?.execSQL(sqlCreateStatementSurvey)
            db?.execSQL(sqlCreateStatementQuestions)
            db?.execSQL(sqlCreateStatementAnswers)
        }
        catch (e: SQLiteException) {}
    }

    // This is called if the database ver. is changed
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    /**
     * return  1 : the new use has been add to the database successfully
     * return -1 : Error, adding new user
     * return -2 : can not open database
     * return -3 : user name is already exist
     *
     */

    private fun checkUsername(user: User): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch(e: SQLiteException) {
            return -2
        }

        val username = user.LoginName.lowercase()

        val sqlStatement = "SELECT * FROM $userTableName WHERE $userColumn_LoginName = ?"
        val param = arrayOf(username)
        val cursor: Cursor =  db.rawQuery(sqlStatement,param)

        if(cursor.moveToFirst()){
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return -3 // Error: the username already exists
        }

        cursor.close()
        db.close()
        return 0 //User not found

    }

    fun addU (user: User) : Int {

        val doesUsernameExist = checkUsername(user)
        if(doesUsernameExist < 0)
            return doesUsernameExist

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(userColumn_LoginName, user.LoginName.lowercase())
        cv.put(userColumn_Password, user.PassWord)
        cv.put(adminColumn_ID, user.AdminID)

        val success = db.insert(userTableName, null, cv)

        db.close()

        if (success.toInt() == -1)
            return success.toInt() //Error, adding new user
        else
            return 1 //Add the user
    }

    fun getU(user: User): Int {

        val db: SQLiteDatabase
        try {
            db = this.readableDatabase
        }
        catch (e: SQLiteException) {
            return -2
        }

        val userName = user.LoginName.lowercase()
        val userPassword = user.PassWord

        val sqlStatement = "SELECT * FROM $userTableName WHERE $userColumn_LoginName = ? AND $userColumn_Password = ?"
        val param = arrayOf(userName, userPassword)
        val cursor: Cursor = db.rawQuery(sqlStatement, param)

        if(cursor.moveToFirst()) {
            // The user is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -1 //The user is not found

    }

    fun getA(user: User): Int {
        val db: SQLiteDatabase
        val username = user.LoginName.lowercase()
        val isAdmin = user.AdminID

        try {
            db = this.readableDatabase
        }
        catch (e: SQLiteException) {
            return -2
        }

        val sqlStatement = "SELECT $adminColumn_ID FROM $userTableName WHERE $userColumn_LoginName = '${user.LoginName}'"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst()) {
            // An admin is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -4
    }

    fun getStd(user: User): Int {
        val db: SQLiteDatabase
        val isAdmin = user.AdminID

        try {
            db = this.readableDatabase
        }
        catch (e: SQLiteException) {
            return -2
        }

        val sqlStatement = "SELECT * FROM $userTableName WHERE $adminColumn_ID = 0"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst()) {
            // A student is found
            val n = cursor.getInt(0)
            cursor.close()
            db.close()
            return n
        }

        cursor.close()
        db.close()
        return -4
    }

    fun addSvy(survey: Survey): Int {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(surveyColumn_Title, survey.title)
        cv.put(surveyColumn_StartDate, survey.startDate)
        cv.put(surveyColumn_EndDate, survey.endDate)

        val success = db.insert(surveyTableName, null, cv)

        db.close()

        if (success.toInt() == -1)
            return success.toInt() //Error, adding new survey
        else
            return 1 //Add the survey
    }

    fun getSvyTitle(): ArrayList<String> {
        val db: SQLiteDatabase = this.readableDatabase
        val surveyList = ArrayList<String>()

        val sqlStatement = "SELECT $surveyColumn_Title FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst())
            do {
                val title: String = cursor.getString(0)

                surveyList.add(title)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    fun getSD(): ArrayList<String> {
        val db: SQLiteDatabase = this.readableDatabase
        val startList = ArrayList<String>()

        val sqlStatement = "SELECT $surveyColumn_StartDate FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst())
            do {
                val start: String = cursor.getString(0)

                startList.add(start)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return startList

    }

    fun getED(): ArrayList<String> {
        val db: SQLiteDatabase = this.readableDatabase
        val endList = ArrayList<String>()

        val sqlStatement = "SELECT $surveyColumn_EndDate FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst())
            do {
                val end: String = cursor.getString(0)

                endList.add(end)
            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return endList
    }

    fun addQ(questions: Questions): Int {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(questionColumn_Text, questions.questionText)
        cv.put(questionColumn_ForeignSurveyID, questions.SurveyID)

        val success = db.insert(questionTableName, null, cv)

        db.close()

        if (success.toInt() == -1)
            return success.toInt() //Error, adding new question
        else
            return 1 //Add the question
    }

    fun getAllQ(): ArrayList<Questions> {
        val questionList = ArrayList<Questions>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement,null)
        if (cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val text: String = cursor.getString(1)
                val id2: Int = cursor.getInt(2)
                val q = Questions(id, text,id2)
                questionList.add(q)
            } while (cursor.moveToFirst())
        cursor.close()
        return questionList
    }

    fun getAllS(): ArrayList<Survey> {
        val db: SQLiteDatabase = this.readableDatabase
        val surveyList = ArrayList<Survey>()

        val sqlStatement = "SELECT * FROM $surveyTableName"
        val cursor: Cursor = db.rawQuery(sqlStatement, null)

        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val title: String = cursor.getString(1)
                val startDate: String = cursor.getString(2)
                val endDate: String = cursor.getString(3)

                val survey = Survey(id, title, startDate, endDate)
                surveyList.add(survey)

            }while(cursor.moveToNext())

        cursor.close()
        db.close()

        return surveyList
    }

    fun getAllQuestionandSurvey(Id: Survey) : ArrayList<Questions> {
        val questionList = ArrayList<Questions>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTableName INNER JOIN $surveyTableName WHERE $questionColumn_ForeignSurveyID = $Id "
        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val questionText: String = cursor.getString(1)
                val SurveyID: Int = cursor.getInt(2)

                val qq = Questions(id,questionText,SurveyID)
                questionList.add(qq)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return questionList
    }


    fun updateQuestion(questions: Questions) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(questionColumn_ID, questions.QID)
        cv.put(questionColumn_Text, questions.questionText)

        val result = db.update(questionTableName,cv,"$questionColumn_ForeignSurveyID = ${questions.SurveyID}", null) == 1
        db.close()
        return result
    }

    fun updateSurvey(survey: Survey) : Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(surveyColumn_Title, survey.title)
        cv.put(surveyColumn_StartDate, survey.startDate)
        cv.put(surveyColumn_EndDate, survey.endDate)

        val result = db.update(surveyTableName,cv,"$surveyColumn_ID = ${survey.SID}", null) == 1
        db.close()
        return result
    }


    fun deleteSurvey (survey: Survey): Boolean {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(surveyTableName,"$surveyColumn_ID = ${survey.SID}",null) == 1
        db.close()
        return result
    }


/*
    fun getAllQuestion() : ArrayList<Questions> {
        val QuestionsList = ArrayList<Questions>()
        val db: SQLiteDatabase = this.readableDatabase
        val sqlStatement = "SELECT * FROM $questionTable"

        val cursor: Cursor =  db.rawQuery(sqlStatement,null)
        if(cursor.moveToFirst())
            do {
                val id: Int = cursor.getInt(0)
                val text: String = cursor.getString(1)
                val id2: Int = cursor.getInt(2)
                val ques = Questions(id,text,id2)
                QuestionsList.add(ques)
            }while(cursor.moveToNext())
        cursor.close()
        db.close()
        return QuestionsList
    }

 */




    /*fun updateSurvey(survey: Survey) : Boolean {

        val db: SQLiteDatabase = this.writableDatabase
        val cv: ContentValues = ContentValues()

        cv.put(Column_StartDate, survey.startDate)
        cv.put(Column_EndDate, survey.endDate)
    }*/
}

