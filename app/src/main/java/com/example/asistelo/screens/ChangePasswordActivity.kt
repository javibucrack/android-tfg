package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.UserDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Clase que permite cambiar la contraseña de una cuenta.
 */
class ChangePasswordActivity : AppCompatActivity() {
    @SuppressLint("CutPasteId")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        val user = intent.getSerializableExtra("user") as UserDto

        val userController = RetrofitClient.retrofit.create(UserController::class.java)

        val actualPassword = findViewById<EditText>(R.id.actualPasswordEditText)

        val newPasswordFirstTry = findViewById<EditText>(R.id.newPasswordFirstTryEditText)

        val newPasswordSecondTry = findViewById<EditText>(R.id.newPasswordSecondTryEditText)

        val changePassword = findViewById<Button>(R.id.changePasswordButton)

        changePassword.setOnClickListener {
            if (user.pass!! != actualPassword.text.toString()) {
                Toast.makeText(
                    this@ChangePasswordActivity,
                    "La contraseña actual no es correcta",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (newPasswordFirstTry.text.toString() != newPasswordSecondTry.text.toString()) {
                    Toast.makeText(
                        this@ChangePasswordActivity,
                        "Las contraseñas no coinciden",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    user.pass = newPasswordSecondTry.text.toString()
                    GlobalScope.launch(Dispatchers.IO) {
                        val changePassword =
                            userController.changePass(user)
                        changePassword.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                when (response.code()) {
                                    200 -> {
                                        Toast.makeText(
                                            this@ChangePasswordActivity,
                                            "Contraseña cambiada correctamente",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        finish()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(
                                    this@ChangePasswordActivity,
                                    "${t.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }
            }
        }

        val togglePasswordButton1 = findViewById<ImageButton>(R.id.showPasswordButton1)
        val togglePasswordButton2 = findViewById<ImageButton>(R.id.showPasswordButton2)
        val togglePasswordButton3 = findViewById<ImageButton>(R.id.showPasswordButton3)
        val passwordPlainText1 = findViewById<EditText>(R.id.actualPasswordEditText)
        val passwordPlainText2 = findViewById<EditText>(R.id.newPasswordFirstTryEditText)
        val passwordPlainText3 = findViewById<EditText>(R.id.newPasswordSecondTryEditText)

        var isPasswordVisible = false

        togglePasswordButton1.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            val eyeIcon = if (isPasswordVisible) {
                R.drawable.eye_open
            } else {
                R.drawable.eye_closed
            }
            togglePasswordButton1.setImageResource(eyeIcon)

            val inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordPlainText1.inputType = inputType
            passwordPlainText1.setSelection(passwordPlainText1.length())
        }

        togglePasswordButton2.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            val eyeIcon = if (isPasswordVisible) {
                R.drawable.eye_open
            } else {
                R.drawable.eye_closed
            }
            togglePasswordButton2.setImageResource(eyeIcon)

            val inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordPlainText2.inputType = inputType
            passwordPlainText2.setSelection(passwordPlainText2.length())
        }

        togglePasswordButton3.setOnClickListener {
            isPasswordVisible = !isPasswordVisible

            val eyeIcon = if (isPasswordVisible) {
                R.drawable.eye_open
            } else {
                R.drawable.eye_closed
            }
            togglePasswordButton3.setImageResource(eyeIcon)

            val inputType = if (isPasswordVisible) {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordPlainText3.inputType = inputType
            passwordPlainText3.setSelection(passwordPlainText3.length())
        }
    }
}