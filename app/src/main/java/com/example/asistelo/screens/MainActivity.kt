package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val login = retrofit.create(UserController::class.java)


        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val user = login.getUser(
                findViewById<EditText>(R.id.emailPlainText).text.toString(),
                findViewById<EditText>(R.id.passwordPlainText).text.toString()
            )

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
                                    //TODO: añadir las funciones que tiene un administrador
                                }
                            }
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@MainActivity,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}
