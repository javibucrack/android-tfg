package com.example.asistelo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.adapter.SubjectAdapter
import com.example.asistelo.controllers.UserController
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.decorator.SimpleItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class StudentSubjectsScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_subjects_screen)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()


        val studentSubjects = retrofit.create(UserController::class.java)

        val student = intent.getSerializableExtra("student") as UserDto

        val subjectList = intent.getSerializableExtra("subjects") as List<String>


//        val student = studentSubjects.getStudent(user.id) as UserDto
//
//        student.enqueue(object : Callback<UserDto> {
//            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
//                when (response.code()) {
//                    200 -> {
//                        val user = response.body()
//                    }
//                    404 -> {
//                        Log.e("login", "Código de respuesta desconocido ${response.code()}")
//                        Toast.makeText(
//                            this@MainActivity,
//                            " ${response.body()}",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                }
//
//            }


//        val subjects = student.subjects

        val subjectsRecyclerView = findViewById<RecyclerView>(R.id.subjectsRecyclerView)

        val subjectsAdapter = SubjectAdapter(subjectList, applicationContext)

        subjectsRecyclerView.layoutManager =
            GridLayoutManager(this, 1, RecyclerView.VERTICAL, false)

        subjectsRecyclerView.addItemDecoration(SimpleItemDecoration(applicationContext, 5))

        subjectsRecyclerView.adapter = subjectsAdapter
    }
}