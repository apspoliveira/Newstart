package newstart.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import newstart.Activity_Main;
import newstart.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Fragment_Settings extends Fragment implements AdapterView.OnItemSelectedListener {

    private double[] dataGoals;
    private String[] languages;
    private String currentLanguage;
    private boolean savePossible = false;
    private boolean firstSelect = true;

    private Button saveButton;
    private TextView textViewVerse;
    private TextView textViewReference;

    private class textWatcher implements TextWatcher {
        private int id;
        private textWatcher(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // pass
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // pass
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // Update value
            if (!editable.toString().isEmpty()) {
                dataGoals[id] = Double.parseDouble(editable.toString());
            }

            // Update background resource of save button
            enableSaveButton();
        }
    }

    private String convertDataToText(double value) {
        // Convert given double to string.
        // Check if double value has ".0" decimals. If yes cut it out.
        if (value % 1 == 0) {
            return String.valueOf((int) value);
        } else {
            return String.valueOf(value);
        }
    }

    private void enableSaveButton() {
        saveButton.setBackgroundResource(R.drawable.shape_box_round_pop);
        saveButton.setTextColor(getContext().getColor(R.color.text_high));
        saveButton.setVisibility(View.VISIBLE);
        savePossible = true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load data from database
        Cursor cursorGoals = ((Activity_Main) requireContext()).databaseHelper.getSettingsGoals();
        if (cursorGoals.getCount() > 0) {
            cursorGoals.moveToFirst();
            dataGoals = new double[] {
                    cursorGoals.getDouble(0),
                    cursorGoals.getDouble(1),
                    cursorGoals.getDouble(2),
                    cursorGoals.getDouble(3)
            };
        } else {
            dataGoals = new double[] {0, 0, 0, 0};
        }
        cursorGoals.close();

        // Languages
        languages = new String[] {
                getResources().getString(R.string.lang_de),
                getResources().getString(R.string.lang_en),
                getResources().getString(R.string.lang_pt)
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Nutrition goal settings
        EditText editTextCalories = view.findViewById(R.id.editTextSettingsGoalsCal);
        editTextCalories.setText(convertDataToText(dataGoals[0]));
        editTextCalories.addTextChangedListener(new textWatcher(0));

        EditText editTextFat = view.findViewById(R.id.editTextSettingsGoalsFat);
        editTextFat.setText(convertDataToText(dataGoals[1]));
        editTextFat.addTextChangedListener(new textWatcher(1));

        EditText editTextCarbs = view.findViewById(R.id.editTextSettingsGoalsCarbs);
        editTextCarbs.setText(convertDataToText(dataGoals[2]));
        editTextCarbs.addTextChangedListener(new textWatcher(2));

        EditText editTextProtein = view.findViewById(R.id.editTextSettingsGoalsProtein);
        editTextProtein.setText(convertDataToText(dataGoals[3]));
        editTextProtein.addTextChangedListener(new textWatcher(3));


        // Language settings spinner
        Spinner spinner = view.findViewById(R.id.spinnerLanguages);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategories = new ArrayAdapter(getContext(), R.layout.spinner_item_purple_middle, languages);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategories);

        // Bible Verse Views
        textViewVerse = view.findViewById(R.id.textViewBibleVerse);
        textViewReference = view.findViewById(R.id.textViewBibleReference);
        fetchBibleVerse();

        // Button
        saveButton = view.findViewById(R.id.buttonSaveSettings);
        saveButton.setVisibility(View.INVISIBLE);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (savePossible) {
                    savePossible = false;
                    saveButton.setBackgroundResource(R.drawable.shape_box_round_middle);
                    saveButton.setTextColor(getContext().getColor(R.color.text_middle));
                    saveButton.setVisibility(View.INVISIBLE);

                    ((Activity_Main) requireContext()).databaseHelper.setSettingsGoals(dataGoals[0], dataGoals[1], dataGoals[2], dataGoals[3]);
                    ((Activity_Main) requireContext()).databaseHelper.setSettingsLanguage(currentLanguage);

                }
            }
        });
    }

    private void fetchBibleVerse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                // Using labs.bible.org API for a random verse
                URL url = new URL("https://labs.bible.org/api/?passage=random&type=json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                JSONArray jsonArray = new JSONArray(response.toString());
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String verseText = jsonObject.getString("text");
                    String reference = jsonObject.getString("bookname") + " " +
                            jsonObject.getString("chapter") + ":" +
                            jsonObject.getString("verse");

                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            textViewVerse.setText(getString(R.string.bible_verse_format, verseText));
                            textViewReference.setText(getString(R.string.bible_reference_format, reference));
                        });
                    }
                }

            } catch (Exception e) {
                Log.e("Fragment_Settings", "Error fetching bible verse", e);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        textViewVerse.setText(R.string.settings_bible_error);
                    });
                }
            }
        });
    }

    // Methods from imported spinner interface -----------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        // Guard clause against the automatic item select on creation
        if (firstSelect) {
            firstSelect = false;
            return;
        }

        // Set value
        switch (position) {
            case 0: currentLanguage = "de"; break;
            case 1: currentLanguage = "en"; break;
            case 2: currentLanguage = "pt"; break;
        }

        // Update button
        enableSaveButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Pass
    }
}
