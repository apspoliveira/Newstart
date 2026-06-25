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
 * Fragment_Temperance - Refined with Material Design 3 and UI/UX Pro Max standards.
 */
public class Fragment_Temperance extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

    private TextSwitcher textSwitcherHints;
    private final int[] tempHints = {
            R.string.hint_temp_1,
            R.string.hint_temp_2,
            R.string.hint_temp_3,
            R.string.hint_temp_4,
            R.string.hint_temp_5
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_temperance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        setupTemperanceGoal(view.findViewById(R.id.checkBoxAvoidSubstances), "temp_subst_");
        setupTemperanceGoal(view.findViewById(R.id.checkBoxCaffeine), "temp_caff_");
        setupTemperanceGoal(view.findViewById(R.id.checkBoxBalance), "temp_bal_");
        setupTemperanceGoal(view.findViewById(R.id.checkBoxSelfControl), "temp_self_");

        textSwitcherHints = view.findViewById(R.id.textSwitcherTempHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            // Updated to themed color for better readability on container background
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_temperance_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        setupCuedPlayer(view.findViewById(R.id.youtube_substances), "6Y_A9Rnd9Is"); 
        setupCuedPlayer(view.findViewById(R.id.youtube_caffeine), "fG6i9yE_160");
        setupCuedPlayer(view.findViewById(R.id.youtube_balance), "iO68V_vH6Gk");
        setupCuedPlayer(view.findViewById(R.id.youtube_self_control), "y7S2S4N88lE");
    }

    private void setupTemperanceGoal(CheckBox checkBox, final String keyPrefix) {
        if (checkBox == null) return;
        final String finalKey = keyPrefix + date;
        
        checkBox.setChecked(sharedPreferences.getBoolean(finalKey, false));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sharedPreferences.edit().putBoolean(finalKey, isChecked).apply();
        });
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(tempHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % tempHints.length;
                textSwitcherHints.setText(getString(tempHints[currentHintIdx]));
                hintHandler.postDelayed(this, 6000);
            }
        };
        hintHandler.postDelayed(hintRunnable, 6000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintRunnable != null) hintHandler.removeCallbacks(hintRunnable);
    }

    private void setupCuedPlayer(YouTubePlayerView playerView, String videoId) {
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
