package com.example.asistelo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R

/**
 * Clase que adapta la lista de asignaturas (pasadas como String) para poder enseñarla por pantalla,
 * eligiendo que campos se quieren mostrar en un layout personalizado.
 */
class SubjectAdapter(val subjects: List<String>, val context: Context) :
    RecyclerView.Adapter<SubjectAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun load(subject: String) {
            val subjectNameTextView = itemView.findViewById<TextView>(R.id.showSubjectTextView)
            subjectNameTextView.text = subject
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val studentLayout = layoutInflater.inflate(R.layout.show_subjects, null)
        return ItemViewHolder(studentLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.load(subjects[position])
    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}