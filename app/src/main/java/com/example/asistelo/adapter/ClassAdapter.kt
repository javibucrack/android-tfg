package com.example.asistelo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.ClassDto
import com.example.asistelo.controllers.dto.UserDto
import com.example.asistelo.screens.StudentsOfClassScreen

class ClassAdapter(val classes: List<ClassDto>, val teacher:UserDto,val context: Context) :
    RecyclerView.Adapter<ClassAdapter.ItemViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun load(classe: ClassDto) {
            val className = itemView.findViewById<TextView>(R.id.showClassName)
            className.text = classe.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val classLayout = layoutInflater.inflate(R.layout.show_class, null)
        return ItemViewHolder(classLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val classe = classes[position]
        holder.load(classe)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, StudentsOfClassScreen::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("class", classe)
            intent.putExtra("teacher",teacher)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return classes.size
    }
}