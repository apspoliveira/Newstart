package com.falyrion.gymtonicapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.falyrion.gymtonicapp.R;
import com.falyrion.gymtonicapp.activities.Activity_Calendar;
import com.falyrion.gymtonicapp.activities.Activity_FullContent;
import com.falyrion.gymtonicapp.data.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Fragment_Nutrition extends Fragment {

    private String date;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

        Button buttonViewRecipe = view.findViewById(R.id.buttonViewRecipe);
        if (buttonViewRecipe != null) {
            buttonViewRecipe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper db = new DatabaseHelper(getContext());
                    String htmlContent = db.getRecipeContent("Garden Lentil Stew");

                    if (htmlContent == null) {
                        // Fallback if not found in DB
                        htmlContent = "<h1>Recipe not found</h1>";
                    }

                    Intent intent = new Intent(getContext(), Activity_FullContent.class);
                    intent.putExtra("title", "Garden Lentil Stew Recipe");
                    intent.putExtra("content", htmlContent);
                    startActivity(intent);
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
    }
}
