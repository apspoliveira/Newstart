package newstart.fragments;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import newstart.R;
import newstart.activities.Activity_Calendar;
import newstart.activities.Activity_FullContent;
import newstart.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class Fragment_Nutrition extends Fragment {

    private String date;
    private String todayBreakfast = "";
    private String todayLunch = "";
    private String todayDinner = "";

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
    private final String[][] mealSuggestions = {
            {"Oatmeal with Blueberries and Walnuts", "Quinoa Salad with Roasted Vegetables", "Steamed Broccoli and Baked Tofu"},
            {"Whole Grain Pancakes with Fresh Fruit", "Black Bean and Corn Tacos", "Lentil Soup with Kale"},
            {"Fruit Smoothie with Flax Seeds", "Chickpea Curry with Brown Rice", "Mixed Green Salad with Seeds"},
            {"Buckwheat Porridge with Almonds", "Hummus and Veggie Wrap", "Vegetable Stir-fry with Tempeh"},
            {"Chia Pudding with Mango", "Sweet Potato and Black Bean Chili", "Roasted Cauliflower with Tahini"},
            {"Whole Wheat Toast with Avocado", "Lentil and Vegetable Stew", "Zucchini Noodles with Pesto"},
            {"Millet with Dates and Cashews", "Quinoa and Black Bean Bowl", "Baked Sweet Potato with Greens"}
    };

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
            playBreakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo(todayBreakfast);
                }
            });
        }

        ImageView playLunch = view.findViewById(R.id.buttonPlayVideoLunch);
        if (playLunch != null) {
            playLunch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo(todayLunch);
                }
            });
        }

        ImageView playDinner = view.findViewById(R.id.buttonPlayVideoDinner);
        if (playDinner != null) {
            playDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playVideo(todayDinner);
                }
            });
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

    private void playVideo(String mealName) {
        String videoId = null;
        if ("Oatmeal with Blueberries and Walnuts".equals(mealName)) {
            videoId = "n4PkKFWlisg";
        }

        if (videoId != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoId));
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Video coming soon for " + mealName, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMealSuggestions(View view) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            Date dateObj = formatter.parse(date);
            if (dateObj != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(dateObj);
                int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
                int suggestionIndex = dayOfYear % mealSuggestions.length;

                String[] todayMeals = mealSuggestions[suggestionIndex];
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
