package com.example.asistelo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.asistelo.R
import com.example.asistelo.adapter.ClassForAbsencesAdapter
import com.example.asistelo.adapter.SubjectForAbsenceListAdapter
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class AddAbsencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_absences)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val userController = retrofit.create(UserController::class.java)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val classList = teacher.classList!!.toMutableList()
        val selectClassSpinner = findViewById<Spinner>(R.id.selectClassSpinner)
        val classAdapter =
            ClassForAbsencesAdapter(this, android.R.layout.simple_spinner_item, classList)
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectClassSpinner.adapter = classAdapter
        var selectedClass = ClassDto(null, null, null, null, null, null, null, null)

        val subjectList = teacher.subjectList!!.toMutableList()
        val selectSubjectSpinner = findViewById<Spinner>(R.id.selectSubjectSpinner)
        val subjectAdapter =
            SubjectForAbsenceListAdapter(this, android.R.layout.simple_spinner_item, subjectList)
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectSubjectSpinner.adapter = subjectAdapter
        var selectedSubject =
            SubjectDto(null, null, null, null, null, null, null, null, null, null, null, null)

        val searchStudentsButton = findViewById<Button>(R.id.searchStudentsButton)

        // Aquí puedes agregar los listeners a los Spinners o hacer cualquier otra acción necesaria.
        selectClassSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        selectSubjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSubject = subjectList[position]
                // Realiza alguna acción con la asignatura seleccionada
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el spinner
            }
        }

        searchStudentsButton.setOnClickListener {

            val studentsList =
                userController.getAllStudents("1", selectedClass.id!!, selectedSubject.id!!)

            studentsList.enqueue(object : Callback<List<UserDto>> {
                override fun onResponse(
                    call: Call<List<UserDto>>,
                    response: Response<List<UserDto>>
                ) {
                    when (response.code()) {
                        200 -> {
                            val students = mutableListOf<UserDto>()

                            val studentsSize = response.body()!!.size
                            for (student in 0 until studentsSize) {
                                students.add(response.body()!![student])
                            }
                            val addAbsencesToStudents =
                                Intent(this@AddAbsencesActivity, AddAbsencesToStudents::class.java)
                            addAbsencesToStudents.putExtra(
                                "teacher",
                                intent.getSerializableExtra("teacher") as UserDto
                            )
                            addAbsencesToStudents.putExtra("students", ArrayList(students))
                            addAbsencesToStudents.putExtra("class", selectedClass)
                            addAbsencesToStudents.putExtra("subject", selectedSubject)
                            startActivity(addAbsencesToStudents)
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@AddAbsencesActivity,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<List<UserDto>>, t: Throwable) {
                    Toast.makeText(
                        this@AddAbsencesActivity,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })
        }
    }
}
