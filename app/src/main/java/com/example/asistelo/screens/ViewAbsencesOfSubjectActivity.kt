package com.example.asistelo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.AbsencesOfSubjectAdapter
import com.example.asistelo.adapter.StudentListForAbsencesAdapter
import com.example.asistelo.controllers.AbsenceController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.decorator.SimpleItemDecoration
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.*

class ViewAbsencesOfSubjectActivity : AppCompatActivity() {
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_absences_of_subject)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val absenceController = retrofit.create(AbsenceController::class.java)

        val absences = intent.getSerializableExtra("absences") as List<AbsenceDto>

        val deleteAbsencesButton = findViewById<Button>(R.id.deleteAbsencesButton)

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

        deleteAbsencesButton.setOnClickListener {
            for ((absenceId, isChecked) in absencesOfSubjectAdapter.checkedMap) {
                if (isChecked) {
                    GlobalScope.launch(Dispatchers.IO) {
                        val deleteAbsence =
                            absenceController.deleteAbsence(
                                absenceId
                            )
                        deleteAbsence.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                when (response.code()) {
                                    200 -> {
                                        Toast.makeText(
                                            this@ViewAbsencesOfSubjectActivity,
                                            "Faltas quitadas correctamente",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        finish()
                                    }
                                    404 -> {
                                        Toast.makeText(
                                            this@ViewAbsencesOfSubjectActivity,
                                            "User not found",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    406 -> {
                                        Toast.makeText(
                                            this@ViewAbsencesOfSubjectActivity,
                                            "Numero de horas no válido",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    422 -> {
                                        Toast.makeText(
                                            this@ViewAbsencesOfSubjectActivity,
                                            "Fecha no válida",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                }

                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                Toast.makeText(
                                    this@ViewAbsencesOfSubjectActivity,
                                    "${t.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        })
                    }
                }

                //TODO: si está marcado el checkbox de borrar, recorrer el endpoint de delete y borrarlo
            }
        }
    }
}