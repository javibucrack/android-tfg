package com.example.asistelo.screens;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.asistelo.R;

public class Main extends AppCompatActivity {
    private Spinner roleSpinner;
    private final String[] roles = {"Estudiante", "Profesor", "Administrador"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        roleSpinner = findViewById(R.id.roleSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);

        TextView selectRol = findViewById(R.id.selectRol);
        selectRol.setOnClickListener(v -> roleSpinner.performClick());

        Button enterButton = findViewById(R.id.enterButton);
        enterButton.setOnClickListener(v -> {
                    String selectedRole = roleSpinner.getSelectedItem().toString();
                    if (selectedRole.equals("Estudiante")) {
                        Intent intent = new Intent(Main.this, StudentHome.class);
                        startActivity(intent);
                    } else if (selectedRole.equals("Profesor")) {
                        Intent intent = new Intent(Main.this, TeacherHome.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
