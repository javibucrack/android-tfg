package com.example.asistelo.screens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.ClassAdapter
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration

class ClassesOfTeacherScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classes_of_teacher_screen)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val nameTextView = findViewById<TextView>(R.id.showUserInClasses)
        nameTextView.text =
            teacher.name + " " + teacher.firstSurname + " " + teacher.secondSurname

        val classList = intent.getSerializableExtra("classes") as List<ClassDto>

        val classesRecyclerView = findViewById<RecyclerView>(R.id.classesRecyclerView)

        val classAdapter = ClassAdapter(classList, teacher, applicationContext)

        classesRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        classesRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        classesRecyclerView.adapter = classAdapter

    }
}