package com.example.asistelo.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.AbsenceController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * Clase que permite elegir una fecha y un número de horas para
 * poder poner una falta a una persona.
 */
class AddAbsenceScreen : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_absence_screen)

        val absenceController = RetrofitClient.retrofit.create(AbsenceController::class.java)

        val subject = intent.getSerializableExtra("subject") as SubjectDto

        val student = intent.getSerializableExtra("student") as UserDto

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val showStudentName = findViewById<TextView>(R.id.showStudentNameInAddAbsenceScreen)
        if (student.secondSurname == null) {
            showStudentName.text = student.name + " " + student.firstSurname
        } else {
            showStudentName.text =
                student.name + " " + student.firstSurname + " " + student.secondSurname
        }
        val showSubjectName = findViewById<TextView>(R.id.showSubjectNameInAddAbsenceScreen)
        showSubjectName.text = subject.name

        val calendar = findViewById<CalendarView>(R.id.selectDateAbsence)

        val numHoursText = findViewById<EditText>(R.id.selectNumHoursAbsence)


        var selectedDate = Date()
        calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(year, month, dayOfMonth)
            selectedDate = date.time
        }


        val addAbsenceButton = findViewById<Button>(R.id.addAbsenceButton)

        addAbsenceButton.setOnClickListener {

            val numHours = numHoursText.text.toString().toInt()
            val absence = AbsenceDto(
                null,
                numHours,
                selectedDate,
                null,
                null,
                null,
                null,
                null
            )
            GlobalScope.launch(Dispatchers.IO) {
                val action =
                    absenceController.newAbsence(absence, subject.id!!, student.id!!, teacher.id!!)
                action.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        when (response.code()) {
                            201 -> {
                                Toast.makeText(
                                    this@AddAbsenceScreen,
                                    "Falta creada correctamente",
                                    Toast.LENGTH_LONG
                                ).show()
                                val goHome = Intent(this@AddAbsenceScreen, TeacherHome::class.java)
                                goHome.putExtra("teacher", teacher)
                                startActivity(goHome)
                                finish()
                            }
                            404 -> {
                                Toast.makeText(
                                    this@AddAbsenceScreen,
                                    "User not found",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            406 -> {
                                Toast.makeText(
                                    this@AddAbsenceScreen,
                                    "Numero de horas no válido",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            422 -> {
                                Toast.makeText(
                                    this@AddAbsenceScreen,
                                    "Fecha no válida",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(
                            this@AddAbsenceScreen,
                            "${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        }
    }
}