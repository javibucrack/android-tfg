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
import com.example.asistelo.controllers.ClassController
import com.example.asistelo.controllers.SubjectController
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminHome : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val classController = RetrofitClient.retrofit.create(ClassController::class.java)

        val subjectController = RetrofitClient.retrofit.create(SubjectController::class.java)

        val admin = intent.getSerializableExtra("admin") as UserDto

        val subjects = mutableListOf<SubjectDto>()

        val classes = mutableListOf<ClassDto>()

        val adminNameTextView = findViewById<TextView>(R.id.adminNameTextView)
        if (admin.secondSurname == null) {
            adminNameTextView.text = admin.name + " " + admin.firstSurname
        } else {
            adminNameTextView.text =
                admin.name + " " + admin.firstSurname + " " + admin.secondSurname
        }

        val addUserButton = findViewById<Button>(R.id.addUserActivityButton)

        val addClassButton = findViewById<Button>(R.id.addClassActivityButton)

        val addSubjectButton = findViewById<Button>(R.id.addSubjectActivityButton)


        addUserButton.setOnClickListener {

            val getClassList = classController.getAllClasses()
            val getSubjects = subjectController.getAllSubjects()
            val addUserActivityIntent = Intent(this@AdminHome, AddUserActivity::class.java)

            val getClassListDeferred = GlobalScope.async(Dispatchers.IO) {
                getClassList.execute().body()
            }

            val getSubjectsDeferred = GlobalScope.async(Dispatchers.IO) {
                getSubjects.execute().body()
            }

            GlobalScope.launch(Dispatchers.Main) {
                val classList = getClassListDeferred.await()
                val subjectList = getSubjectsDeferred.await()

                // Verifica si las respuestas de las llamadas son exitosas
                if (classList != null && subjectList != null) {
                    addUserActivityIntent.putExtra("classList", ArrayList(classList))
                    addUserActivityIntent.putExtra("subjects", ArrayList(subjectList))
                    addUserActivityIntent.putExtra("admin", admin)
                    startActivity(addUserActivityIntent)
                } else {
                    Toast.makeText(
                        this@AdminHome,
                        "Error al obtener las listas",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }


//            getClassList.enqueue(object : Callback<List<ClassDto>> {
//                override fun onResponse(
//                    call: Call<List<ClassDto>>,
//                    response: Response<List<ClassDto>>
//                ) {
//                    when (response.code()) {
//                        200 -> {
//                            val classSize = response.body()!!.size
//                            for (clase in 0 until classSize) {
//                                classes.add(response.body()!![clase])
//                            }
//                            classes.add(
//                                0,
//                                ClassDto(null, null, null, "ninguno", null, null, null, null)
//                            )
//
//                            addUserActivityIntent.putExtra("classList", ArrayList(classes))
//                            addUserActivityIntent.putExtra("admin", admin)
//                            startActivity(addUserActivityIntent)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<ClassDto>>, t: Throwable) {
//                    Toast.makeText(
//                        this@AdminHome,
//                        "${t.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
//
//            getSubjects.enqueue(object : Callback<List<SubjectDto>> {
//                override fun onResponse(
//                    call: Call<List<SubjectDto>>,
//                    response: Response<List<SubjectDto>>
//                ) {
//                    when (response.code()) {
//                        200 -> {
//                            val subjectsSize = response.body()!!.size
//                            for (subject in 0 until subjectsSize) {
//                                subjects.add(response.body()!![subject])
//                            }
//
//                            addUserActivityIntent.putExtra("subjects", ArrayList(subjects))
//                            addUserActivityIntent.putExtra("admin", admin)
//                            startActivity(addUserActivityIntent)
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<List<SubjectDto>>, t: Throwable) {
//                    Toast.makeText(
//                        this@AdminHome,
//                        "${t.message}",
//                        Toast.LENGTH_LONG
//                    ).show()
//                }
//            })
//        }

        addClassButton.setOnClickListener {
            val addUserActivityIntent = Intent(this@AdminHome, AddClassActivity::class.java)
            addUserActivityIntent.putExtra("admin", admin)
            startActivity(addUserActivityIntent)
        }

        addSubjectButton.setOnClickListener {
            val addSubjectActivityIntent = Intent(this@AdminHome, AddSubjectActivity::class.java)
            addSubjectActivityIntent.putExtra("admin", admin)
            startActivity(addSubjectActivityIntent)
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
                    Intent(this@AdminHome, ProfileScreen::class.java)
                profileIntent.putExtra("user", intent.getSerializableExtra("admin") as UserDto)
                startActivity(profileIntent)
                // Open profile activity
                return true
            }
            R.id.logOutMenu -> {
                val logOutIntent =
                    Intent(this@AdminHome, MainActivity::class.java)
                Toast.makeText(this@AdminHome, "Cerrando sesión", Toast.LENGTH_LONG).show()
                startActivity(logOutIntent)
                // Open settings activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
