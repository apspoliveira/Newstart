package com.falyrion.gymtonicapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.falyrion.gymtonicapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Fragment_Air extends Fragment {

    private int airMinutes = 0;
    private final int airGoal = 30;
    private String date;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_air, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        ProgressBar progressBar = view.findViewById(R.id.progressBarAir);
        TextView progressText = view.findViewById(R.id.textViewAirProgress);
        Button addButton = view.findViewById(R.id.buttonAddAir);

        // NEWSTART Air Principle Checkboxes
        CheckBox checkBoxFreshAir = view.findViewById(R.id.checkBoxFreshAir);
        CheckBox checkBoxVentilation = view.findViewById(R.id.checkBoxVentilation);
        CheckBox checkBoxDeepBreathing = view.findViewById(R.id.checkBoxDeepBreathing);
        CheckBox checkBoxCleanAir = view.findViewById(R.id.checkBoxCleanAir);

        // Load saved states
        airMinutes = sharedPreferences.getInt("air_minutes_" + date, 0);
        checkBoxFreshAir.setChecked(sharedPreferences.getBoolean("air_fresh_" + date, false));
        checkBoxVentilation.setChecked(sharedPreferences.getBoolean("air_vent_" + date, false));
        checkBoxDeepBreathing.setChecked(sharedPreferences.getBoolean("air_deep_" + date, false));
        checkBoxCleanAir.setChecked(sharedPreferences.getBoolean("air_clean_" + date, false));

        addButton.setOnClickListener(v -> {
            if (airMinutes < airGoal) {
                airMinutes += 5;
                sharedPreferences.edit().putInt("air_minutes_" + date, airMinutes).apply();
                updateUI(progressBar, progressText);
            }
        });

        checkBoxFreshAir.setOnCheckedChangeListener((buttonView, isChecked) -> 
            sharedPreferences.edit().putBoolean("air_fresh_" + date, isChecked).apply());
        checkBoxVentilation.setOnCheckedChangeListener((buttonView, isChecked) -> 
            sharedPreferences.edit().putBoolean("air_vent_" + date, isChecked).apply());
        checkBoxDeepBreathing.setOnCheckedChangeListener((buttonView, isChecked) -> 
            sharedPreferences.edit().putBoolean("air_deep_" + date, isChecked).apply());
        checkBoxCleanAir.setOnCheckedChangeListener((buttonView, isChecked) -> 
            sharedPreferences.edit().putBoolean("air_clean_" + date, isChecked).apply());

        updateUI(progressBar, progressText);
    }

    private void updateUI(ProgressBar progressBar, TextView progressText) {
        int progress = (int) (((double) airMinutes / airGoal) * 100);
        progressBar.setProgress(Math.min(progress, 100));
        progressText.setText(airMinutes + " / " + airGoal + " mins");
    }
}
