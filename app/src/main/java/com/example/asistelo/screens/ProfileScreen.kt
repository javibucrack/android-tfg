package com.example.asistelo.screens

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class ProfileScreen : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        val user = intent.getSerializableExtra("user") as UserDto


        val profileName = findViewById<TextView>(R.id.showNameProfile)
        profileName.text = user.name

        val profileSurname = findViewById<TextView>(R.id.showSurnameProfile)
        val surname = user.first_surname + " " + user.second_surname
        profileSurname.text = surname

        val profileEmail = findViewById<TextView>(R.id.showEmailProfile)
        profileEmail.text = user.email

        val profileRol = findViewById<TextView>(R.id.showRolProfile)
        profileRol.text = user.role.name
    }
}