package com.example.asistelo.screens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.SubjectForAbsencesAdapter
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration

class SubjectsOfStudentForAbsencesScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subjects_of_student_for_absences_screen)

        val student = intent.getSerializableExtra("student") as UserDto

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val subjectList = student.subjectList

        val studentNameTextView = findViewById<TextView>(R.id.showStudentNameInSubjectList)
        if (student.secondSurname == null) {
            studentNameTextView.text = student.name + " " + student.firstSurname
        } else {
            studentNameTextView.text =
                student.name + " " + student.firstSurname + " " + student.secondSurname
        }

        val subjectsRecyclerView = findViewById<RecyclerView>(R.id.subjectsOfStudentRecyclerView)

        val subjectsAdapter = SubjectForAbsencesAdapter(subjectList!!, student, teacher,applicationContext)

        subjectsRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        subjectsRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        subjectsRecyclerView.adapter = subjectsAdapter
    }
}