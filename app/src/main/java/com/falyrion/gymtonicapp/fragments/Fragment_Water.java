package com.falyrion.gymtonicapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.falyrion.gymtonicapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Fragment_Water extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

    // Video IDs for each category
    private final String[] videoIdsDrinkWater = {"AVSNiAndIGU", "rlFRSJYCax8"};
    private final String[] videoIdsHydrotherapy = {"HfBWUzP2tYU", "F2hIAOfw5h8"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_water, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        // Checkboxes
        CheckBox checkBoxDrinkWater = view.findViewById(R.id.checkBoxDrinkWater);
        CheckBox checkBoxHydrotherapy = view.findViewById(R.id.checkBoxHydrotherapy);

        // Player Views
        YouTubePlayerView playerDrinkWater = view.findViewById(R.id.youtube_drink_water);
        YouTubePlayerView playerHydrotherapy = view.findViewById(R.id.youtube_hydrotherapy);

        // Add to lifecycle
        getLifecycle().addObserver(playerDrinkWater);
        getLifecycle().addObserver(playerHydrotherapy);

        // Load saved states
        checkBoxDrinkWater.setChecked(sharedPreferences.getBoolean("water_drink_" + date, false));
        checkBoxHydrotherapy.setChecked(sharedPreferences.getBoolean("water_hydro_" + date, false));

        // Randomly select one video ID for each player
        Random random = new Random();
        String videoIdDrinkWater = videoIdsDrinkWater[random.nextInt(videoIdsDrinkWater.length)];
        String videoIdHydrotherapy = videoIdsHydrotherapy[random.nextInt(videoIdsHydrotherapy.length)];

        // Initialize Players with the randomly selected videos
        setupPlayer(playerDrinkWater, videoIdDrinkWater);
        setupPlayer(playerHydrotherapy, videoIdHydrotherapy);

        // Listeners
        checkBoxDrinkWater.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("water_drink_" + date, isChecked).apply());
        checkBoxHydrotherapy.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("water_hydro_" + date, isChecked).apply());
    }

    private void setupPlayer(YouTubePlayerView playerView, String videoId) {
        playerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
