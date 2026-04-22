package newstart.fragments;

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

import newstart.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Fragment_Workout extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

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

        // Checkboxes
        CheckBox checkBoxStrength = view.findViewById(R.id.checkBoxStrength);
        CheckBox checkBoxCardio = view.findViewById(R.id.checkBoxCardio);
        CheckBox checkBoxFlexibility = view.findViewById(R.id.checkBoxFlexibility);

        // Player Views
        YouTubePlayerView playerStrength = view.findViewById(R.id.youtube_strength);
        YouTubePlayerView playerCardio = view.findViewById(R.id.youtube_cardio);
        YouTubePlayerView playerFlexibility = view.findViewById(R.id.youtube_flexibility);

        // Add to lifecycle
        getLifecycle().addObserver(playerStrength);
        getLifecycle().addObserver(playerCardio);
        getLifecycle().addObserver(playerFlexibility);

        // Load saved states
        checkBoxStrength.setChecked(sharedPreferences.getBoolean("workout_strength_" + date, false));
        checkBoxCardio.setChecked(sharedPreferences.getBoolean("workout_cardio_" + date, false));
        checkBoxFlexibility.setChecked(sharedPreferences.getBoolean("workout_flexibility_" + date, false));

        // Randomly select one video ID for each player
        Random random = new Random();
        String videoIdStrength = videoIdsStrength[random.nextInt(videoIdsStrength.length)];
        String videoIdCardio = videoIdsCardio[random.nextInt(videoIdsCardio.length)];
        String videoIdFlexibility = videoIdsFlexibility[random.nextInt(videoIdsFlexibility.length)];

        // Initialize Players with the randomly selected videos
        setupPlayer(playerStrength, videoIdStrength);
        setupPlayer(playerCardio, videoIdCardio);
        setupPlayer(playerFlexibility, videoIdFlexibility);

        // Listeners
        checkBoxStrength.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("workout_strength_" + date, isChecked).apply());
        checkBoxCardio.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("workout_cardio_" + date, isChecked).apply());
        checkBoxFlexibility.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("workout_flexibility_" + date, isChecked).apply());
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
