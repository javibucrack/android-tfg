package com.example.asistelo.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.asistelo.controllers.dto.SubjectDto

class SubjectForAbsenceListAdapter(
    context: Context,
    resource: Int,
    private val subjectList: List<SubjectDto>
) :
    ArrayAdapter<SubjectDto>(context, resource, subjectList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val subjectNameTextView = view.findViewById<TextView>(android.R.id.text1)
        subjectNameTextView.text = subjectList[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        val subjectNameTextView = view.findViewById<TextView>(android.R.id.text1)
        subjectNameTextView.text = subjectList[position].name
        return view
    }
}