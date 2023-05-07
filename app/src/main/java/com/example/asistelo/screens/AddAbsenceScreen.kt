package com.example.asistelo.screens

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.asistelo.R
import com.example.asistelo.controllers.AbsenceController
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.Date

class AddAbsenceScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_absence_screen)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val absenceController = retrofit.create(AbsenceController::class.java)


        val subject = intent.getSerializableExtra("subject") as SubjectDto

        val student = intent.getSerializableExtra("student") as UserDto

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val showStudentName = findViewById<TextView>(R.id.showStudentNameInAddAbsenceScreen)
        if (student.second_surname == null) {
            showStudentName.text = student.name + " " + student.first_surname
        } else {
            showStudentName.text =
                student.name + " " + student.first_surname + " " + student.second_surname
        }
        val showSubjectName = findViewById<TextView>(R.id.showSubjectNameInAddAbsenceScreen)
        showSubjectName.text = subject.name

        val showCalendarTextView = findViewById<TextView>(R.id.showCalendarTextView)

        val calendar = findViewById<CalendarView>(R.id.selectDateAbsence)

        val numHoursText = findViewById<EditText>(R.id.selectNumHoursAbsence)
        val numHours = numHoursText.text.toString().toIntOrNull() ?: 0


        showCalendarTextView.setOnClickListener {
            calendar.visibility = View.VISIBLE
        }

        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year" // Formato de fecha personalizado
            showCalendarTextView.text = selectedDate
            calendar.visibility = View.GONE // Oculta el calendario después de seleccionar una fecha
        }

        val date = Date(calendar.date)

        val addAbsenceButton = findViewById<Button>(R.id.addAbsenceButton)

        val absence = AbsenceDto(
            null,
            numHours,
            date,
            null,
            teacher,
            null,
            student,
            subject
        )

        //TODO: arreglar el metodo que envia la ausencia a la base de datos

        addAbsenceButton.setOnClickListener {
            absenceController.newAbsence(absence).execute()
        }
    }
}