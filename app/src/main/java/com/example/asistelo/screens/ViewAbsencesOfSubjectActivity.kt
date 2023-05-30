package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.AbsenceDto

class ViewAbsencesOfSubjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_absences_of_subject)

        val absences = intent.getSerializableExtra("absences") as List<AbsenceDto>

        //TODO: hacer adapter y recycler view que enseñe las faltas de esa asignatura con un checkbox de borrar
        //TODO: si está marcado el checkbox de borrar, recorrer el endpoint de delete y borrarlo
    }
}