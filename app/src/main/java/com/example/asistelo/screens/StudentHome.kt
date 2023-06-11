package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Clase que se le enseña al estudiante cuando inicia sesión.
 * Contiene los botones con las distintas actividades que este puede hacer.
 */
class StudentHome : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val studentController = RetrofitClient.retrofit.create(UserController::class.java)

        val student = intent.getSerializableExtra("student") as UserDto

        val studentNameTextView = findViewById<TextView>(R.id.studentNameTextView)
        if (student.secondSurname == null) {
            studentNameTextView.text = student.name + " " + student.firstSurname
        } else {
            studentNameTextView.text =
                student.name + " " + student.firstSurname + " " + student.secondSurname
        }

        val showSubjectButton = findViewById<Button>(R.id.viewSubjectsButton)


        showSubjectButton.setOnClickListener {

            val user = studentController.getUser(student.id!!)

            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    when (response.code()) {
                        200 -> {
                            val subjectList = response.body()!!.subjectList

                            val subjects = mutableListOf<String>()

                            if (subjectList != null) {
                                for (subject in subjectList) {
                                    subjects.add(subject.name!!)
                                }
                            }
                            if (subjects.size > 0) {
                                val showSubjectsIntent =
                                    Intent(this@StudentHome, StudentSubjectsScreen::class.java)
                                showSubjectsIntent.putExtra(
                                    "student",
                                    intent.getSerializableExtra("student") as UserDto
                                )
                                showSubjectsIntent.putStringArrayListExtra(
                                    "subjects",
                                    ArrayList(subjects)
                                )
                                startActivity(showSubjectsIntent)
                            } else {
                                Toast.makeText(
                                    this@StudentHome,
                                    "No hay asignaturas",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@StudentHome,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@StudentHome,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })

        }

        val showAbsencesButton = findViewById<Button>(R.id.viewAbsencesButton)

        showAbsencesButton.setOnClickListener {

            val user = studentController.getAbsences(student.id!!)

            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    when (response.code()) {
                        200 -> {
                            val absenceList = response.body()!!.absenceList

                            val absences = mutableListOf<AbsenceDto>()

                            if (absenceList != null) {
                                for (absence in absenceList) {
                                    absences.add(absence)
                                }
                            }
                            if (absences.size > 0) {
                                val showAbsencesIntent =
                                    Intent(this@StudentHome, StudentAbsencesScreen::class.java)
                                showAbsencesIntent.putExtra(
                                    "student",
                                    intent.getSerializableExtra("student") as UserDto
                                )
                                showAbsencesIntent.putExtra(
                                    "absences",
                                    ArrayList(absences)
                                )
                                startActivity(showAbsencesIntent)
                            } else {
                                Toast.makeText(
                                    this@StudentHome,
                                    "No hay faltas",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@StudentHome,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@StudentHome,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })

        }

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_logout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileMenu -> {
                val profileIntent =
                    Intent(this@StudentHome, ProfileScreen::class.java)
                profileIntent.putExtra(
                    "user",
                    intent.getSerializableExtra("student") as UserDto
                )
                startActivity(profileIntent)
                return true
            }
            R.id.logOutMenu -> {
                val logOutIntent =
                    Intent(this@StudentHome, MainActivity::class.java)
                Toast.makeText(this@StudentHome, "Cerrando sesión", Toast.LENGTH_LONG).show()
                startActivity(logOutIntent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
