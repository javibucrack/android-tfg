package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class StudentHome : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val student = intent.getSerializableExtra("student") as UserDto

        val studentNameTextView = findViewById<TextView>(R.id.studentNameTextView)
        studentNameTextView.text = student.name

        val showSubjectButton = findViewById<Button>(R.id.viewSubjectsButton)

        showSubjectButton.setOnClickListener {
            val showSubjectsIntent =
                Intent(this@StudentHome, StudentSubjectsScreen::class.java)
            showSubjectsIntent.putExtra(
                "student",
                intent.getSerializableExtra("student") as UserDto
            )
            startActivity(showSubjectsIntent)
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
                profileIntent.putExtra("user", intent.getSerializableExtra("student") as UserDto)
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