package newstart.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        // Player Views
        YouTubePlayerView player01 = view.findViewById(R.id.youtube_sun_01);
        YouTubePlayerView player02 = view.findViewById(R.id.youtube_sun_02);

        getLifecycle().addObserver(player01);
        getLifecycle().addObserver(player02);

        setupPlayer(player01, "7SRE9963F9U"); // Benefits of Sunlight
        setupPlayer(player02, "6_z2iK2W-4k"); // Vitamin D video
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
