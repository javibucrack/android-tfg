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
import com.example.asistelo.controllers.dto.SubjectDto
import java.text.SimpleDateFormat
import java.util.*

/**
 * Clase que adapta la lista de asignaturas para poder enseñarla por pantalla,
 * eligiendo que campos se quieren mostrar en un layout personalizado.
 */
class SubjectForAddUserAdapter(
    val subjects: List<SubjectDto>,
    val context: Context,
) :
    RecyclerView.Adapter<SubjectForAddUserAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    public val checkedMap: MutableMap<Int, Boolean> =
        mutableMapOf() // Mapa para almacenar el estado de marcado/desmarcado

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun load(subject: SubjectDto) {
            val showSubjectNameForAddUser =
                itemView.findViewById<TextView>(R.id.showSubjectNameForAddUser)
            showSubjectNameForAddUser.text = subject.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val showSubjectForAddUserLayout =
            layoutInflater.inflate(R.layout.show_subject_for_add_user, null)
        return ItemViewHolder(showSubjectForAddUserLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val subject = subjects[position]
        holder.load(subject)

        val addSubjectToSubjectListCheckBox =
            holder.itemView.findViewById<CheckBox>(R.id.addSubjectToSubjectListCheckBox)
        addSubjectToSubjectListCheckBox.isChecked = checkedMap.getOrDefault(subject.id, false)

        addSubjectToSubjectListCheckBox.setOnCheckedChangeListener { _, isChecked ->
            checkedMap[subject.id!!] = isChecked
        }
    }

    override fun getItemCount(): Int {
        return subjects.size
    }
}