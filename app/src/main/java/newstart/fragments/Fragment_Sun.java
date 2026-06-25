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
import android.widget.TextSwitcher;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import newstart.R;
import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Fragment_Sun - Matches the visual standards with the Light Green Sunlight theme.
 */
public class Fragment_Sun extends Fragment {

    private String date;
    private int sunMinutes = 0;
    private SharedPreferences sharedPreferences;
    private TextView textViewSunMinutes;
    private TextView textViewSunGoal;

    private TextSwitcher textSwitcherHints;
    private final int[] sunHints = {
            R.string.hint_sun_1,
            R.string.hint_sun_2,
            R.string.hint_sun_3,
            R.string.hint_sun_4,
            R.string.hint_sun_5
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
        textViewSunGoal = view.findViewById(R.id.textViewSunGoal);
        MaterialButton buttonPlus = view.findViewById(R.id.buttonPlusSun);
        MaterialButton buttonMinus = view.findViewById(R.id.buttonMinusSun);

        // Load saved state
        sunMinutes = sharedPreferences.getInt("sun_mins_" + date, 0);
        updateSunText();

        buttonPlus.setOnClickListener(v -> {
            v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sunMinutes += 5;
            saveSunMinutes();
            updateSunText();
        });

        buttonMinus.setOnClickListener(v -> {
            if (sunMinutes >= 5) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                sunMinutes -= 5;
                saveSunMinutes();
                updateSunText();
            }
        });

        textSwitcherHints = view.findViewById(R.id.textSwitcherSunHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        setupPlayer(view.findViewById(R.id.youtube_sun_01), "7SRE9963F9U");
        setupPlayer(view.findViewById(R.id.youtube_sun_02), "6_z2iK2W-4k");
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(sunHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % sunHints.length;
                textSwitcherHints.setText(getString(sunHints[currentHintIdx]));
                hintHandler.postDelayed(this, 5000);
            }
        };
        hintHandler.postDelayed(hintRunnable, 5000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintRunnable != null) hintHandler.removeCallbacks(hintRunnable);
    }

    private void updateSunText() {
        textViewSunMinutes.setText(getString(R.string.sun_minutes_format, sunMinutes));
        // Use the sun_primary color for the tracker text as per your green theme
        textViewSunMinutes.setTextColor(ContextCompat.getColor(requireContext(), R.color.sun_primary));
        if (textViewSunGoal != null) {
            textViewSunGoal.setText(getString(R.string.sun_goal_format, 20));
        }
    }

    private void saveSunMinutes() {
        sharedPreferences.edit().putInt("sun_mins_" + date, sunMinutes).apply();
    }

    private void setupPlayer(YouTubePlayerView playerView, String videoId) {
        if (playerView == null) return;
        getLifecycle().addObserver(playerView);
        playerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
