package com.example.asistelo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

/**
 * Clase que adapta la lista de usuarios (estudiantes) para poder enseñarla por pantalla,
 * eligiendo que campos se quieren mostrar en un layout personalizado.
 */
class StudentListForAbsencesAdapter(
    val students: List<UserDto>,
    val context: Context
) :
    RecyclerView.Adapter<StudentListForAbsencesAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    public val checkedMap: MutableMap<Int, Boolean> =
        mutableMapOf() // Mapa para almacenar el estado de marcado/desmarcado

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun load(student: UserDto) {
            val showStudentNameInStudentListTextView =
                itemView.findViewById<TextView>(R.id.showStudentNameInStudentListTextView)
            showStudentNameInStudentListTextView.text = student.name
            val showStudentSurnameInStudentListTextView =
                itemView.findViewById<TextView>(R.id.showStudentSurnameInStudentListTextView)
            if (student.secondSurname == null) {
                showStudentSurnameInStudentListTextView.text =
                    student.firstSurname
            } else {
                showStudentSurnameInStudentListTextView.text =
                    student.firstSurname + " " + student.secondSurname
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val studentInStudentListLayout =
            layoutInflater.inflate(R.layout.show_student_in_student_list, null)
        return ItemViewHolder(studentInStudentListLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val student = students[position]
        holder.load(student)

        val addAbsenceCheckBox = holder.itemView.findViewById<CheckBox>(R.id.addAbsenceCheckBox)
        addAbsenceCheckBox.isChecked = checkedMap.getOrDefault(student.id, false)

        addAbsenceCheckBox.setOnCheckedChangeListener { _, isChecked ->
            checkedMap[student.id!!] = isChecked
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }
}