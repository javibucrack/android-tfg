package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.ClassForAbsencesAdapter
import com.example.asistelo.adapter.SubjectForAddUserAdapter
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.RolDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Clase que permite crear un usuario nuevo.
 */
class AddUserActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val userController = RetrofitClient.retrofit.create(UserController::class.java)

        val addUserButton = findViewById<Button>(R.id.addUserButton)

        val admin = intent.getSerializableExtra("admin") as UserDto

        val subjects = intent.getSerializableExtra("subjects") as List<SubjectDto>

        val subjectRecyclerView = findViewById<RecyclerView>(R.id.subjectListRecyclerView)

        val subjectForAddUserAdapter = SubjectForAddUserAdapter(subjects, applicationContext)

        subjectRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        subjectRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        subjectRecyclerView.adapter = subjectForAddUserAdapter

        val classList = intent.getSerializableExtra("classList") as List<ClassDto>

        val classSpinner = findViewById<Spinner>(R.id.selectClassSpinnerForAddUser)

        var selectedClass = ClassDto(null, null, null, null, null, null, null, null)

        val classAdapter =
            ClassForAbsencesAdapter(this, android.R.layout.simple_spinner_item, classList)
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classSpinner.adapter = classAdapter

        val name = findViewById<EditText>(R.id.insertNameEditText)

        val firstSurname = findViewById<EditText>(R.id.insertFirstSurnameEditText)

        val secondSurname = findViewById<EditText>(R.id.insertSecondSurnameEditText)

        val email = findViewById<EditText>(R.id.insertEmailEditText)

        val roles = arrayOf("Estudiante", "Profesor", "Administrador")

        val rolSpinner = findViewById<Spinner>(R.id.selectRolSpinner)

        val selectedRol = RolDto(null, null)


        val rolesAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rolSpinner.adapter = rolesAdapter

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
                selectedRol.id = 1
                // No se seleccionó nada en el spinner
            }
        }

        // Aquí puedes agregar los listeners a los Spinners o hacer cualquier otra acción necesaria.
        classSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedClass = classList[position]
                // Realiza alguna acción con la clase seleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el spinner
            }
        }

        val actualDate = Date()

        addUserButton.setOnClickListener {
            val subjectList = mutableListOf<SubjectDto>()
            for ((subjectId, isChecked) in subjectForAddUserAdapter.checkedMap) {
                if (isChecked) {
                    for (subject in subjects.indices) {
                        if (subjects[subject].id == subjectId) {
                            subjectList.add(subjects[subject])
                        }
                    }
                }
            }
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
                subjectList,
                mutableListOf(selectedClass)
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
                                val goHome = Intent(this@AddUserActivity, AdminHome::class.java)
                                goHome.putExtra("admin", admin)
                                startActivity(goHome)
                                //TODO: que te lleve al home
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