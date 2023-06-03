package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.asistelo.R
import com.example.asistelo.controllers.ClassController
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.*
import retrofit2.converter.jackson.JacksonConverterFactory

class TeacherHome : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val teacherController = retrofit.create(ClassController::class.java)

        val userController = retrofit.create(UserController::class.java)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val teacherNameTextView = findViewById<TextView>(R.id.teacherNameTextView)
        if (teacher.secondSurname == null) {
            teacherNameTextView.text = teacher.name + " " + teacher.firstSurname
        } else {
            teacherNameTextView.text =
                teacher.name + " " + teacher.firstSurname + " " + teacher.secondSurname
        }


        val showClassesButton = findViewById<Button>(R.id.addAbsenceButtonActivity)

        val addAbsencesButtonActivity = findViewById<Button>(R.id.addAbsencesActivityButton)

        val viewAndDeleteAbsencesButton = findViewById<Button>(R.id.viewAndDeleteAbsencesButton)

        showClassesButton.setOnClickListener {

            val user = teacherController.getClasses(teacher.id!!)

            user.enqueue(object : Callback<List<ClassDto>> {
                override fun onResponse(
                    call: Call<List<ClassDto>>,
                    response: Response<List<ClassDto>>
                ) {
                    when (response.code()) {
                        200 -> {
                            val classes = mutableListOf<ClassDto>()

                            val classSize = response.body()!!.size
                            for (clase in 0 until classSize) {
                                classes.add(response.body()!![clase])
                            }

                            val showClassesIntent =
                                Intent(this@TeacherHome, ClassesOfTeacherScreen::class.java)
                            showClassesIntent.putExtra(
                                "teacher",
                                intent.getSerializableExtra("teacher") as UserDto
                            )
                            showClassesIntent.putExtra(
                                "classes",
                                ArrayList(classes)
                            )
                            startActivity(showClassesIntent)
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@TeacherHome,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<List<ClassDto>>, t: Throwable) {
                    Toast.makeText(
                        this@TeacherHome,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })
        }

        addAbsencesButtonActivity.setOnClickListener {
            val user = userController.getUser(teacher.id!!)

            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(
                    call: Call<UserDto>,
                    response: Response<UserDto>
                ) {
                    when (response.code()) {
                        200 -> {
                            val addAbsencesActivity =
                                Intent(this@TeacherHome, AddAbsencesActivity::class.java)
                            addAbsencesActivity.putExtra(
                                "teacher",
                                response.body()
                            )
                            startActivity(addAbsencesActivity)
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@TeacherHome,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@TeacherHome,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })
        }

        viewAndDeleteAbsencesButton.setOnClickListener {
            val user = userController.getUser(teacher.id!!)

            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(
                    call: Call<UserDto>,
                    response: Response<UserDto>
                ) {
                    when (response.code()) {
                        200 -> {
                            val selectSubjectAbsencesActivity =
                                Intent(this@TeacherHome, SelectSubjectAbsencesActivity::class.java)
                            selectSubjectAbsencesActivity.putExtra(
                                "teacher",
                                response.body()
                            )
                            startActivity(selectSubjectAbsencesActivity)
                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@TeacherHome,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Toast.makeText(
                        this@TeacherHome,
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
                    Intent(this@TeacherHome, ProfileScreen::class.java)
                profileIntent.putExtra("user", intent.getSerializableExtra("teacher") as UserDto)
                startActivity(profileIntent)
                // Open profile activity
                return true
            }
            R.id.logOutMenu -> {
                val logOutIntent =
                    Intent(this@TeacherHome, MainActivity::class.java)
                Toast.makeText(this@TeacherHome, "Cerrando sesión", Toast.LENGTH_LONG).show()
                startActivity(logOutIntent)
                // Open settings activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}