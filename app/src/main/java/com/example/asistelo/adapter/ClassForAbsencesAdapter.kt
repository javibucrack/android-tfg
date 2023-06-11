package com.example.asistelo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.asistelo.controllers.dto.ClassDto

/**
 * Clase que adapta la lista de clases para poder enseñarla por pantalla,
 * eligiendo que campos se quieren mostrar en un layout personalizado.
 */
class ClassForAbsencesAdapter(
    context: Context,
    resource: Int,
    private val classList: List<ClassDto>
) :
    ArrayAdapter<ClassDto>(context, resource, classList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val classNameTextView = view.findViewById<TextView>(android.R.id.text1)
        classNameTextView.text = classList[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val classNameTextView = view.findViewById<TextView>(android.R.id.text1)
        classNameTextView.text = classList[position].name
        return view
    }
}