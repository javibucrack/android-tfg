package com.example.asistelo.screens

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
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.*
import retrofit2.converter.jackson.JacksonConverterFactory

class TeacherHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val teacherController = retrofit.create(ClassController::class.java)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val teacherNameTextView = findViewById<TextView>(R.id.teacherNameTextView)
        teacherNameTextView.text = teacher.name


        val showClassesButton = findViewById<Button>(R.id.addAbsenceButtonActivity)

        showClassesButton.setOnClickListener {

            val user = teacherController.getClasses(teacher.id)

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
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_role, menu)
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
            R.id.settingsMenu -> {
                // Open settings activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}