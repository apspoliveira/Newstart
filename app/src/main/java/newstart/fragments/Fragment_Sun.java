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

public class Fragment_Sun extends Fragment {

    private String date;
    private int sunMinutes = 0;
    private SharedPreferences sharedPreferences;
    private TextView textViewSunMinutes;

    private TextSwitcher textSwitcherHints;
    private final String[] sunHints = {
            "Sunlight is the best source of Vitamin D.",
            "15-20 minutes of sun exposure daily is ideal for most people.",
            "Sunlight helps regulate your circadian rhythm for better sleep.",
            "Exposure to sun can improve your mood by boosting serotonin levels.",
            "Morning sun is often considered the safest and most beneficial.",
            "Sunlight helps strengthen your immune system."
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sun, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        textViewSunMinutes = view.findViewById(R.id.textViewSunMinutes);
        Button buttonPlus = view.findViewById(R.id.buttonPlusSun);
        Button buttonMinus = view.findViewById(R.id.buttonMinusSun);

        // Load saved state
        sunMinutes = sharedPreferences.getInt("sun_mins_" + date, 0);
        updateSunText();

        buttonPlus.setOnClickListener(v -> {
            sunMinutes += 5;
            saveSunMinutes();
            updateSunText();
        });

        buttonMinus.setOnClickListener(v -> {
            if (sunMinutes >= 5) {
                sunMinutes -= 5;
                saveSunMinutes();
                updateSunText();
            }
        });

        // Sliding Hints Logic
        textSwitcherHints = view.findViewById(R.id.textSwitcherSunHints);
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
        YouTubePlayerView player01 = view.findViewById(R.id.youtube_sun_01);
        YouTubePlayerView player02 = view.findViewById(R.id.youtube_sun_02);

        getLifecycle().addObserver(player01);
        getLifecycle().addObserver(player02);

        setupPlayer(player01, "7SRE9963F9U"); // Benefits of Sunlight
        setupPlayer(player02, "6_z2iK2W-4k"); // Vitamin D video
    }

    private void startHintsSliding() {
        textSwitcherHints.setText(sunHints[currentHintIdx]);
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx++;
                if (currentHintIdx >= sunHints.length) currentHintIdx = 0;
                textSwitcherHints.setText(sunHints[currentHintIdx]);
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

    private void updateSunText() {
        textViewSunMinutes.setText(sunMinutes + " minutes");
    }

    private void saveSunMinutes() {
        sharedPreferences.edit().putInt("sun_mins_" + date, sunMinutes).apply();
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
