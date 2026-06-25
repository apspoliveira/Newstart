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

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Fragment_Trust - Optimized with Material Design 3 and UI/UX Pro Max standards.
 */
public class Fragment_Trust extends Fragment {

    private String date;
    private SharedPreferences sharedPreferences;
    private TextView textViewVerse, textViewReference;

    private TextSwitcher textSwitcherHints;
    private final int[] trustHints = {
            R.string.hint_trust_1,
            R.string.hint_trust_2,
            R.string.hint_trust_3,
            R.string.hint_trust_4,
            R.string.hint_trust_5
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trust, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        date = formatter.format(new Date());

        textViewVerse = view.findViewById(R.id.textViewVerse);
        textViewReference = view.findViewById(R.id.textViewReference);
        
        setupTrustGoal(view.findViewById(R.id.checkBoxPrayer), "trust_prayer_");
        setupTrustGoal(view.findViewById(R.id.checkBoxMeditation), "trust_medit_");
        setupTrustGoal(view.findViewById(R.id.checkBoxGratitude), "trust_gratitude_");
        setupTrustGoal(view.findViewById(R.id.checkBoxRest), "trust_rest_");

        fetchBibleVerse();

        textSwitcherHints = view.findViewById(R.id.textSwitcherTrustHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            // Updated to themed color for better readability on container background
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_trust_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        setupCuedPlayer(view.findViewById(R.id.youtube_prayer), "3qDneS3hK3o"); 
        setupCuedPlayer(view.findViewById(R.id.youtube_meditation), "MH5lnMCGVFk"); 
        setupCuedPlayer(view.findViewById(R.id.youtube_gratitude), "7vYm2y8p1fA"); 
        setupCuedPlayer(view.findViewById(R.id.youtube_rest), "a6Ue690_q4U"); 
    }

    private void setupTrustGoal(CheckBox checkBox, final String keyPrefix) {
        if (checkBox == null) return;
        final String finalKey = keyPrefix + date;
        checkBox.setChecked(sharedPreferences.getBoolean(finalKey, false));
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            buttonView.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
            sharedPreferences.edit().putBoolean(finalKey, isChecked).apply();
        });
    }

    private void fetchBibleVerse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Bypass SSL certification requirement for the API call
                TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                    }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);

                URL url = new URL("https://www.abibliadigital.com.br/api/verses/nvi/random");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) response.append(line);
                reader.close();

                JSONObject jsonObject = new JSONObject(response.toString());
                String verseText = jsonObject.getString("text");
                String bookName = jsonObject.getJSONObject("book").getString("name");
                String ref = bookName + " " + jsonObject.getInt("chapter") + ":" + jsonObject.getInt("number");

                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        textViewVerse.setText(getString(R.string.bible_verse_format, verseText));
                        textViewReference.setText(getString(R.string.bible_reference_format, ref));
                    });
                }
            } catch (Exception e) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        textViewVerse.setText(getString(R.string.default_bible_verse));
                        textViewReference.setText(getString(R.string.default_bible_reference));
                    });
                }
            }
        });
    }

    private void startHintsSliding() {
        if (textSwitcherHints == null) return;
        textSwitcherHints.setText(getString(trustHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % trustHints.length;
                textSwitcherHints.setText(getString(trustHints[currentHintIdx]));
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
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
}
