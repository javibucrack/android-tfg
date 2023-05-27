package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class ChangePasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        val user = intent.getSerializableExtra("user") as UserDto

        val actualPassword = findViewById<EditText>(R.id.actualPasswordEditText)

        val newPasswordFirstTry = findViewById<EditText>(R.id.newPasswordFirstTryEditText)

        val newPasswordSecondTry = findViewById<EditText>(R.id.newPasswordSecondTryEditText)

        val changePassword = findViewById<Button>(R.id.changePasswordButton)

        changePassword.setOnClickListener {
            if (!user.pass!!.equals(actualPassword)) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "La contraseña actual no es correcta",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (!newPasswordFirstTry.equals(newPasswordSecondTry)) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Las contraseñas no coinciden",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    //TODO: añadir endpoint que cambia la contraseña
                }
            }
        }
    }
}