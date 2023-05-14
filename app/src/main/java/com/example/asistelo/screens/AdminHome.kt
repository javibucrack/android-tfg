package com.example.asistelo.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.asistelo.R
import com.example.asistelo.controllers.dto.UserDto

class AdminHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)


        val admin = intent.getSerializableExtra("admin") as UserDto

        val adminName = findViewById<TextView>(R.id.adminNameTextView)

        adminName.text = admin.name

        val addUserButton = findViewById<Button>(R.id.addUserActivityButton)

        addUserButton.setOnClickListener {
            val addUserActivityIntent = Intent(this@AdminHome, AddUserActivity::class.java)
            addUserActivityIntent.putExtra("admin", admin)
            startActivity(addUserActivityIntent)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_select_role, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileMenu -> {
                val profileIntent =
                    Intent(this@AdminHome, ProfileScreen::class.java)
                profileIntent.putExtra("user", intent.getSerializableExtra("admin") as UserDto)
                startActivity(profileIntent)
                // Open profile activity
                return true
            }
            R.id.logOutMenu -> {
                val logOutIntent =
                    Intent(this@AdminHome, MainActivity::class.java)
                Toast.makeText(this@AdminHome, "Cerrando sesión", Toast.LENGTH_LONG).show()
                startActivity(logOutIntent)
                // Open settings activity
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}