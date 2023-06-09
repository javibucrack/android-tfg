package com.example.asistelo.screens

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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

class ChangePasswordActivity : AppCompatActivity() {
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
    }
}