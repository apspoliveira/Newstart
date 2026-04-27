package newstart.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import newstart.Activity_Main;
import newstart.R;
import newstart.notifications.NotificationReceiver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Fragment_Settings extends Fragment implements AdapterView.OnItemSelectedListener {

    private String[] languages;
    private String currentLanguage;
    private boolean savePossible = false;
    private boolean firstSelect = true;

    private Button saveButton;
    private TextView textViewVerse;
    private TextView textViewReference;

    private CheckBox checkAir, checkNutrition, checkSun, checkWater, checkWorkout;
    private SharedPreferences sharedPrefs;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(getContext(), "Notifications enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Notifications permission denied", Toast.LENGTH_SHORT).show();
                    disableAllNotifCheckboxes();
                }
            });

    private void enableSaveButton() {
        saveButton.setBackgroundResource(R.drawable.shape_box_round_pop);
        saveButton.setTextColor(getContext().getColor(R.color.text_high));
        saveButton.setVisibility(View.VISIBLE);
        savePossible = true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Languages
        languages = new String[] {
                getResources().getString(R.string.lang_en),
                getResources().getString(R.string.lang_pt)
        };
        
        sharedPrefs = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // Language settings spinner
        Spinner spinner = view.findViewById(R.id.spinnerLanguages);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter adapterCategories = new ArrayAdapter(getContext(), R.layout.spinner_item_purple_middle, languages);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterCategories);

        // Bible Verse Views
        textViewVerse = view.findViewById(R.id.textViewVerse);
        textViewReference = view.findViewById(R.id.textViewReference);
        
        fetchBibleVerse();

        // Notification Checkboxes
        checkAir = view.findViewById(R.id.checkNotifAir);
        checkNutrition = view.findViewById(R.id.checkNotifNutrition);
        checkSun = view.findViewById(R.id.checkNotifSun);
        checkWater = view.findViewById(R.id.checkNotifWater);
        checkWorkout = view.findViewById(R.id.checkNotifWorkout);

        // Load saved notification settings
        checkAir.setChecked(sharedPrefs.getBoolean("notif_air", false));
        checkNutrition.setChecked(sharedPrefs.getBoolean("notif_nutrition", false));
        checkSun.setChecked(sharedPrefs.getBoolean("notif_sun", false));
        checkWater.setChecked(sharedPrefs.getBoolean("notif_water", false));
        checkWorkout.setChecked(sharedPrefs.getBoolean("notif_workout", false));

        View.OnClickListener notifClickListener = v -> {
            checkAndRequestNotificationPermission();
            enableSaveButton();
        };
        checkAir.setOnClickListener(notifClickListener);
        checkNutrition.setOnClickListener(notifClickListener);
        checkSun.setOnClickListener(notifClickListener);
        checkWater.setOnClickListener(notifClickListener);
        checkWorkout.setOnClickListener(notifClickListener);

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

                    ((Activity_Main) requireContext()).databaseHelper.setSettingsLanguage(currentLanguage);
                    
                    saveNotificationSettings();
                    Toast.makeText(getContext(), "Settings saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkAndRequestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void disableAllNotifCheckboxes() {
        checkAir.setChecked(false);
        checkNutrition.setChecked(false);
        checkSun.setChecked(false);
        checkWater.setChecked(false);
        checkWorkout.setChecked(false);
    }

    private void saveNotificationSettings() {
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putBoolean("notif_air", checkAir.isChecked());
        editor.putBoolean("notif_nutrition", checkNutrition.isChecked());
        editor.putBoolean("notif_sun", checkSun.isChecked());
        editor.putBoolean("notif_water", checkWater.isChecked());
        editor.putBoolean("notif_workout", checkWorkout.isChecked());
        editor.apply();

        updateAlarms();
    }

    private void updateAlarms() {
        scheduleAlarm("air", checkAir.isChecked(), 9, 0); // 9:00 AM
        scheduleAlarm("nutrition", checkNutrition.isChecked(), 12, 30); // 12:30 PM
        scheduleAlarm("sun", checkSun.isChecked(), 11, 40); // 10:00 AM
        scheduleAlarm("water", checkWater.isChecked(), 15, 0); // 3:00 PM
        scheduleAlarm("workout", checkWorkout.isChecked(), 17, 30); // 5:30 PM
    }

    private void scheduleAlarm(String type, boolean enable, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.EXTRA_TYPE, type);
        
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(), 
                type.hashCode(), 
                intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        if (enable) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                } else {
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void fetchBibleVerse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (firstSelect) {
            firstSelect = false;
            return;
        }

        switch (position) {
            case 0: currentLanguage = "de"; break;
            case 1: currentLanguage = "en"; break;
            case 2: currentLanguage = "pt"; break;
        }

        enableSaveButton();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
