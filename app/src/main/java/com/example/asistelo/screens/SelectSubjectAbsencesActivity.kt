package com.example.asistelo.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asistelo.R
import com.example.asistelo.adapter.SubjectForAbsenceListAdapter
import com.example.asistelo.config.RetrofitClient
import com.example.asistelo.controllers.AbsenceController
import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Clase que permite buscar las ausencias de una asignatura.
 */
class SelectSubjectAbsencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_subject_absences)

        val absenceController = RetrofitClient.retrofit.create(AbsenceController::class.java)

        val teacher = intent.getSerializableExtra("teacher") as UserDto

        val subjectList = teacher.subjectList!!.toMutableList()

        val searchAbsencesBySubjectButton = findViewById<Button>(R.id.searchAbsencesBySubjectButton)

        val selectSubjectSpinner = findViewById<Spinner>(R.id.selectSubjectForAbsencesSpinner)
        val subjectAdapter =
            SubjectForAbsenceListAdapter(this, android.R.layout.simple_spinner_item, subjectList)
        subjectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        selectSubjectSpinner.adapter = subjectAdapter
        var selectedSubject =
            SubjectDto(null, null, null, null, null, null, null, null, null, null, null, null)

        selectSubjectSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedSubject = subjectList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No se seleccionó nada en el spinner
            }
        }
        searchAbsencesBySubjectButton.setOnClickListener {

            val absenceList = absenceController.getAbsences(teacher.id!!, selectedSubject.id!!)


            absenceList.enqueue(object : Callback<List<AbsenceDto>> {
                override fun onResponse(
                    call: Call<List<AbsenceDto>>,
                    response: Response<List<AbsenceDto>>
                ) {
                    when (response.code()) {
                        200 -> {

                            val absences = mutableListOf<AbsenceDto>()

                            val absenceListSize = response.body()!!.size
                            for (absence in 0 until absenceListSize) {
                                absences.add(response.body()!![absence])
                            }
                            val viewAbsencesBySubjectActivity =
                                Intent(
                                    this@SelectSubjectAbsencesActivity,
                                    ViewAbsencesOfSubjectActivity::class.java
                                )
                            if (absences.size>0){
                                viewAbsencesBySubjectActivity.putExtra(
                                    "absences",
                                    ArrayList(absences)
                                )
                                startActivity(viewAbsencesBySubjectActivity)
                            }else{
                                Toast.makeText(
                                    this@SelectSubjectAbsencesActivity,
                                    "No hay faltas",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }
                        404 -> {
                            Log.e("login", "Código de respuesta desconocido ${response.code()}")
                            Toast.makeText(
                                this@SelectSubjectAbsencesActivity,
                                " ${response.body()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                }

                override fun onFailure(call: Call<List<AbsenceDto>>, t: Throwable) {
                    Toast.makeText(
                        this@SelectSubjectAbsencesActivity,
                        "${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }


            })
        }

    }
}