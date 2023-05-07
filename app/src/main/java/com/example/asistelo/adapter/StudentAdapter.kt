package com.example.asistelo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.screens.StudentSubjectsScreen
import com.example.asistelo.screens.SubjectsOfStudentForAbsencesScreen

class StudentAdapter(val students: List<UserDto>, val teacher: UserDto, val context: Context) :
    RecyclerView.Adapter<StudentAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun load(student: UserDto) {
            val studentName = itemView.findViewById<TextView>(R.id.showStudentName)
            if (student.second_surname == null) {
                studentName.text = student.name + " " + student.first_surname
            } else {
                studentName.text =
                    student.name + " " + student.first_surname + " " + student.second_surname
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val classLayout = layoutInflater.inflate(R.layout.show_student, null)
        return ItemViewHolder(classLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val student = students[position]
        holder.load(student)
        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, SubjectsOfStudentForAbsencesScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("student", student)
            intent.putExtra("teacher", teacher)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}