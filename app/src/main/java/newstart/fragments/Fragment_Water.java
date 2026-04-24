package newstart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import newstart.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Fragment_Water extends Fragment {

    private String date;
    private int waterGlasses = 0;
    private SharedPreferences sharedPreferences;
    private TextView textViewWaterGlasses;

    private TextSwitcher textSwitcherHints;
    private final String[] waterHints = {
            "Drink at least 8 glasses of water a day for optimal hydration.",
            "Starting your day with two glasses of water jump-starts your metabolism.",
            "Water helps transport nutrients and oxygen to your cells.",
            "Hydration is key for healthy skin and proper digestion.",
            "Thirst is often mistaken for hunger. Drink water first!",
            "Drinking water can help improve concentration and energy levels."
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

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

        textViewWaterGlasses = view.findViewById(R.id.textViewWaterGlasses);
        Button buttonPlus = view.findViewById(R.id.buttonPlusWater);
        Button buttonMinus = view.findViewById(R.id.buttonMinusWater);
        CheckBox checkBoxHydrotherapy = view.findViewById(R.id.checkBoxHydrotherapy);

        // Load saved states
        waterGlasses = sharedPreferences.getInt("water_glasses_" + date, 0);
        updateWaterText();
        checkBoxHydrotherapy.setChecked(sharedPreferences.getBoolean("water_hydro_" + date, false));

        buttonPlus.setOnClickListener(v -> {
            waterGlasses++;
            saveWaterGlasses();
            updateWaterText();
        });

        buttonMinus.setOnClickListener(v -> {
            if (waterGlasses > 0) {
                waterGlasses--;
                saveWaterGlasses();
                updateWaterText();
            }
        });

        // Sliding Hints Logic
        textSwitcherHints = view.findViewById(R.id.textSwitcherWaterHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            textView.setTextColor(getResources().getColor(android.R.color.white));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        // Player Views
        YouTubePlayerView playerDrinkWater = view.findViewById(R.id.youtube_drink_water);
        YouTubePlayerView playerHydrotherapy = view.findViewById(R.id.youtube_hydrotherapy);

        // Add to lifecycle
        getLifecycle().addObserver(playerDrinkWater);
        getLifecycle().addObserver(playerHydrotherapy);

        // Randomly select one video ID for each player
        Random random = new Random();
        String videoIdDrinkWater = videoIdsDrinkWater[random.nextInt(videoIdsDrinkWater.length)];
        String videoIdHydrotherapy = videoIdsHydrotherapy[random.nextInt(videoIdsHydrotherapy.length)];

        // Initialize Players with the randomly selected videos
        setupPlayer(playerDrinkWater, videoIdDrinkWater);
        setupPlayer(playerHydrotherapy, videoIdHydrotherapy);

        // Listeners
        checkBoxHydrotherapy.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("water_hydro_" + date, isChecked).apply());
    }

    private void startHintsSliding() {
        textSwitcherHints.setText(waterHints[currentHintIdx]);
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx++;
                if (currentHintIdx >= waterHints.length) currentHintIdx = 0;
                textSwitcherHints.setText(waterHints[currentHintIdx]);
                hintHandler.postDelayed(this, 5000); // Change hint every 5 seconds
            }
        };
        hintHandler.postDelayed(hintRunnable, 5000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintHandler != null && hintRunnable != null) {
            hintHandler.removeCallbacks(hintRunnable);
        }
    }

    private void updateWaterText() {
        textViewWaterGlasses.setText(waterGlasses + (waterGlasses == 1 ? " glass" : " glasses"));
    }

    private void saveWaterGlasses() {
        sharedPreferences.edit().putInt("water_glasses_" + date, waterGlasses).apply();
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
