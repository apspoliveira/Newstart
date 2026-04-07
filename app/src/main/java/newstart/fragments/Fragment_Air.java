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

public class Fragment_Air extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;

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

        // Checkboxes
        CheckBox checkBoxFreshAir = view.findViewById(R.id.checkBoxFreshAir);
        CheckBox checkBoxVentilation = view.findViewById(R.id.checkBoxVentilation);
        CheckBox checkBoxDeepBreathing = view.findViewById(R.id.checkBoxDeepBreathing);
        CheckBox checkBoxCleanAir = view.findViewById(R.id.checkBoxCleanAir);

        // Player Views
        YouTubePlayerView playerFreshAir = view.findViewById(R.id.youtube_fresh_air);
        YouTubePlayerView playerVentilation = view.findViewById(R.id.youtube_ventilation);
        YouTubePlayerView playerDeepBreathing = view.findViewById(R.id.youtube_deep_breathing);
        YouTubePlayerView playerCleanAir = view.findViewById(R.id.youtube_clean_air);

        // Add to lifecycle
        getLifecycle().addObserver(playerFreshAir);
        getLifecycle().addObserver(playerVentilation);
        getLifecycle().addObserver(playerDeepBreathing);
        getLifecycle().addObserver(playerCleanAir);

        // Load saved states
        checkBoxFreshAir.setChecked(sharedPreferences.getBoolean("air_fresh_" + date, false));
        checkBoxVentilation.setChecked(sharedPreferences.getBoolean("air_vent_" + date, false));
        checkBoxDeepBreathing.setChecked(sharedPreferences.getBoolean("air_deep_" + date, false));
        checkBoxCleanAir.setChecked(sharedPreferences.getBoolean("air_clean_" + date, false));

        // Randomly select one video ID for each player
        Random random = new Random();
        String videoIdFreshAir = videoIdsFreshAir[random.nextInt(videoIdsFreshAir.length)];
        String videoIdVentilation = videoIdsVentilation[random.nextInt(videoIdsVentilation.length)];
        String videoIdDeepBreathing = videoIdsDeepBreathing[random.nextInt(videoIdsDeepBreathing.length)];
        String videoIdCleanAir = videoIdsCleanAir[random.nextInt(videoIdsCleanAir.length)];

        // Initialize Players with the randomly selected videos
        setupPlayer(playerFreshAir, videoIdFreshAir);
        setupPlayer(playerVentilation, videoIdVentilation);
        setupPlayer(playerDeepBreathing, videoIdDeepBreathing);
        setupPlayer(playerCleanAir, videoIdCleanAir);

        // Listeners
        checkBoxFreshAir.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("air_fresh_" + date, isChecked).apply());
        checkBoxVentilation.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("air_vent_" + date, isChecked).apply());
        checkBoxDeepBreathing.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("air_deep_" + date, isChecked).apply());
        checkBoxCleanAir.setOnCheckedChangeListener((buttonView, isChecked) ->
                sharedPreferences.edit().putBoolean("air_clean_" + date, isChecked).apply());
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
