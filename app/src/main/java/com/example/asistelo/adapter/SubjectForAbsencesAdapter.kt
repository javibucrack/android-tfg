package com.example.asistelo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.SubjectDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.screens.AddAbsenceScreen

class SubjectForAbsencesAdapter(
    val subjects: List<SubjectDto>,
    val student: UserDto,
    val teacher: UserDto,
    val context: Context
) :
    RecyclerView.Adapter<SubjectForAbsencesAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun load(subject: SubjectDto) {
            val subjectNameTextView = itemView.findViewById<TextView>(R.id.showSubjectTextView)
            subjectNameTextView.text = subject.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val subjectLayout = layoutInflater.inflate(R.layout.show_subjects, null)
        return ItemViewHolder(subjectLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val subject = subjects[position]
        holder.load(subject)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddAbsenceScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("subject", subject)
            intent.putExtra("student", student)
            intent.putExtra("teacher", teacher)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}