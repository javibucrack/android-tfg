package com.example.asistelo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class TeacherHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_home)

        val user = intent.getSerializableExtra("user") as UserDto

        val teacherNameTextView = findViewById<TextView>(R.id.teacherNameTextView)
        teacherNameTextView.text = user.name
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_role, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileMenu -> {
                val profileIntent =
                    Intent(this@TeacherHome, ProfileScreen::class.java)
                profileIntent.putExtra("user", intent.getSerializableExtra("user") as UserDto)
                startActivity(profileIntent)
                // Open profile activity
                return true
            }
            R.id.settingsMenu -> {
                // Open settings activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}