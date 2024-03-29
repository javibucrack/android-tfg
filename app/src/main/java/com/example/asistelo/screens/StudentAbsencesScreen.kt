package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.AbsenceAdapter
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration

/**
 * Clase que enseña la lista de ausencias de un alumno.
 */
class StudentAbsencesScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_absences_screen)

        val student = intent.getSerializableExtra("student") as UserDto

        val nameTextView = findViewById<TextView>(R.id.showUserNameInAbsences)
        nameTextView.text = student.name + " " + student.firstSurname + " " + student.secondSurname

        val absencesList = intent.getSerializableExtra("absences") as List<AbsenceDto>

        val absencesRecyclerView = findViewById<RecyclerView>(R.id.absencesRecyclerView)

        val absenceAdapter = AbsenceAdapter(absencesList, applicationContext)

        absencesRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        absencesRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        absencesRecyclerView.adapter = absenceAdapter
    }
}