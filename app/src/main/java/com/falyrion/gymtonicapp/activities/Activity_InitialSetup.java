package com.falyrion.gymtonicapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.falyrion.gymtonicapp.Activity_Main;
import com.falyrion.gymtonicapp.R;
import com.falyrion.gymtonicapp.data.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_InitialSetup extends AppCompatActivity {

    private TextInputEditText editTextName, editTextAge, editTextHeight, editTextWeight;
    private Button buttonStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if setup is already completed
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        if (prefs.getBoolean("setup_completed", false)) {
            startActivity(new Intent(this, Activity_Main.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_initial_setup);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        buttonStart = findViewById(R.id.buttonStart);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAndContinue();
            }
        });
    }

    private void saveAndContinue() {
        String name = editTextName.getText().toString().trim();
        String ageStr = editTextAge.getText().toString().trim();
        String heightStr = editTextHeight.getText().toString().trim();
        String weightStr = editTextWeight.getText().toString().trim();

        if (name.isEmpty() || ageStr.isEmpty() || heightStr.isEmpty() || weightStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            float height = Float.parseFloat(heightStr);
            double weight = Double.parseDouble(weightStr);

            // Save to SharedPreferences
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_name", name);
            editor.putInt("user_age", age);
            editor.putFloat("user_height", height);
            editor.putBoolean("setup_completed", true);
            editor.apply();

            // Save weight to database for today
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            String date = formatter.format(new Date());
            
            // addDataBody(date, weight, chest, belly, butt, waist, arm_r, arm_l, leg_r, leg_l)
            dbHelper.addDataBody(date, weight, 0, 0, 0, 0, 0, 0, 0, 0);
            dbHelper.close();

            // Go to Activity_Main (which will show Nutrition fragment by default)
            Intent intent = new Intent(Activity_InitialSetup.this, Activity_Main.class);
            startActivity(intent);
            finish();

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
