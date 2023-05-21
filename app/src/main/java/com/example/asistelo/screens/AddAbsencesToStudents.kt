package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto

class AddAbsencesToStudents : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_absences_to_students)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val className = intent.getSerializableExtra("class") as ClassDto

        val subject = intent.getSerializableExtra("subject") as SubjectDto

        val students = intent.getSerializableExtra("students") as List<UserDto>

        val classNameTextView = findViewById<TextView>(R.id.showClassNameTextView)
        classNameTextView.text = className.name

        val subjectNameTextView = findViewById<TextView>(R.id.showSubjectNameTextView)
        subjectNameTextView.text = subject.name
    }
}