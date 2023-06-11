package com.example.asistelo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.AbsenceDto
import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase que adapta la lista de ausencias para poder enseñarla por pantalla,
 * eligiendo que campos se quieren mostrar en un layout personalizado.
 */
class AbsenceAdapter(val absences: List<AbsenceDto>, val context: Context) :
    RecyclerView.Adapter<AbsenceAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun load(absence: AbsenceDto) {
            val subjectNameTextView =
                itemView.findViewById<TextView>(R.id.showSubjectTextViewInAbsences)
            subjectNameTextView.text = absence.subject!!.name
            val dateTextView = itemView.findViewById<TextView>(R.id.showDateInAbsences)
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(absence.date!!)
            dateTextView.text = formattedDate
            val numHoursTextView = itemView.findViewById<TextView>(R.id.showNumHoursInAbsences)
            numHoursTextView.text = absence.numHours.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val absenceLayout = layoutInflater.inflate(R.layout.show_absence, null)
        return ItemViewHolder(absenceLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.load(absences[position])
    }

    override fun getItemCount(): Int {
        return absences.size
    }
}