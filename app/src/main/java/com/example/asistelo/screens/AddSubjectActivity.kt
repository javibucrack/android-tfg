package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.asistelo.R
import com.example.asistelo.controllers.SubjectController
import com.example.asistelo.controllers.dto.SubjectDto
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

class AddSubjectActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val subjectController = retrofit.create(SubjectController::class.java)

        val admin = intent.getSerializableExtra("admin") as UserDto

        val addSubjectButton = findViewById<Button>(R.id.addSubjectButton)

        val name = findViewById<EditText>(R.id.insertSubjectNameEditText)

        val numHours = findViewById<EditText>(R.id.insertSubjectNumHoursEditText)

        val actualDate = Date()

        addSubjectButton.setOnClickListener {
            val totalHours = numHours.text.toString().toInt()
            val subject = SubjectDto(
                null,
                totalHours,
                actualDate,
                null,
                name.text.toString(),
                admin,
                null,
                null,
                null,
                null,
                null,
                null
            )

            GlobalScope.launch(Dispatchers.IO) {
                val action =
                    subjectController.addSubject(subject, admin.id!!)
                action.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response.code()) {
                            201 -> {
                                Toast.makeText(
                                    this@AddSubjectActivity,
                                    "Asignatura creada correctamente",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            409 -> {
                                Toast.makeText(
                                    this@AddSubjectActivity,
                                    "La asignatura ya existe",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            403 -> {
                                Toast.makeText(
                                    this@AddSubjectActivity,
                                    "No tienes permisos para insertar asignaturas",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@AddSubjectActivity,
                            "${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}