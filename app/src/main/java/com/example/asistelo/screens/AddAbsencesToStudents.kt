package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.StudentListForAbsencesAdapter
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.AbsenceController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.ClassDto
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
 * Clase que permite pasar lista y poner falta a una lista de alumnos.
 */
class AddAbsencesToStudents : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_absences_to_students)

        val absenceController = RetrofitClient.retrofit.create(AbsenceController::class.java)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val className = intent.getSerializableExtra("class") as ClassDto

        val subject = intent.getSerializableExtra("subject") as SubjectDto

        val students = intent.getSerializableExtra("students") as List<UserDto>

        val classNameTextView = findViewById<TextView>(R.id.showClassNameTextView)
        classNameTextView.text = className.name

        val subjectNameTextView = findViewById<TextView>(R.id.showSubjectNameTextView)
        subjectNameTextView.text = subject.name

        val addAbsencesToStudentListButton =
            findViewById<Button>(R.id.addAbsencesToStudentListButton)

        val studentListRecyclerView = findViewById<RecyclerView>(R.id.studentListRecyclerView)

        val studentListForAbsencesAdapter =
            StudentListForAbsencesAdapter(students, applicationContext)

        studentListRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        studentListRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        studentListRecyclerView.adapter = studentListForAbsencesAdapter


        addAbsencesToStudentListButton.setOnClickListener {
            for ((studentId, isChecked) in studentListForAbsencesAdapter.checkedMap) {
                if (isChecked) {
                    val selectedDate = Date()
                    val absence = AbsenceDto(
                        null,
                        1,
                        selectedDate,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                    GlobalScope.launch(Dispatchers.IO) {
                        val action =
                            absenceController.newAbsence(
                                absence,
                                subject.id!!,
                                studentId,
                                teacher.id!!
                            )
                        action.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                when (response.code()) {
                                    201 -> {
                                        Toast.makeText(
                                            this@AddAbsencesToStudents,
                                            "Falta creada correctamente",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val goHome = Intent(this@AddAbsencesToStudents, TeacherHome::class.java)
                                        goHome.putExtra("teacher", teacher)
                                        startActivity(goHome)
                                        finish()
                                    }
                                    404 -> {
                                        Toast.makeText(
                                            this@AddAbsencesToStudents,
                                            "User not found",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    406 -> {
                                        Toast.makeText(
                                            this@AddAbsencesToStudents,
                                            "Numero de horas no válido",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    422 -> {
                                        Toast.makeText(
                                            this@AddAbsencesToStudents,
                                            "Fecha no válida",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(
                                    this@AddAbsencesToStudents,
                                    "${t.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }

                }
            }
        }
    }
}