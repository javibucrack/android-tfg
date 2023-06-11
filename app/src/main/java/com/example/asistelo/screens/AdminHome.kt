package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import com.example.asistelo.controllers.dto.UserDto
import kotlinx.coroutines.*

/**
 * Clase que se le enseña al administrador cuando inicia sesión.
 * Contiene los botones con las distintas actividades que este puede hacer.
 */
class AdminHome : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val classController = RetrofitClient.retrofit.create(ClassController::class.java)

        val subjectController = RetrofitClient.retrofit.create(SubjectController::class.java)

        val admin = intent.getSerializableExtra("admin") as UserDto

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
                return true
            }
            R.id.logOutMenu -> {
                val logOutIntent =
                    Intent(this@AdminHome, MainActivity::class.java)
                Toast.makeText(this@AdminHome, "Cerrando sesión", Toast.LENGTH_LONG).show()
                startActivity(logOutIntent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
