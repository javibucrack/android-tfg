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
import com.example.asistelo.controllers.dto.AbsenceDto
import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase que adapta la lista de ausencias para poder quitarla mediante
 * un checkbox "deleteAbsenceCheckBox"
 */
class AbsencesOfSubjectAdapter(
    val absences: List<AbsenceDto>,
    val context: Context,
) :
    RecyclerView.Adapter<AbsencesOfSubjectAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    public val checkedMap: MutableMap<Int, Boolean> =
        mutableMapOf() // Mapa para almacenar el estado de marcado/desmarcado

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun load(absence: AbsenceDto) {
            val showStudentNameInAbsencesOfSubject =
                itemView.findViewById<TextView>(R.id.showStudentNameInAbsencesOfSubject)
            showStudentNameInAbsencesOfSubject.text = absence.student!!.name
            val showStudentSurnameInAbsencesOfSubject =
                itemView.findViewById<TextView>(R.id.showStudentSurnameInAbsencesOfSubject)
            if (absence.student.secondSurname == null) {
                showStudentSurnameInAbsencesOfSubject.text =
                    absence.student.firstSurname
            } else {
                showStudentSurnameInAbsencesOfSubject.text =
                    absence.student.firstSurname + " " + absence.student.secondSurname
            }

            val showAbsenceDateInAbsencesOfSubject =
                itemView.findViewById<TextView>(R.id.showAbsenceDateInAbsencesOfSubject)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(absence.date!!)
            showAbsenceDateInAbsencesOfSubject.text = formattedDate

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val absenceOfSubjectLayout =
            layoutInflater.inflate(R.layout.show_absence_of_subject, null)
        return ItemViewHolder(absenceOfSubjectLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val absence = absences[position]
        holder.load(absence)

        val deleteAbsenceCheckBox =
            holder.itemView.findViewById<CheckBox>(R.id.deleteAbsenceCheckBox)
        deleteAbsenceCheckBox.isChecked = checkedMap.getOrDefault(absence.id, false)

        deleteAbsenceCheckBox.setOnCheckedChangeListener { _, isChecked ->
            checkedMap[absence.id!!] = isChecked
        }
    }

    override fun getItemCount(): Int {
        return absences.size
    }
}