package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.SubjectAdapter
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration

class StudentSubjectsScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_subjects_screen)


        val student = intent.getSerializableExtra("student") as UserDto

        val nameTextView = findViewById<TextView>(R.id.showUserNameInSubjectsList)
        nameTextView.text = student.name + " " + student.firstSurname + " " + student.secondSurname

        val subjectList = intent.getSerializableExtra("subjects") as List<String>

        val subjectsRecyclerView = findViewById<RecyclerView>(R.id.subjectsRecyclerView)

        val subjectsAdapter = SubjectAdapter(subjectList, applicationContext)

        subjectsRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        subjectsRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        subjectsRecyclerView.adapter = subjectsAdapter
    }
}