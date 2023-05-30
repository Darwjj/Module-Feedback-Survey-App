package com.example.surveyapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.surveyapp.Model.DataBaseHelper
import com.example.surveyapp.Model.User

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun onClickButton(view: View) {
        val username = findViewById<EditText>(R.id.editTextNewUsername).text.toString()
        val password = findViewById<EditText>(R.id.editTextNewPassword).text.toString()
        val passwordagain = findViewById<EditText>(R.id.editTextRePassword).text.toString()
        var admin: Boolean
        val intentStudent = Intent(this, MainActivityUser::class.java).apply {
        }
        val intentAdmin = Intent(this, MainActivityAdmin::class.java).apply {
        }

        val adminCheck = findViewById<CheckBox>(R.id.checkBoxAdmin)
        admin = adminCheck.isChecked

        when {
            username.isNullOrBlank() -> Toast.makeText(this, "Enter a username.", Toast.LENGTH_SHORT).show()
            password.isNullOrBlank() -> Toast.makeText(this, "Enter a password.", Toast.LENGTH_SHORT).show()
            passwordagain.isNullOrBlank() -> Toast.makeText(this, "Enter your password again.", Toast.LENGTH_SHORT).show()
            password != passwordagain -> Toast.makeText(this, "Password not match.", Toast.LENGTH_SHORT).show()

            else -> {
                //Push username and password to the database
                val newUser = User(-1, username, password, admin)
                val myDatabase = DataBaseHelper(this)
                val result = myDatabase.addU(newUser)

                when(result) {
                    1 -> {
                        if (admin) {
                            Toast.makeText(this, "$username has successfully made an account.", Toast.LENGTH_SHORT).show()
                            startActivity(intentAdmin)
                        }

                        else if(!admin) {
                            Toast.makeText(this, "$username has successfully made an account.", Toast.LENGTH_SHORT).show()
                            startActivity(intentStudent)
                        }
                    }
                    -3 -> Toast.makeText(this, "The username '$username' already exists.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}