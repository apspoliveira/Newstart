package newstart.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import newstart.R;
import newstart.activities.Activity_Calendar;
import newstart.activities.Activity_FullContent;
import newstart.data.DatabaseHelper;
import newstart.data.MealConstants;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Fragment_Nutrition extends Fragment {

    private String date;
    private String todayBreakfast = "";
    private String todayLunch = "";
    private String todayDinner = "";

    private YouTubePlayer breakfastPlayer, lunchPlayer, dinnerPlayer;

    private TextSwitcher textSwitcherHints;
    private final int[] nutritionHints = {
            R.string.hint_nutr_1,
            R.string.hint_nutr_2,
            R.string.hint_nutr_3,
            R.string.hint_nutr_4,
            R.string.hint_nutr_5,
            R.string.hint_nutr_6
    };
    private int currentHintIdx = 0;
    private final Handler hintHandler = new Handler();
    private Runnable hintRunnable;

    private final String[][] mealSuggestionsPt = {
            {MealConstants.PT_AVEIA_MIRTILOS, MealConstants.PT_SALADA_QUINOA_LEGUMES, MealConstants.PT_BROCOLIS_TOFU_GRELHADO},
            {MealConstants.PT_PANQUECAS_INTEGRAIS, MealConstants.PT_TACOS_FEIJAO_MILHO, MealConstants.PT_SOPA_LENTILHA_COUVE},
            {MealConstants.PT_SMOOTHIE_LINHACA, MealConstants.PT_CARIL_GRAO_BICO, MealConstants.PT_SALADA_VERDE_SEMENTES},
            {MealConstants.PT_PAPA_SARRACENO, MealConstants.PT_WRAP_HUMUS, MealConstants.PT_SALTEADO_TEMPEH},
            {MealConstants.PT_PUDIM_CHIA, MealConstants.PT_CHILI_BATATA_DOCE, MealConstants.PT_COUVE_FLOR_TAHINI},
            {MealConstants.PT_TORRADA_ABACATE, MealConstants.PT_GISADO_LENTILHAS, MealConstants.PT_ZOODLES_PESTO},
            {MealConstants.PT_PAINCO_CAJU, MealConstants.PT_BOWL_QUINOA_FEIJAO, MealConstants.PT_BATATA_DOCE_FOLHAS_VERDES},
            {MealConstants.PT_MEXIDO_TOFU_ESPINAFRES, MealConstants.PT_SALADA_FARRO, MealConstants.PT_RAIZES_ASSADAS_ALHO},
            {MealConstants.PT_TACA_ACAI, MealConstants.PT_MASSA_LENTILHA_MARINARA, MealConstants.PT_SALADA_COUVE_QUINOA},
            {MealConstants.PT_QUINOA_PEQUENO_ALMOCO, MealConstants.PT_EMPADAO_LENTILHA, MealConstants.PT_ESPARGOS_ASSADOS},
            {MealConstants.PT_TORRADA_AMENDOIM_BANANA, MealConstants.PT_BOWL_BUDDHA_GRAO, MealConstants.PT_PIMENTOS_RECHEADOS},
            {MealConstants.PT_SMOOTHIE_BOWL_FRUTOS_SECOS, MealConstants.PT_SOPA_CEVADA_VEGETAIS, MealConstants.PT_RISOTO_COGUMELOS},
            {MealConstants.PT_PAPAS_MILHO, MealConstants.PT_WRAP_FALAFEL, MealConstants.PT_LASANHA_BERINGELA},
            {MealConstants.PT_AVEIA_MACA_CANELA, MealConstants.PT_SOPA_ERVILHA, MealConstants.PT_TOFU_AGRIDOCE},
            {MealConstants.PT_BURRITO_VEG, MealConstants.PT_TABULE_SALSA, MealConstants.PT_COUVE_BRUXELAS_BALSAMICO},
            {MealConstants.PT_GRANOLA_LEITE_AMENDOAS, MealConstants.PT_SOPA_MINESTRONE, MealConstants.PT_BOK_CHOY_TEMPEH},
            {MealConstants.PT_ABACATE_CENTEIO, MealConstants.PT_SALADA_FEIJAO_FRADE, MealConstants.PT_ABOBORA_ASSADA_QUINOA},
            {MealConstants.PT_MUESLI_FRUTAS, MealConstants.PT_PAELLA_VEGETAIS, MealConstants.PT_BIFES_PORTOBELLO},
            {MealConstants.PT_QUINOA_PESSEGOS, MealConstants.PT_SOPA_REPOLHO_BATATAS, MealConstants.PT_BIFES_COUVE_FLOR},
            {MealConstants.PT_PANQUECAS_BANANA, MealConstants.PT_SALADA_GRAO_MEDITERRANEA, MealConstants.PT_SALTEADO_BROCOLIS_CAJU},
            {MealConstants.PT_AVEIA_ABOBORA, MealConstants.PT_KORMA_VEGETAIS, MealConstants.PT_BETERRABA_GLACIADA},
            {MealConstants.PT_IOGURTE_SOJA_FRUTAS, MealConstants.PT_SOPA_FEIJAO_BRANCO, MealConstants.PT_SALADA_REPOLHO_CENOURA},
            {MealConstants.PT_PAO_ESPELTA_MANTEIGA, MealConstants.PT_LENTILHA_ARROZ_MUJADARA, MealConstants.PT_ALCACHOFRAS_VAPOR},
            {MealConstants.PT_MEXIDO_TOFU_VEGETAIS, MealConstants.PT_SALADA_GRAO_ASSADO, MealConstants.PT_PALITOS_BATATA_DOCE},
            {MealConstants.PT_PAPAS_PERA_NOZ, MealConstants.PT_CHILI_TRES_FEIJOES, MealConstants.PT_VAGEM_ALHO},
            {MealConstants.PT_SMOOTHIE_ESPINAFRES, MealConstants.PT_SUSHI_VEGGIE, MealConstants.PT_SOPA_MISO_TOFU},
            {MealConstants.PT_SARRACENO_ERVAS, MealConstants.PT_GISADO_TOMATE_LENTILHAS, MealConstants.PT_ERVILHAS_QUEBRAR_SALTEADAS},
            {MealConstants.PT_SALADA_FRUTA_CANHAMO, MealConstants.PT_QUINOA_ROMA, MealConstants.PT_SALTEADO_ABOBORINHA_MILHO},
            {MealConstants.PT_TORRADA_HUMUS, MealConstants.PT_SOPA_CEVADA_COGUMELOS, MealConstants.PT_VEGETAIS_VAPOR},
            {MealConstants.PT_PARFAIT_VEGAN, MealConstants.PT_TOFU_BROCOLIS_AMENDOIM, MealConstants.PT_ABOBORA_MENINA_ASSADA},
            {MealConstants.PT_AVEIA_FIGOS, MealConstants.PT_BOWL_QUINOA_MEXICANO, MealConstants.PT_ACELGA_ALHO}
    };

    private static final Map<String, String> mealVideos = new HashMap<>();
    static {
        mealVideos.put(MealConstants.PT_AVEIA_MIRTILOS, "41Xy3SihQfs");
        mealVideos.put(MealConstants.PT_PANQUECAS_INTEGRAIS, "FcvDYecIcAs");
        mealVideos.put(MealConstants.PT_SMOOTHIE_LINHACA, "DLgJF2jV_mU");
        mealVideos.put(MealConstants.PT_PAPA_SARRACENO, "q_Q-7Mv3Uu0");
        mealVideos.put(MealConstants.PT_PUDIM_CHIA, "o0iN3n-pP7w");
        mealVideos.put(MealConstants.PT_TORRADA_ABACATE, "L6Xo9xGfL-M");
        mealVideos.put(MealConstants.PT_PAINCO_CAJU, "eFf_y4C0E78");
        mealVideos.put(MealConstants.PT_MEXIDO_TOFU_ESPINAFRES, "zH_hI5N_G4M");
        mealVideos.put(MealConstants.PT_SALADA_QUINOA_LEGUMES, "8k2_kGf7oQc");
        mealVideos.put(MealConstants.PT_TACOS_FEIJAO_MILHO, "83uY7n0-nIk");
        mealVideos.put(MealConstants.PT_BROCOLIS_TOFU_GRELHADO, "S-u-8j9f-fI");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null && getArguments().containsKey("date")) {
            date = getArguments().getString("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter.format(new Date());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Header and Navigation
        view.findViewById(R.id.buttonNutritionCalendar).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), Activity_Calendar.class);
            intent.putExtra("date", date);
            intent.putExtra("fragmentID", 0);
            startActivity(intent);
        });

        // Sliding Hints - Use themed colors
        textSwitcherHints = view.findViewById(R.id.textSwitcherNutritionHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            textView.setTextColor(ContextCompat.getColor(requireContext(), R.color.on_nutrition_container));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });
        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));
        startHintsSliding();

        // Meal Suggestions Data Logic
        updateMealSuggestions(view);

        // Setup Meal Cards
        setupMealCard(view.findViewById(R.id.includeBreakfast), todayBreakfast, getString(R.string.nutrition_breakfast_title), getString(R.string.nutrition_breakfast_desc), 0);
        setupMealCard(view.findViewById(R.id.includeLunch), todayLunch, getString(R.string.nutrition_lunch_title), getString(R.string.nutrition_lunch_desc), 1);
        setupMealCard(view.findViewById(R.id.includeDinner), todayDinner, getString(R.string.nutrition_dinner_title), getString(R.string.nutrition_dinner_desc), 2);

        // Summary Clicks
        View.OnClickListener summaryClickListener = v -> showRecipe(((TextView) v).getText().toString());
        view.findViewById(R.id.textBreakfastSuggestion).setOnClickListener(summaryClickListener);
        view.findViewById(R.id.textLunchSuggestion).setOnClickListener(summaryClickListener);
        view.findViewById(R.id.textDinnerSuggestion).setOnClickListener(summaryClickListener);
    }

    private void setupMealCard(View cardContainer, String mealName, String typeLabel, String description, int type) {
        TextView textType = cardContainer.findViewById(R.id.textMealType);
        TextView textTitle = cardContainer.findViewById(R.id.textMealTitle);
        TextView textDesc = cardContainer.findViewById(R.id.textMealDescription);
        Button buttonRecipe = cardContainer.findViewById(R.id.buttonViewRecipe);
        ImageView buttonPlay = cardContainer.findViewById(R.id.buttonPlayVideo);
        YouTubePlayerView playerView = cardContainer.findViewById(R.id.youtube_player);

        textType.setText(typeLabel);
        textTitle.setText(mealName);
        textDesc.setText(description);

        buttonRecipe.setOnClickListener(v -> showRecipe(mealName));
        
        getLifecycle().addObserver(playerView);
        playerView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (type == 0) breakfastPlayer = youTubePlayer;
                else if (type == 1) lunchPlayer = youTubePlayer;
                else if (type == 2) dinnerPlayer = youTubePlayer;
            }
        });

        buttonPlay.setOnClickListener(v -> {
            YouTubePlayer player = (type == 0) ? breakfastPlayer : (type == 1) ? lunchPlayer : dinnerPlayer;
            playInternalVideo(mealName, player, playerView, cardContainer);
        });
    }

    private void playInternalVideo(String mealName, YouTubePlayer playerObj, YouTubePlayerView playerView, View container) {
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "Sem ligação à internet.", Toast.LENGTH_SHORT).show();
            return;
        }

        String videoId = mealVideos.get(mealName);
        if (videoId != null && playerObj != null) {
            playerView.setVisibility(View.VISIBLE);
            container.findViewById(R.id.imagePlaceholder).setVisibility(View.GONE);
            container.findViewById(R.id.overlay).setVisibility(View.GONE);
            container.findViewById(R.id.layoutText).setVisibility(View.GONE);
            container.findViewById(R.id.buttonPlayVideo).setVisibility(View.GONE);
            playerObj.loadVideo(videoId, 0f);
        } else {
            Toast.makeText(getContext(), "Vídeo brevemente disponível.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMealSuggestions(View view) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date dateObj = formatter.parse(date);
            if (dateObj != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                int idx = (dayOfMonth - 1) % mealSuggestionsPt.length;

                todayBreakfast = mealSuggestionsPt[idx][0];
                todayLunch = mealSuggestionsPt[idx][1];
                todayDinner = mealSuggestionsPt[idx][2];

                ((TextView) view.findViewById(R.id.textBreakfastSuggestion)).setText(todayBreakfast);
                ((TextView) view.findViewById(R.id.textLunchSuggestion)).setText(todayLunch);
                ((TextView) view.findViewById(R.id.textDinnerSuggestion)).setText(todayDinner);
            }
        } catch (Exception ignored) {}
    }

    private void startHintsSliding() {
        textSwitcherHints.setText(getString(nutritionHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx = (currentHintIdx + 1) % nutritionHints.length;
                textSwitcherHints.setText(getString(nutritionHints[currentHintIdx]));
                hintHandler.postDelayed(this, 5000);
            }
        };
        hintHandler.postDelayed(hintRunnable, 5000);
    }

    private void showRecipe(String title) {
        if (title == null || title.isEmpty() || title.equals("Loading...")) return;
        DatabaseHelper db = new DatabaseHelper(getContext());
        String htmlContent = db.getRecipeContent(title);
        Intent intent = new Intent(getContext(), Activity_FullContent.class);
        intent.putExtra("title", title);
        intent.putExtra("content", htmlContent != null ? htmlContent : "<h1>Receita em breve!</h1>");
        intent.putExtra("fragmentID", 0); // Nutrition
        startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintRunnable != null) hintHandler.removeCallbacks(hintRunnable);
    }
}
