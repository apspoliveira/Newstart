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
import androidx.cardview.widget.CardView;
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

    // Meal Suggestions following NEWSTART principles (Plant-based, Whole foods)
    private final String[][] mealSuggestionsEn = {
            {"Oatmeal with Blueberries and Walnuts", "Quinoa Salad with Roasted Vegetables", "Steamed Broccoli and Baked Tofu"},
            {"Whole Grain Pancakes with Fresh Fruit", "Black Bean and Corn Tacos", "Lentil Soup with Kale"},
            {"Fruit Smoothie with Flax Seeds", "Chickpea Curry with Brown Rice", "Mixed Green Salad with Seeds"},
            {"Buckwheat Porridge with Almonds", "Hummus and Veggie Wrap", "Vegetable Stir-fry with Tempeh"},
            {"Chia Pudding with Mango", "Sweet Potato and Black Bean Chili", "Roasted Cauliflower with Tahini"},
            {"Whole Wheat Toast with Avocado", "Lentil and Vegetable Stew", "Zucchini Noodles with Pesto"},
            {"Millet with Dates and Cashews", "Quinoa and Black Bean Bowl", "Baked Sweet Potato with Greens"},
            {"Tofu Scramble with Spinach", "Farro Salad with Dried Cranberries", "Roasted Roots with Garlic Dip"},
            {"Acai Bowl with Homemade Granola", "Red Lentil Pasta with Marinara", "Kale and Quinoa Salad"},
            {"Breakfast Quinoa with Berries", "Lentil Shepherd's Pie (Vegan)", "Baked Asparagus with Almonds"},
            {"Peanut Butter Banana Toast", "Buddha Bowl with Chickpeas", "Stuffed Peppers with Wild Rice"},
            {"Smoothie Bowl with Nuts", "Vegetable Barley Soup", "Mushroom Risotto (Brown Rice)"},
            {"Cornmeal Porridge", "Falafel Wrap with Hummus", "Eggplant Lasagna (No-Cheese)"},
            {"Apple Cinnamon Oats", "Split Pea Soup", "Sweet and Sour Tofu"},
            {"Breakfast Burrito (Beans/Veg)", "Tabouli with Extra Parsley", "Brussels Sprouts with Balsamic"},
            {"Granola with Almond Milk", "Minestrone Soup", "Stir-fried Bok Choy and Tempeh"},
            {"Mashed Avocado on Rye", "Black-Eyed Pea Salad", "Baked Squash with Quinoa"},
            {"Fruit and Nut Muesli", "Vegetable Paella", "Grilled Portobello Steaks"},
            {"Quinoa with Peaches", "Cabbage Soup with Potatoes", "Spiced Cauliflower Steaks"},
            {"Banana Pancakes (Oat Flour)", "Mediterranean Chickpea Salad", "Broccoli and Cashew Stir-fry"},
            {"Overnight Oats with Pumpkin", "Vegetable Korma", "Balsamic Glazed Beets"},
            {"Soy Yogurt with Mixed Berries", "White Bean and Kale Soup", "Cabbage and Carrot Slaw"},
            {"Spelt Bread with Nut Butter", "Green Lentil and Rice (Mujadara)", "Steamed Artichokes"},
            {"Tofu and Veggie Hash", "Roasted Chickpea Salad", "Baked Sweet Potato Wedges"},
            {"Pear and Walnut Porridge", "Three Bean Chili", "Garlic Sauteed Green Beans"},
            {"Smoothie with Spinach/Fruit", "Brown Rice and Veggie Sushi", "Miso Soup with Tofu"},
            {"Buckwheat with Savory Herbs", "Tomato and Lentil Stew", "Stir-fried Snap Peas"},
            {"Fruit Salad with Hemp Seeds", "Quinoa with Pomegranate", "Zucchini and Corn Sauté"},
            {"Whole Grain Toast with Hummus", "Barley and Mushroom Soup", "Steamed Mixed Vegetables"},
            {"Mixed Berry Parfait (Vegan)", "Tofu and Broccoli with Peanut Sauce", "Baked Acorn Squash"},
            {"Steel Cut Oats with Figs", "Mexican Quinoa Bowl", "Sautéed Swiss Chard with Garlic"}
    };

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
        // Breakfasts
        mealVideos.put("Oatmeal with Blueberries and Walnuts", "41Xy3SihQfs");
        mealVideos.put(MealConstants.PT_AVEIA_MIRTILOS, "41Xy3SihQfs");
        mealVideos.put("Whole Grain Pancakes with Fresh Fruit", "FcvDYecIcAs");
        mealVideos.put(MealConstants.PT_PANQUECAS_INTEGRAIS, "FcvDYecIcAs");
        mealVideos.put("Fruit Smoothie with Flax Seeds", "DLgJF2jV_mU");
        mealVideos.put(MealConstants.PT_SMOOTHIE_LINHACA, "DLgJF2jV_mU");
        mealVideos.put("Buckwheat Porridge with Almonds", "q_Q-7Mv3Uu0");
        mealVideos.put(MealConstants.PT_PAPA_SARRACENO, "q_Q-7Mv3Uu0");
        mealVideos.put("Chia Pudding with Mango", "o0iN3n-pP7w");
        mealVideos.put(MealConstants.PT_PUDIM_CHIA, "o0iN3n-pP7w");
        mealVideos.put("Whole Wheat Toast with Avocado", "L6Xo9xGfL-M");
        mealVideos.put(MealConstants.PT_TORRADA_ABACATE, "L6Xo9xGfL-M");
        mealVideos.put("Millet with Dates and Cashews", "eFf_y4C0E78");
        mealVideos.put(MealConstants.PT_PAINCO_CAJU, "eFf_y4C0E78");
        mealVideos.put("Tofu Scramble with Spinach", "zH_hI5N_G4M");
        mealVideos.put(MealConstants.PT_MEXIDO_TOFU_ESPINAFRES, "zH_hI5N_G4M");

        // Lunches
        mealVideos.put(MealConstants.PT_SALADA_QUINOA_LEGUMES, "8k2_kGf7oQc");
        mealVideos.put(MealConstants.PT_TACOS_FEIJAO_MILHO, "83uY7n0-nIk");

        // Dinners
        mealVideos.put(MealConstants.PT_BROCOLIS_TOFU_GRELHADO, "S-u-8j9f-fI");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get date from arguments
        if (getArguments() != null && getArguments().containsKey("date")) {
            date = getArguments().getString("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter.format(new Date());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nutrition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        updateMealSuggestions(view);

        // Sliding Hints Logic
        textSwitcherHints = view.findViewById(R.id.textSwitcherNutritionHints);
        textSwitcherHints.setFactory(() -> {
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.START);
            textView.setTextColor(getResources().getColor(android.R.color.white));
            textView.setTextSize(16);
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            return textView;
        });

        textSwitcherHints.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
        textSwitcherHints.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));

        startHintsSliding();

        ImageView buttonCalendar = view.findViewById(R.id.buttonNutritionCalendar);
        if (buttonCalendar != null) {
            buttonCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Activity_Calendar.class);
                    intent.putExtra("date", date);
                    intent.putExtra("fragmentID", 0);
                    startActivity(intent);
                }
            });
        }

        // Initialize players
        setupPlayerInitialization(view.findViewById(R.id.youtube_breakfast), 0);
        setupPlayerInitialization(view.findViewById(R.id.youtube_lunch), 1);
        setupPlayerInitialization(view.findViewById(R.id.youtube_dinner), 2);

        // Breakfast Card Click -> Video
        CardView cardBreakfast = view.findViewById(R.id.cardBreakfast);
        if (cardBreakfast != null) {
            cardBreakfast.setOnClickListener(v -> playBreakfastVideo(view));
        }

        // Lunch Card Click -> Video
        CardView cardLunch = view.findViewById(R.id.cardLunch);
        if (cardLunch != null) {
            cardLunch.setOnClickListener(v -> playLunchVideo(view));
        }

        // Dinner Card Click -> Video
        CardView cardDinner = view.findViewById(R.id.cardDinner);
        if (cardDinner != null) {
            cardDinner.setOnClickListener(v -> playDinnerVideo(view));
        }

        // Breakfast Recipe Button
        Button buttonViewBreakfast = view.findViewById(R.id.buttonViewBreakfastRecipe);
        if (buttonViewBreakfast != null) {
            buttonViewBreakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipe(todayBreakfast);
                }
            });
        }

        // Lunch Recipe Button
        Button buttonViewLunch = view.findViewById(R.id.buttonViewLunchRecipe);
        if (buttonViewLunch != null) {
            buttonViewLunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipe(todayLunch);
                }
            });
        }

        // Dinner Recipe Button
        Button buttonViewDinner = view.findViewById(R.id.buttonViewDinnerRecipe);
        if (buttonViewDinner != null) {
            buttonViewDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRecipe(todayDinner);
                }
            });
        }

        // Play Video Buttons
        ImageView playBreakfast = view.findViewById(R.id.buttonPlayVideoBreakfast);
        if (playBreakfast != null) {
            playBreakfast.setOnClickListener(v -> playBreakfastVideo(view));
        }

        ImageView playLunch = view.findViewById(R.id.buttonPlayVideoLunch);
        if (playLunch != null) {
            playLunch.setOnClickListener(v -> playLunchVideo(view));
        }

        ImageView playDinner = view.findViewById(R.id.buttonPlayVideoDinner);
        if (playDinner != null) {
            playDinner.setOnClickListener(v -> playDinnerVideo(view));
        }

        // Handle clicks on top suggestions summary list
        View.OnClickListener summaryClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                showRecipe(tv.getText().toString());
            }
        };

        TextView breakfastSum = view.findViewById(R.id.textBreakfastSuggestion);
        TextView lunchSum = view.findViewById(R.id.textLunchSuggestion);
        TextView dinnerSum = view.findViewById(R.id.textDinnerSuggestion);

        if (breakfastSum != null) breakfastSum.setOnClickListener(summaryClickListener);
        if (lunchSum != null) lunchSum.setOnClickListener(summaryClickListener);
        if (dinnerSum != null) dinnerSum.setOnClickListener(summaryClickListener);
    }

    private void setupPlayerInitialization(YouTubePlayerView pView, int type) {
        if (pView == null) return;
        getLifecycle().addObserver(pView);
        pView.initialize(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                if (type == 0) breakfastPlayer = youTubePlayer;
                else if (type == 1) lunchPlayer = youTubePlayer;
                else if (type == 2) dinnerPlayer = youTubePlayer;
            }
        });
    }

    private void playBreakfastVideo(View view) {
        playInternalVideo(todayBreakfast, breakfastPlayer, 
            view.findViewById(R.id.youtube_breakfast),
            view.findViewById(R.id.imageBreakfastPlaceholder),
            view.findViewById(R.id.overlayBreakfast),
            view.findViewById(R.id.layoutBreakfastText),
            view.findViewById(R.id.buttonPlayVideoBreakfast),
            view.findViewById(R.id.buttonViewBreakfastRecipe));
    }

    private void playLunchVideo(View view) {
        playInternalVideo(todayLunch, lunchPlayer,
            view.findViewById(R.id.youtube_lunch),
            view.findViewById(R.id.imageLunchPlaceholder),
            view.findViewById(R.id.overlayLunch),
            view.findViewById(R.id.layoutLunchText),
            view.findViewById(R.id.buttonPlayVideoLunch),
            view.findViewById(R.id.buttonViewLunchRecipe));
    }

    private void playDinnerVideo(View view) {
        playInternalVideo(todayDinner, dinnerPlayer,
            view.findViewById(R.id.youtube_dinner),
            view.findViewById(R.id.imageDinnerPlaceholder),
            view.findViewById(R.id.overlayDinner),
            view.findViewById(R.id.layoutDinnerText),
            view.findViewById(R.id.buttonPlayVideoDinner),
            view.findViewById(R.id.buttonViewDinnerRecipe));
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) requireContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private void playInternalVideo(String mealName, YouTubePlayer playerObj, YouTubePlayerView playerView, View placeholder, View overlay, View textLayout, View playButton, View recipeButton) {
        if (mealName == null || playerView == null) return;
        
        if (!isNetworkAvailable()) {
            Toast.makeText(getContext(), "Sem ligação à internet. Verifique o seu Wi-Fi.", Toast.LENGTH_LONG).show();
            return;
        }

        String videoId = mealVideos.get(mealName);

        if (videoId != null && !videoId.isEmpty() && !videoId.contains("f9f9j")) {
            playerView.setVisibility(View.VISIBLE);

            if (playerObj != null) {
                // Hide placeholder elements
                if (placeholder != null) placeholder.setVisibility(View.GONE);
                if (overlay != null) overlay.setVisibility(View.GONE);
                if (textLayout != null) textLayout.setVisibility(View.GONE);
                if (playButton != null) playButton.setVisibility(View.GONE);
                if (recipeButton != null) recipeButton.setVisibility(View.GONE);
                
                // Small delay to allow the layout to settle before loading the video stream
                playerView.postDelayed(() -> playerObj.loadVideo(videoId, 0f), 200);
            } else {
                Toast.makeText(getContext(), "A carregar o reprodutor, tente novamente.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Vídeo brevemente disponível para: " + mealName, Toast.LENGTH_SHORT).show();
        }
    }

    private void startHintsSliding() {
        textSwitcherHints.setText(getString(nutritionHints[currentHintIdx]));
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx++;
                if (currentHintIdx >= nutritionHints.length) currentHintIdx = 0;
                textSwitcherHints.setText(getString(nutritionHints[currentHintIdx]));
                hintHandler.postDelayed(this, 5000); // Change hint every 5 seconds
            }
        };
        hintHandler.postDelayed(hintRunnable, 5000);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (hintHandler != null && hintRunnable != null) {
            hintHandler.removeCallbacks(hintRunnable);
        }
    }

    private void showRecipe(String title) {
        if (title == null || title.isEmpty() || title.equals("Loading...")) return;

        DatabaseHelper db = new DatabaseHelper(getContext());
        String htmlContent = db.getRecipeContent(title);

        Intent intent = new Intent(getContext(), Activity_FullContent.class);
        intent.putExtra("title", title);
        if (htmlContent != null) {
            intent.putExtra("content", htmlContent);
        } else {
            intent.putExtra("content", "<h1>Receita em breve!</h1><p>Estamos a trabalhar para adicionar " + title + " à nossa base de dados.</p>");
        }
        startActivity(intent);
    }

    private void updateMealSuggestions(View view) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date dateObj = formatter.parse(date);
            if (dateObj != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
                
                // Sempre usar Português como padrão para as sugestões
                String[][] currentSuggestions = mealSuggestionsPt;
                
                int suggestionIndex = (dayOfMonth - 1) % currentSuggestions.length;

                String[] todayMeals = currentSuggestions[suggestionIndex];
                todayBreakfast = todayMeals[0];
                todayLunch = todayMeals[1];
                todayDinner = todayMeals[2];

                // Update Summary card
                TextView breakfastSum = view.findViewById(R.id.textBreakfastSuggestion);
                TextView lunchSum = view.findViewById(R.id.textLunchSuggestion);
                TextView dinnerSum = view.findViewById(R.id.textDinnerSuggestion);

                if (breakfastSum != null) breakfastSum.setText(todayBreakfast);
                if (lunchSum != null) lunchSum.setText(todayLunch);
                if (dinnerSum != null) dinnerSum.setText(todayDinner);

                // Update Detail cards
                TextView breakfastTitle = view.findViewById(R.id.textBreakfastCardTitle);
                TextView lunchTitle = view.findViewById(R.id.textLunchCardTitle);
                TextView dinnerTitle = view.findViewById(R.id.textDinnerCardTitle);

                if (breakfastTitle != null) breakfastTitle.setText(todayBreakfast);
                if (lunchTitle != null) lunchTitle.setText(todayLunch);
                if (dinnerTitle != null) dinnerTitle.setText(todayDinner);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
