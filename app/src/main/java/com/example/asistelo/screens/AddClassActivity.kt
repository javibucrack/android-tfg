package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.asistelo.R
import com.example.asistelo.controllers.ClassController
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*

class AddClassActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_class)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val classController = retrofit.create(ClassController::class.java)

        val admin = intent.getSerializableExtra("admin") as UserDto

        val addClassButton = findViewById<Button>(R.id.addClassButton)

        val name = findViewById<EditText>(R.id.insertClassNameEditText)

        val actualDate = Date()


        addClassButton.setOnClickListener {
            val classDto = ClassDto(
                null,
                actualDate,
                null,
                name.text.toString(),
                admin,
                null,
                null,
                null
            )

            GlobalScope.launch(Dispatchers.IO) {
                val action =
                    classController.addClass(classDto, admin.id!!)
                action.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response.code()) {
                            201 -> {
                                Toast.makeText(
                                    this@AddClassActivity,
                                    "Clase creada correctamente",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            409 -> {
                                Toast.makeText(
                                    this@AddClassActivity,
                                    "La clase ya existe",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            403 -> {
                                Toast.makeText(
                                    this@AddClassActivity,
                                    "No tienes permisos para insertar clases",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@AddClassActivity,
                            "${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }

    }
}