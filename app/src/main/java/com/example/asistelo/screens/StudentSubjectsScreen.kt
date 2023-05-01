package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class StudentSubjectsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_subjects_screen)

        val student = intent.getSerializableExtra("student") as UserDto

        val studentName = findViewById<TextView>(R.id.showStudentName)
        studentName.text = student.name

    }
}