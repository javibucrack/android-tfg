package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.LoginController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var roleSpinner: Spinner? = null
        val roles = arrayOf("Estudiante", "Profesor", "Administrador")

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val login = retrofit.create(LoginController::class.java)


        roleSpinner = findViewById(R.id.roleSpinner)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        roleSpinner.adapter = adapter

        val selectRol = findViewById<TextView>(R.id.selectRol)
        selectRol.setOnClickListener { v: View? -> roleSpinner.performClick() }


        val enterButton = findViewById<Button>(R.id.enterButton)
        enterButton.setOnClickListener { v: View? ->
//            val selectedRole = roleSpinner.selectedItem.toString()
//            if (selectedRole == "Estudiante") {
//                val intent = Intent(this, StudentHome::class.java)
//                startActivity(intent)
//            } else if (selectedRole == "Profesor") {
//                val intent = Intent(this, TeacherHome::class.java)
//                startActivity(intent)
//            }


            val user = login.searchUser(
                1,
                findViewById<EditText>(R.id.emailPlainText).text.toString(),
                findViewById<EditText>(R.id.passwordPlainText).text.toString()
            )
            val intent = Intent(this, StudentHome::class.java)

            user.enqueue(object : Callback<Boolean> {
                override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                    when (response.code()) {
                        200 -> {
                            startActivity(intent)
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@MainActivity,
                                "Código de respuesta desconocido ${response.code()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<Boolean>, t: Throwable) {
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
