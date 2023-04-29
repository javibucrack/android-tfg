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
            val user = login.searchUser(
                findViewById<EditText>(R.id.emailPlainText).text.toString(),
                findViewById<EditText>(R.id.passwordPlainText).text.toString()
            )
            val studentIntent = Intent(this, StudentHome::class.java)
            val teacherIntent = Intent(this, TeacherHome::class.java)


            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    when (response.code()) {
                        200 -> {
                            when (response.body()?.role?.name) {
                                "student" -> {
                                    startActivity(studentIntent)
                                }
                                "teacher" -> {
                                    startActivity(teacherIntent)
                                }
                                "admin" -> {

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
