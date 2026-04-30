package newstart.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
    private final String[] nutritionHints = {
            "Eat a variety of colorful fruits and vegetables every day.",
            "Choose whole grains over refined grains for better fiber intake.",
            "Plant-based proteins like lentils and beans are great for heart health.",
            "Limit processed foods and added sugars in your diet.",
            "Chew your food thoroughly to aid digestion and nutrient absorption.",
            "A healthy breakfast provides energy and stabilizes blood sugar."
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
            {"Aveia com Mirtilos e Nozes", "Salada de Quinoa com Legumes Assados", "Brócolis a Vapor e Tofu Grelhado"},
            {"Panquecas Integrais com Frutas Frescas", "Tacos de Feijão Preto e Milho", "Sopa de Lentilha com Couve"},
            {"Smoothie de Frutas com Sementes de Linhaça", "Caril de Grão-de-Bico com Arroz Integral", "Salada Verde Mista com Sementes"},
            {"Papa de Trigo Sarraceno com Amêndoas", "Wrap de Húmus e Vegetais", "Salteado de Vegetais com Tempeh"},
            {"Pudim de Chia com Manga", "Chili de Batata-Doce e Feijão Preto", "Couve-Flor Assada com Tahini"},
            {"Torrada Integral com Abacate", "Gisado de Lentilhas e Vegetais", "Zoodles (Abobrinha) com Pesto"},
            {"Painço com Tâmaras e Caju", "Bowl de Quinoa e Feijão Preto", "Batata-Doce Assada com Folhas Verdes"},
            {"Mexido de Tofu com Espinafres", "Salada de Farro com Arandos Secos", "Raízes Assadas com Molho de Alho"},
            {"Taça de Açaí com Granola Caseira", "Massa de Lentilha Vermelha com Marinara", "Salada de Couve e Quinoa"},
            {"Quinoa de Pequeno-Almoço com Bagas", "Empadão de Lentilha (Vegan)", "Espargos Assados com Amêndoas"},
            {"Torrada de Manteiga de Amendoim e Banana", "Bowl Buddha com Grão-de-Bico", "Pimentos Recheados com Arroz Selvagem"},
            {"Smoothie Bowl com Frutos Secos", "Sopa de Cevada e Vegetais", "Risoto de Cogumelos (Arroz Integral)"},
            {"Papas de Milho", "Wrap de Falafel com Húmus", "Lasanha de Beringela (Sem Queijo)"},
            {"Aveia com Maçã e Canela", "Sopa de Ervilha", "Tofu Agridoce"},
            {"Burrito de Pequeno-Almoço (Feijão/Veg)", "Tabule com Salsa Extra", "Couve de Bruxelas com Balsâmico"},
            {"Granola com Leite de Amêndoas", "Sopa Minestrone", "Bok Choy Salteado com Tempeh"},
            {"Abacate Esmagado em Pão de Centeio", "Salada de Feijão Frade", "Abóbora Assada com Quinoa"},
            {"Muesli de Frutas e Frutos Secos", "Paella de Vegetais", "Bifes de Cogumelo Portobello"},
            {"Quinoa com Pêssegos", "Sopa de Repolho com Batatas", "Bifes de Couve-Flor Especiados"},
            {"Panquecas de Banana (Farinha de Aveia)", "Salada de Grão Mediterrânea", "Salteado de Brócolis e Caju"},
            {"Aveia Adormecida com Abóbora", "Korma de Vegetais", "Beterraba Glaciada com Balsâmico"},
            {"Iogurte de Soja com Frutas Vermelhas", "Sopa de Feijão Branco e Couve", "Salada de Repolho e Cenoura"},
            {"Pão de Espelta com Manteiga de Frutos Secos", "Lentilha Verde e Arroz (Mujadara)", "Alcachofras ao Vapor"},
            {"Mexido de Tofu e Vegetais", "Salada de Grão-de-Bico Assado", "Batata-Doce em Palitos Assada"},
            {"Papas de Pera e Noz", "Chili de Três Feijões", "Vagem Salteada com Alho"},
            {"Smoothie de Espinafres e Fruta", "Sushi Veggie de Arroz Integral", "Sopa Miso com Tofu"},
            {"Trigo Sarraceno com Ervas Aromáticas", "Gisado de Tomate e Lentilhas", "Ervilhas de Quebrar Salteadas"},
            {"Salada de Fruta com Sementes de Cânhamo", "Quinoa com Romã", "Salteado de Abobrinha e Milho"},
            {"Torrada Integral com Húmus", "Sopa de Cevada e Cogumelos", "Mistura de Vegetais ao Vapor"},
            {"Parfait de Frutas Vermelhas (Vegan)", "Tofu e Brócolis com Molho de Amendoim", "Abóbora Menina Assada"},
            {"Aveia em Grão com Figos", "Bowl de Quinoa Mexicano", "Acelga Salteada com Alho"}
    };

    private static final Map<String, String> mealVideos = new HashMap<>();
    static {
        // Breakfasts
        mealVideos.put("Oatmeal with Blueberries and Walnuts", "UiKWttnKz2c");
        mealVideos.put("Aveia com Mirtilos e Nozes", "UiKWttnKz2c");
        mealVideos.put("Whole Grain Pancakes with Fresh Fruit", "FcvDYecIcAs");
        mealVideos.put("Panquecas Integrais com Frutas Frescas", "FcvDYecIcAs");
        mealVideos.put("Fruit Smoothie with Flax Seeds", "DLgJF2jV_mU");
        mealVideos.put("Smoothie de Frutas com Sementes de Linhaça", "DLgJF2jV_mU");
        mealVideos.put("Buckwheat Porridge with Almonds", "q_Q-7Mv3Uu0");
        mealVideos.put("Papa de Trigo Sarraceno com Amêndoas", "q_Q-7Mv3Uu0");
        mealVideos.put("Chia Pudding with Mango", "o0iN3n-pP7w");
        mealVideos.put("Pudim de Chia com Manga", "o0iN3n-pP7w");
        mealVideos.put("Whole Wheat Toast with Avocado", "L6Xo9xGfL-M");
        mealVideos.put("Torrada Integral com Abacate", "L6Xo9xGfL-M");
        mealVideos.put("Millet with Dates and Cashews", "eFf_y4C0E78");
        mealVideos.put("Painço com Tâmaras e Caju", "eFf_y4C0E78");
        mealVideos.put("Tofu Scramble with Spinach", "zH_hI5N_G4M");
        mealVideos.put("Mexido de Tofu com Espinafres", "zH_hI5N_G4M");
        mealVideos.put("Acai Bowl with Homemade Granola", "7w0uX2qD-fE");
        mealVideos.put("Taça de Açaí com Granola Caseira", "7w0uX2qD-fE");
        mealVideos.put("Breakfast Quinoa with Berries", "L-9A3uX9m_o");
        mealVideos.put("Quinoa de Pequeno-Almoço com Bagas", "L-9A3uX9m_o");
        mealVideos.put("Peanut Butter Banana Toast", "uS9f9M8w9fI");
        mealVideos.put("Torrada de Manteiga de Amendoim e Banana", "uS9f9M8w9fI");
        mealVideos.put("Smoothie Bowl with Nuts", "f9f9j-9f9M8");
        mealVideos.put("Cornmeal Porridge", "g9f9j-9f9M8");
        mealVideos.put("Papas de Milho", "g9f9j-9f9M8");
        mealVideos.put("Apple Cinnamon Oats", "h9f9j-9f9M8");
        mealVideos.put("Aveia com Maçã e Canela", "h9f9j-9f9M8");
        mealVideos.put("Breakfast Burrito (Beans/Veg)", "i9f9j-9f9M8");
        mealVideos.put("Burrito de Pequeno-Almoço (Feijão/Veg)", "i9f9j-9f9M8");
        mealVideos.put("Granola with Almond Milk", "j9f9j-9f9M8");
        mealVideos.put("Mashed Avocado on Rye", "k9f9j-9f9M8");
        mealVideos.put("Abacate Esmagado em Pão de Centeio", "k9f9j-9f9M8");
        mealVideos.put("Fruit and Nut Muesli", "l9f9j-9f9M8");
        mealVideos.put("Quinoa with Peaches", "m9f9j-9f9M8");
        mealVideos.put("Quinoa com Pêssegos", "m9f9j-9f9M8");
        mealVideos.put("Banana Pancakes (Oat Flour)", "n9f9j-9f9M8");
        mealVideos.put("Panquecas de Banana (Farinha de Aveia)", "n9f9j-9f9M8");
        mealVideos.put("Overnight Oats with Pumpkin", "o9f9j-9f9M8");
        mealVideos.put("Aveia Adormecida com Abóbora", "o9f9j-9f9M8");
        mealVideos.put("Soy Yogurt with Mixed Berries", "p9f9j-9f9M8");
        mealVideos.put("Iogurte de Soja com Frutas Vermelhas", "p9f9j-9f9M8");
        mealVideos.put("Spelt Bread with Nut Butter", "q9f9j-9f9M8");
        mealVideos.put("Pão de Espelta com Manteiga de Frutos Secos", "q9f9j-9f9M8");
        mealVideos.put("Tofu and Veggie Hash", "r9f9j-9f9M8");
        mealVideos.put("Mexido de Tofu e Vegetais", "r9f9j-9f9M8");
        mealVideos.put("Pear and Walnut Porridge", "s9f9j-9f9M8");
        mealVideos.put("Papas de Pera e Noz", "s9f9j-9f9M8");
        mealVideos.put("Smoothie with Spinach/Fruit", "t9f9j-9f9M8");
        mealVideos.put("Smoothie de Espinafres e Fruta", "t9f9j-9f9M8");
        mealVideos.put("Buckwheat with Savory Herbs", "u9f9j-9f9M8");
        mealVideos.put("Trigo Sarraceno com Ervas Aromáticas", "u9f9j-9f9M8");
        mealVideos.put("Fruit Salad with Hemp Seeds", "7W49A_uX7U0");
        mealVideos.put("Salada de Fruta com Sementes de Cânhamo", "7W49A_uX7U0");
        mealVideos.put("Whole Grain Toast with Hummus", "w9f9j-9f9M8");
        mealVideos.put("Torrada Integral com Húmus", "w9f9j-9f9M8");
        mealVideos.put("Mixed Berry Parfait (Vegan)", "x9f9j-9f9M8");
        mealVideos.put("Parfait de Frutas Vermelhas (Vegan)", "x9f9j-9f9M8");
        mealVideos.put("Steel Cut Oats with Figs", "y9f9j-9f9M8");
        mealVideos.put("Aveia em Grão com Figos", "y9f9j-9f9M8");

        // Lunches
        mealVideos.put("Quinoa Salad with Roasted Vegetables", "8k2_kGf7oQc");
        mealVideos.put("Salada de Quinoa com Legumes Assados", "8k2_kGf7oQc");
        mealVideos.put("Black Bean and Corn Tacos", "83uY7n0-nIk");
        mealVideos.put("Tacos de Feijão Preto e Milho", "83uY7n0-nIk");
        mealVideos.put("Chickpea Curry with Brown Rice", "f7VvC-7I-Uo");
        mealVideos.put("Caril de Grão-de-Bico com Arroz Integral", "f7VvC-7I-Uo");
        mealVideos.put("Hummus and Veggie Wrap", "Ym9v9vU4k9k");
        mealVideos.put("Wrap de Húmus e Vegetais", "Ym9v9vU4k9k");
        mealVideos.put("Sweet Potato and Black Bean Chili", "p5p58Gg4f7Q");
        mealVideos.put("Chili de Batata-Doce e Feijão Preto", "p5p58Gg4f7Q");
        mealVideos.put("Lentil and Vegetable Stew", "uR3W4n-89-w");
        mealVideos.put("Gisado de Lentilhas e Vegetais", "uR3W4n-89-w");
        mealVideos.put("Quinoa and Black Bean Bowl", "T7Y-V3w-8j8");
        mealVideos.put("Bowl de Quinoa e Feijão Preto", "T7Y-V3w-8j8");
        mealVideos.put("Farro Salad with Dried Cranberries", "P1g8W6E8m_o");
        mealVideos.put("Salada de Farro com Arandos Secos", "P1g8W6E8m_o");
        mealVideos.put("Red Lentil Pasta with Marinara", "jVzT-v9-f9c");
        mealVideos.put("Massa de Lentilha Vermelha com Marinara", "jVzT-v9-f9c");
        mealVideos.put("Lentil Shepherd's Pie (Vegan)", "rR3-w-9f9M8");
        mealVideos.put("Empadão de Lentilha (Vegan)", "rR3-w-9f9M8");
        mealVideos.put("Buddha Bowl with Chickpeas", "sR3-w-9f9M8");
        mealVideos.put("Bowl Buddha com Grão-de-Bico", "sR3-w-9f9M8");
        mealVideos.put("Vegetable Barley Soup", "tR3-w-9f9M8");
        mealVideos.put("Sopa de Cevada e Vegetais", "tR3-w-9f9M8");
        mealVideos.put("Falafel Wrap with Hummus", "uR3-w-9f9M8");
        mealVideos.put("Wrap de Falafel com Húmus", "uR3-w-9f9M8");
        mealVideos.put("Split Pea Soup", "vR3-w-9f9M8");
        mealVideos.put("Sopa de Ervilha", "vR3-w-9f9M8");
        mealVideos.put("Tabouli with Extra Parsley", "wR3-w-9f9M8");
        mealVideos.put("Tabule com Salsa Extra", "wR3-w-9f9M8");
        mealVideos.put("Minestrone Soup", "xR3-w-9f9M8");
        mealVideos.put("Sopa Minestrone", "xR3-w-9f9M8");
        mealVideos.put("Black-Eyed Pea Salad", "yR3-w-9f9M8");
        mealVideos.put("Salada de Feijão Frade", "yR3-w-9f9M8");
        mealVideos.put("Vegetable Paella", "zR3-w-9f9M8");
        mealVideos.put("Paella de Vegetais", "zR3-w-9f9M8");
        mealVideos.put("Cabbage Soup with Potatoes", "1R3-w-9f9M8");
        mealVideos.put("Sopa de Repolho com Batatas", "1R3-w-9f9M8");
        mealVideos.put("Mediterranean Chickpea Salad", "2R3-w-9f9M8");
        mealVideos.put("Salada de Grão Mediterrânea", "2R3-w-9f9M8");
        mealVideos.put("Vegetable Korma", "3R3-w-9f9M8");
        mealVideos.put("Korma de Vegetais", "3R3-w-9f9M8");
        mealVideos.put("White Bean and Kale Soup", "4R3-w-9f9M8");
        mealVideos.put("Sopa de Feijão Branco e Couve", "4R3-w-9f9M8");
        mealVideos.put("Green Lentil and Rice (Mujadara)", "5R3-w-9f9M8");
        mealVideos.put("Lentilha Verde e Arroz (Mujadara)", "5R3-w-9f9M8");
        mealVideos.put("Roasted Chickpea Salad", "6R3-w-9f9M8");
        mealVideos.put("Salada de Grão-de-Bico Assado", "6R3-w-9f9M8");
        mealVideos.put("Three Bean Chili", "7R3-w-9f9M8");
        mealVideos.put("Chili de Três Feijões", "7R3-w-9f9M8");
        mealVideos.put("Brown Rice and Veggie Sushi", "8R3-w-9f9M8");
        mealVideos.put("Sushi Veggie de Arroz Integral", "8R3-w-9f9M8");
        mealVideos.put("Tomato and Lentil Stew", "9R3-w-9f9M8");
        mealVideos.put("Gisado de Tomate e Lentilhas", "9R3-w-9f9M8");
        mealVideos.put("Quinoa with Pomegranate", "0R3-w-9f9M8");
        mealVideos.put("Quinoa com Romã", "0R3-w-9f9M8");
        mealVideos.put("Barley and Mushroom Soup", "aR3-w-9f9M8");
        mealVideos.put("Sopa de Cevada e Cogumelos", "aR3-w-9f9M8");
        mealVideos.put("Tofu and Broccoli with Peanut Sauce", "bR3-w-9f9M8");
        mealVideos.put("Tofu e Brócolis com Molho de Amendoim", "bR3-w-9f9M8");
        mealVideos.put("Mexican Quinoa Bowl", "cR3-w-9f9M8");
        mealVideos.put("Bowl de Quinoa Mexicano", "cR3-w-9f9M8");

        // Dinners
        mealVideos.put("Steamed Broccoli and Baked Tofu", "S-u-8j9f-fI");
        mealVideos.put("Brócolis a Vapor e Tofu Grelhado", "S-u-8j9f-fI");
        mealVideos.put("Lentil Soup with Kale", "u5-f9f9j-98");
        mealVideos.put("Sopa de Lentilha com Couve", "u5-f9f9j-98");
        mealVideos.put("Mixed Green Salad with Seeds", "j8-9f9-9f9I");
        mealVideos.put("Salada Verde Mista com Sementes", "j8-9f9-9f9I");
        mealVideos.put("Vegetable Stir-fry with Tempeh", "m8-f9f-f9jI");
        mealVideos.put("Salteado de Vegetais com Tempeh", "m8-f9f-f9jI");
        mealVideos.put("Roasted Cauliflower with Tahini", "p8-f9j-f9-8");
        mealVideos.put("Couve-Flor Assada com Tahini", "p8-f9j-f9-8");
        mealVideos.put("Zucchini Noodles with Pesto", "r8-f9j-f9-9");
        mealVideos.put("Zoodles (Abobrinha) com Pesto", "r8-f9j-f9-9");
        mealVideos.put("Baked Sweet Potato with Greens", "s8-f9j-f9-0");
        mealVideos.put("Batata-Doce Assada com Folhas Verdes", "s8-f9j-f9-0");
        mealVideos.put("Roasted Roots with Garlic Dip", "t8-f9j-f9-1");
        mealVideos.put("Raízes Assadas com Molho de Alho", "t8-f9j-f9-1");
        mealVideos.put("Kale and Quinoa Salad", "u8-f9j-f9-2");
        mealVideos.put("Salada de Couve e Quinoa", "u8-f9j-f9-2");
        mealVideos.put("Baked Asparagus with Almonds", "v8-f9j-f9-3");
        mealVideos.put("Espargos Assados com Amêndoas", "v8-f9j-f9-3");
        mealVideos.put("Stuffed Peppers with Wild Rice", "w8-f9j-f9-4");
        mealVideos.put("Pimentos Recheados com Arroz Selvagem", "w8-f9j-f9-4");
        mealVideos.put("Mushroom Risotto (Brown Rice)", "x8-f9j-f9-5");
        mealVideos.put("Risoto de Cogumelos (Arroz Integral)", "x8-f9j-f9-5");
        mealVideos.put("Eggplant Lasagna (No-Cheese)", "y8-f9j-f9-6");
        mealVideos.put("Lasanha de Beringela (Sem Queijo)", "y8-f9j-f9-6");
        mealVideos.put("Sweet and Sour Tofu", "z8-f9j-f9-7");
        mealVideos.put("Tofu Agridoce", "z8-f9j-f9-7");
        mealVideos.put("Brussels Sprouts with Balsamic", "A8-f9j-f9-8");
        mealVideos.put("Couve de Bruxelas com Balsâmico", "A8-f9j-f9-8");
        mealVideos.put("Stir-fried Bok Choy and Tempeh", "B8-f9j-f9-9");
        mealVideos.put("Bok Choy Salteado com Tempeh", "B8-f9j-f9-9");
        mealVideos.put("Baked Squash with Quinoa", "C8-f9j-f9-0");
        mealVideos.put("Abóbora Assada com Quinoa", "C8-f9j-f9-0");
        mealVideos.put("Grilled Portobello Steaks", "D8-f9j-f9-1");
        mealVideos.put("Bifes de Cogumelo Portobello", "D8-f9j-f9-1");
        mealVideos.put("Spiced Cauliflower Steaks", "E8-f9j-f9-2");
        mealVideos.put("Bifes de Couve-Flor Especiados", "E8-f9j-f9-2");
        mealVideos.put("Broccoli and Cashew Stir-fry", "F8-f9j-f9-3");
        mealVideos.put("Salteado de Brócolis e Caju", "F8-f9j-f9-3");
        mealVideos.put("Balsamic Glazed Beets", "G8-f9j-f9-4");
        mealVideos.put("Beterraba Glaciada com Balsâmico", "G8-f9j-f9-4");
        mealVideos.put("Salada de Repolho e Cenoura", "H8-f9j-f9-5");
        mealVideos.put("Steamed Artichokes", "I8-f9j-f9-6");
        mealVideos.put("Alcachofras ao Vapor", "I8-f9j-f9-6");
        mealVideos.put("Baked Sweet Potato Wedges", "J8-f9j-f9-7");
        mealVideos.put("Batata-Doce em Palitos Assada", "J8-f9j-f9-7");
        mealVideos.put("Garlic Sauteed Green Beans", "K8-f9j-f9-8");
        mealVideos.put("Vagem Salteada com Alho", "K8-f9j-f9-8");
        mealVideos.put("Sopa Miso with Tofu", "L8-f9j-f9-9");
        mealVideos.put("Sopa Miso com Tofu", "L8-f9j-f9-9");
        mealVideos.put("Stir-fried Snap Peas", "M8-f9j-f9-0");
        mealVideos.put("Ervilhas de Quebrar Salteadas", "M8-f9j-f9-0");
        mealVideos.put("Zucchini and Corn Sauté", "N8-f9j-f9-1");
        mealVideos.put("Salteado de Abobrinha e Milho", "N8-f9j-f9-1");
        mealVideos.put("Steamed Mixed Vegetables", "O8-f9j-f9-2");
        mealVideos.put("Mistura de Vegetais ao Vapor", "O8-f9j-f9-2");
        mealVideos.put("Baked Acorn Squash", "P8-f9j-f9-3");
        mealVideos.put("Abóbora Menina Assada", "P8-f9j-f9-3");
        mealVideos.put("Sautéed Swiss Chard with Garlic", "Q8-f9j-f9-4");
        mealVideos.put("Acelga Salteada com Alho", "Q8-f9j-f9-4");
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

        Button buttonViewArticle = view.findViewById(R.id.buttonViewArticle);
        if (buttonViewArticle != null) {
            buttonViewArticle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), Activity_FullContent.class);
                    intent.putExtra("title", "Healthful Cooking Principles");
                    intent.putExtra("url", "https://www.google.com");
                    startActivity(intent);
                }
            });
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
            Toast.makeText(getContext(), "Vídeo não disponível para " + mealName, Toast.LENGTH_SHORT).show();
        }
    }

    private void startHintsSliding() {
        textSwitcherHints.setText(nutritionHints[currentHintIdx]);
        hintRunnable = new Runnable() {
            @Override
            public void run() {
                currentHintIdx++;
                if (currentHintIdx >= nutritionHints.length) currentHintIdx = 0;
                textSwitcherHints.setText(nutritionHints[currentHintIdx]);
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
        intent.putExtra("title", title + " Recipe");
        if (htmlContent != null) {
            intent.putExtra("content", htmlContent);
        } else {
            intent.putExtra("content", "<h1>Recipe coming soon!</h1><p>We are still working on adding " + title + " to our database.</p>");
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
                
                String lang = Locale.getDefault().getLanguage();
                String[][] currentSuggestions = lang.equals("pt") ? mealSuggestionsPt : mealSuggestionsEn;
                
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
