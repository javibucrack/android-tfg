package com.example.asistelo.screens

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.RolDto
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

class AddUserActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val userController = retrofit.create(UserController::class.java)

        val addUserButton = findViewById<Button>(R.id.addUserButton)

        val admin = intent.getSerializableExtra("admin") as UserDto

        val name = findViewById<EditText>(R.id.insertNameEditText)

        val firstSurname = findViewById<EditText>(R.id.insertFirstSurnameEditText)

        val secondSurname = findViewById<EditText>(R.id.insertSecondSurnameEditText)

        val email = findViewById<EditText>(R.id.insertEmailEditText)

        val roles = arrayOf("Estudiante", "Profesor", "Administrador")
        
        val rolSpinner = findViewById<Spinner>(R.id.selectRolSpinner)

        val selectedRol = RolDto(null, null)


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolSpinner.adapter = adapter

        rolSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedRol.name = roles[position]
                when (selectedRol.name) {
                    "Estudiante" -> {
                        selectedRol.id = 1
                    }
                    "Profesor" -> {
                        selectedRol.id = 2

                    }
                    "Administrador" -> {
                        selectedRol.id = 3

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el spinner
            }
        }
        val actualDate = Date()

        val classes: List<ClassDto> = listOf()
        val subjects: List<SubjectDto> = listOf()

        addUserButton.setOnClickListener {
            val user = UserDto(
                null,
                actualDate,
                null,
                email.text.toString(),
                firstSurname.text.toString(),
                name.text.toString(),
                null,//Se añadirá una contraseña automática, que se controla desde el backend en java
                secondSurname.text.toString(),
                admin.id,
                null,
                selectedRol,
                null,
                subjects,
                classes
            )

            GlobalScope.launch(Dispatchers.IO) {
                val action =
                    userController.addUser(user, admin.id!!)
                action.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response.code()) {
                            201 -> {
                                Toast.makeText(
                                    this@AddUserActivity,
                                    "Usuario creado correctamente",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            409 -> {
                                Toast.makeText(
                                    this@AddUserActivity,
                                    "Error email",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            400 -> {
                                Toast.makeText(
                                    this@AddUserActivity,
                                    "Campos no están bien introducidos",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            403 -> {
                                Toast.makeText(
                                    this@AddUserActivity,
                                    "No tienes permisos para insertar usuarios",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@AddUserActivity,
                            "${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}