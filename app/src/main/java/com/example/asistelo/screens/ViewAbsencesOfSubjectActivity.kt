package com.example.asistelo.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.AbsencesOfSubjectAdapter
import com.example.asistelo.adapter.StudentListForAbsencesAdapter
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.decorator.SimpleItemDecoration

class ViewAbsencesOfSubjectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_absences_of_subject)

        val absences = intent.getSerializableExtra("absences") as List<AbsenceDto>

        val showSubjectNameInAbsences = findViewById<TextView>(R.id.showSubjectNameInAbsences)
        showSubjectNameInAbsences.text = absences.get(0).subject!!.name

        val absencesOfSubjectRecyclerView =
            findViewById<RecyclerView>(R.id.absencesOfSubjectRecyclerView)

        val absencesOfSubjectAdapter =
            AbsencesOfSubjectAdapter(absences, applicationContext)

        absencesOfSubjectRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        absencesOfSubjectRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        absencesOfSubjectRecyclerView.adapter = absencesOfSubjectAdapter

        //TODO: hacer adapter y recycler view que enseñe las faltas de esa asignatura con un checkbox de borrar
        //TODO: si está marcado el checkbox de borrar, recorrer el endpoint de delete y borrarlo
    }
}