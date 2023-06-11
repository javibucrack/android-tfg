package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

/**
 * Clase que enseña la información de cualquier usuario.
 */
class ProfileScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val user = intent.getSerializableExtra("user") as UserDto


        val profileName = findViewById<TextView>(R.id.showNameProfile)
        profileName.text = user.name

        val profileSurname = findViewById<TextView>(R.id.showSurnameProfile)
        val surname = user.firstSurname + " " + user.secondSurname
        profileSurname.text = surname

        val profileEmail = findViewById<TextView>(R.id.showEmailProfile)
        profileEmail.text = user.email

        val profileRol = findViewById<TextView>(R.id.showRolProfile)
        profileRol.text = user.role!!.name

        val changePasswordActivityButton = findViewById<Button>(R.id.changePasswordActivityButton)

        changePasswordActivityButton.setOnClickListener {
            val changePasswordActivityIntent = Intent(this, ChangePasswordActivity::class.java)
            changePasswordActivityIntent.putExtra("user", user)
            startActivity(changePasswordActivityIntent)
        }
    }
}