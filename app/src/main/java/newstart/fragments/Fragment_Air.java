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
 * Fragment_Air - Optimized with Material 3 and UI/UX Pro Max standards.
 * Implements haptic feedback for interactions and high-contrast accessible typography.
 */
public class Fragment_Air extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

    private TextSwitcher textSwitcherHints;
    private final int[] airHints = {
            R.string.hint_air_1,
            R.string.hint_air_2,
            R.string.hint_air_3,
            R.string.hint_air_4,
            R.string.hint_air_5
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    // Video IDs for each category
    private final String[] videoIdsFreshAir = {"OM_X52rdeds", "oxO2qotv3wM"};
    private final String[] videoIdsVentilation = {"F2hIAOfw5h8", "owwfYlpibU0"};
    private final String[] videoIdsDeepBreathing = {"QVeEhcKIyd8", "MH5lnMCGVF"};
    private final String[] videoIdsCleanAir = {"rlFRSJYCax8", "MsZp5thi3sY"};

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

        // Setup Checkboxes with Pro Max Interaction Patterns (Rule 27: Haptics)
        setupAirGoal(view.findViewById(R.id.checkBoxFreshAir), "air_fresh_");
        setupAirGoal(view.findViewById(R.id.checkBoxVentilation), "air_vent_");
        setupAirGoal(view.findViewById(R.id.checkBoxDeepBreathing), "air_deep_");
        setupAirGoal(view.findViewById(R.id.checkBoxCleanAir), "air_clean_");

        // Sliding Hints - Rule 36 (High Contrast)
        textSwitcherHints = view.findViewById(R.id.textSwitcherAirHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            // Updated to themed color for better readability on container background
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_air_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        // Player Views - Performance Optimization (Rule 96: Bandwidth efficient)
        Random random = new Random();
        setupPlayer(view.findViewById(R.id.youtube_fresh_air), videoIdsFreshAir[random.nextInt(videoIdsFreshAir.length)]);
        setupPlayer(view.findViewById(R.id.youtube_ventilation), videoIdsVentilation[random.nextInt(videoIdsVentilation.length)]);
        setupPlayer(view.findViewById(R.id.youtube_deep_breathing), videoIdsDeepBreathing[random.nextInt(videoIdsDeepBreathing.length)]);
        setupPlayer(view.findViewById(R.id.youtube_clean_air), videoIdsCleanAir[random.nextInt(videoIdsCleanAir.length)]);
    }

    private void setupAirGoal(CheckBox checkBox, String keyPrefix) {
        if (checkBox == null) return;
        String finalKey = keyPrefix + date;
        checkBox.setChecked(sharedPreferences.getBoolean(finalKey, false));
        
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Tactile feedback on achievement
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sharedPreferences.edit().putBoolean(finalKey, isChecked).apply();
        });
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(airHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % airHints.length;
                textSwitcherHints.setText(getString(airHints[currentHintIdx]));
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
                // Efficiency: cue instead of auto-load
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
