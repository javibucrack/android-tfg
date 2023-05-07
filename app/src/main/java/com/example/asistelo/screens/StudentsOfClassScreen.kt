package com.example.asistelo.screens

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.StudentAdapter
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration

class StudentsOfClassScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_students_of_class_screen)

        val className = intent.getSerializableExtra("class") as ClassDto

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val classNameTextView = findViewById<TextView>(R.id.showClassNameInStudentList)
        classNameTextView.text = className.name

        val students = className.students

        val studentsRecyclerView = findViewById<RecyclerView>(R.id.studentsOfClassRecyclerView)

        if (students!!.size > 1) {
            val studentAdapter = StudentAdapter(students,teacher ,applicationContext)
            studentsRecyclerView.layoutManager =
                GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

            studentsRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

            studentsRecyclerView.adapter = studentAdapter

        } else {
            Toast.makeText(
                this@StudentsOfClassScreen,
                "Lista de estudiantes vacia",
                Toast.LENGTH_LONG
            ).show()
        }


    }
}