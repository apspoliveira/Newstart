package newstart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import newstart.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Fragment_Workout - Enhanced with Material 3 and UI/UX Pro Max interaction patterns.
 */
public class Fragment_Workout extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

    private TextSwitcher textSwitcherHints;
    private final int[] workoutHints = {
            R.string.workout_hint_1,
            R.string.workout_hint_2,
            R.string.workout_hint_3,
            R.string.workout_hint_4,
            R.string.workout_hint_5,
            R.string.workout_hint_6
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    // Video IDs for each category
    private final String[] videoIdsStrength = {"3vS6-O7Yy8Y", "UItWltVZZmE"};
    private final String[] videoIdsCardio = {"ml6cT4AZdqI", "gC_L9qAHVJ8"};
    private final String[] videoIdsFlexibility = {"Y6Z7H8p2Yt0", "L_xrDAtykMI"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        // Setup Checkboxes with Pro Max Interaction Patterns (Rule 27: Haptics)
        setupWorkoutGoal(view.findViewById(R.id.checkBoxStrength), "workout_strength_");
        setupWorkoutGoal(view.findViewById(R.id.checkBoxCardio), "workout_cardio_");
        setupWorkoutGoal(view.findViewById(R.id.checkBoxFlexibility), "workout_flexibility_");

        // Sliding Hints - Applying Accessibility & Contrast Rules (Rule 36)
        textSwitcherHints = view.findViewById(R.id.textSwitcherWorkoutHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            // Updated to themed color for better readability on container background
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_workout_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        // Player Views - Performance Optimization (Rule 96: Bandwidth efficient)
        Random random = new Random();
        setupPlayer(view.findViewById(R.id.youtube_strength), videoIdsStrength[random.nextInt(videoIdsStrength.length)]);
        setupPlayer(view.findViewById(R.id.youtube_cardio), videoIdsCardio[random.nextInt(videoIdsCardio.length)]);
        setupPlayer(view.findViewById(R.id.youtube_flexibility), videoIdsFlexibility[random.nextInt(videoIdsFlexibility.length)]);
    }

    private void setupWorkoutGoal(CheckBox checkBox, String keyPrefix) {
        if (checkBox == null) return;
        String finalKey = keyPrefix + date;
        checkBox.setChecked(sharedPreferences.getBoolean(finalKey, false));
        
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sharedPreferences.edit().putBoolean(finalKey, isChecked).apply();
        });
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(workoutHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % workoutHints.length;
                textSwitcherHints.setText(getString(workoutHints[currentHintIdx]));
                hintHandler.postDelayed(this, 5500); 
            }
        };
        hintHandler.postDelayed(hintRunnable, 5500);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintRunnable != null) hintHandler.removeCallbacks(hintRunnable);
    }

    private void setupPlayer(YouTubePlayerView playerView, String videoId) {
        getLifecycle().addObserver(playerView);
        playerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                // Rule 96: Bandwidth efficiency - cue instead of load
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
