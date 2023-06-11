package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Clase MainActivity que es la principal del proyecto, la que se abre nada más abrir la aplicación,
 * y que contiene el inicio de sesión.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val login = RetrofitClient.retrofit.create(UserController::class.java)

        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val user = login.login(
                findViewById<EditText>(R.id.emailPlainText).text.toString(),
                findViewById<EditText>(R.id.passwordPlainText).text.toString()
            )

            /**
             * Método que permite el inicio de sesión, y que dependiendo del código de respuesta
             * del servicio, realizará una acción u otra.
             */
            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    when (response.code()) {
                        200 -> {
                            val user = response.body()
                            when (response.body()?.role?.name) {
                                "Student" -> {
                                    val studentIntent =
                                        Intent(this@MainActivity, StudentHome::class.java)
                                    studentIntent.putExtra("student", user)
                                    startActivity(studentIntent)
                                    finish()
                                }
                                "Teacher" -> {
                                    val teacherIntent =
                                        Intent(this@MainActivity, TeacherHome::class.java)
                                    teacherIntent.putExtra("teacher", user)
                                    startActivity(teacherIntent)
                                    finish()
                                }
                                "Admin" -> {
                                    val adminIntent =
                                        Intent(this@MainActivity, AdminHome::class.java)
                                    adminIntent.putExtra("admin", user)
                                    startActivity(adminIntent)
                                    finish()
                                }
                            }
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@MainActivity,
                                "Los datos introducidos no son correctos",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                /**
                 * Método que se lanza en caso de fallar la conexión al Retrofit.
                 */
                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "No es posible conectarse.\nPor favor intentalo más tarde",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}
