package com.example.asistelo.screens

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
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class StudentHome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val studentSubjects = retrofit.create(UserController::class.java)

        val student = intent.getSerializableExtra("student") as UserDto

        val studentNameTextView = findViewById<TextView>(R.id.studentNameTextView)
        studentNameTextView.text = student.name

        val showSubjectButton = findViewById<Button>(R.id.viewSubjectsButton)

        showSubjectButton.setOnClickListener {

            val user = studentSubjects.getStudent(student.id)

            user.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    when (response.code()) {
                        200 -> {
                            val subjectList = response.body()!!.subjects

                            val subjects = mutableListOf<String>()

                            if (subjectList != null) {
                                for (subject in subjectList) {
                                    subjects.add(subject.name)
                                }
                            }
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
        menuInflater.inflate(R.menu.menu_select_role, menu)
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
