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

/**
 * Fragment_Rest - Optimized with Material Design 3 and UI/UX Pro Max standards.
 * Implements haptic feedback for interactions and high-contrast accessible typography.
 */
public class Fragment_Rest extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

    private TextSwitcher textSwitcherHints;
    private final int[] restHints = {
            R.string.hint_rest_1,
            R.string.hint_rest_2,
            R.string.hint_rest_3,
            R.string.hint_rest_4,
            R.string.hint_rest_5
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rest, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        // Setup Checkboxes with Pro Max Interaction Patterns (Rule 27: Haptics)
        setupRestGoal(view.findViewById(R.id.checkBoxRestHours), "rest_hours_");
        setupRestGoal(view.findViewById(R.id.checkBoxNoScreens), "rest_screens_");
        setupRestGoal(view.findViewById(R.id.checkBoxRegularSchedule), "rest_sched_");
        setupRestGoal(view.findViewById(R.id.checkBoxEnvironment), "rest_env_");

        // Sliding Hints Logic - Applying UI/UX Pro Max Accessibility & Contrast rules (Rule 36)
        textSwitcherHints = view.findViewById(R.id.textSwitcherRestHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            // Updated to themed color for better readability on container background
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_rest_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        // Rule 7/8: standard 300ms transition for micro-interactions
        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        // Player Views - Performance-conscious loading (Rule 96: Bandwidth efficient)
        setupCuedPlayer(view.findViewById(R.id.youtube_rest_hours), "MvM93SOn96Q"); 
        setupCuedPlayer(view.findViewById(R.id.youtube_no_screens), "E08Oq8_iXas");
        setupCuedPlayer(view.findViewById(R.id.youtube_regular_schedule), "G0zJ_q_S-90");
        setupCuedPlayer(view.findViewById(R.id.youtube_environment), "9m5D-7F9v-g");
    }

    private void setupRestGoal(CheckBox checkBox, String keyPrefix) {
        if (checkBox == null) return;
        String finalKey = keyPrefix + date;
        checkBox.setChecked(sharedPreferences.getBoolean(finalKey, false));
        
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Rule 27: Provide tactile confirmation for health achievements
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sharedPreferences.edit().putBoolean(finalKey, isChecked).apply();
        });
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(restHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % restHints.length;
                textSwitcherHints.setText(getString(restHints[currentHintIdx]));
                // Rule 73: Increased interval (6s) for readable measure on mobile
                hintHandler.postDelayed(this, 6000);
            }
        };
        hintHandler.postDelayed(hintRunnable, 6000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintHandler != null && hintRunnable != null) {
            hintHandler.removeCallbacks(hintRunnable);
        }
    }

    private void setupCuedPlayer(YouTubePlayerView playerView, String videoId) {
        if (playerView == null) return;
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
