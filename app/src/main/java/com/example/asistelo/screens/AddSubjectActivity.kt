package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.config.RetrofitClient
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
import java.util.*

/**
 * Clase que permite crear una asignatura nueva.
 */
class AddSubjectActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subject)

        val subjectController = RetrofitClient.retrofit.create(SubjectController::class.java)

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
                                val goHome = Intent(this@AddSubjectActivity, AdminHome::class.java)
                                goHome.putExtra("admin", admin)
                                startActivity(goHome)
                                finish()
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