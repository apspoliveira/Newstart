package newstart.fragments;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
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
    private String currentLanguage = "pt";
    private boolean firstSelect = true;

    private Button saveButton;
    private TextView textViewVerse;
    private TextView textViewReference;

    private CheckBox checkAir, checkNutrition, checkSun, checkWater, checkWorkout;
    private SharedPreferences sharedPrefs;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(getContext(), "Notificações ativadas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permissão negada", Toast.LENGTH_SHORT).show();
                    disableAllNotifCheckboxes();
                }
            });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Alterado para Português ser o primeiro da lista
        languages = new String[] {
                getString(R.string.lang_pt),
                getString(R.string.lang_en),
                getString(R.string.lang_de)
        };
        sharedPrefs = requireActivity().getSharedPreferences("NEWSTART_Prefs", Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spinner spinner = view.findViewById(R.id.spinnerLanguages);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_purple_middle, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        textViewVerse = view.findViewById(R.id.textViewVerse);
        textViewReference = view.findViewById(R.id.textViewReference);
        
        fetchBibleVerse();

        checkAir = view.findViewById(R.id.checkNotifAir);
        checkNutrition = view.findViewById(R.id.checkNotifNutrition);
        checkSun = view.findViewById(R.id.checkNotifSun);
        checkWater = view.findViewById(R.id.checkNotifWater);
        checkWorkout = view.findViewById(R.id.checkNotifWorkout);

        checkAir.setChecked(sharedPrefs.getBoolean("notif_air", false));
        checkNutrition.setChecked(sharedPrefs.getBoolean("notif_nutrition", false));
        checkSun.setChecked(sharedPrefs.getBoolean("notif_sun", false));
        checkWater.setChecked(sharedPrefs.getBoolean("notif_water", false));
        checkWorkout.setChecked(sharedPrefs.getBoolean("notif_workout", false));

        View.OnClickListener notifClickListener = v -> checkAndRequestNotificationPermission();
        checkAir.setOnClickListener(notifClickListener);
        checkNutrition.setOnClickListener(notifClickListener);
        checkSun.setOnClickListener(notifClickListener);
        checkWater.setOnClickListener(notifClickListener);
        checkWorkout.setOnClickListener(notifClickListener);

        Cursor cursor = ((Activity_Main) requireActivity()).databaseHelper.getSettingsLanguage();
        if (cursor != null && cursor.moveToFirst()) {
            currentLanguage = cursor.getString(1);
            int selection = 0; // Default PT (agora no índice 0)
            if ("en".equals(currentLanguage)) selection = 1;
            else if ("de".equals(currentLanguage)) selection = 2;
            spinner.setSelection(selection);
            cursor.close();
        } else {
            // Se não houver configuração, garante que o Português (índice 0) está selecionado
            spinner.setSelection(0);
        }

        spinner.setOnItemSelectedListener(this);

        saveButton = view.findViewById(R.id.buttonSaveSettings);
        saveButton.setVisibility(View.VISIBLE); 
        saveButton.setOnClickListener(v -> {
            ((Activity_Main) requireContext()).databaseHelper.setSettingsLanguage(currentLanguage);
            saveNotificationSettings();
            Toast.makeText(getContext(), "Configurações salvas!", Toast.LENGTH_SHORT).show();
            // Recarrega a atividade para aplicar idioma
            Intent intent = new Intent(getActivity(), Activity_Main.class);
            intent.putExtra("fragmentID", 3);
            startActivity(intent);
            getActivity().finish();
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
        sharedPrefs.edit()
                .putBoolean("notif_air", checkAir.isChecked())
                .putBoolean("notif_nutrition", checkNutrition.isChecked())
                .putBoolean("notif_sun", checkSun.isChecked())
                .putBoolean("notif_water", checkWater.isChecked())
                .putBoolean("notif_workout", checkWorkout.isChecked())
                .apply();
        updateAlarms();
    }

    private void updateAlarms() {
        scheduleAlarm("air", checkAir.isChecked(), 9, 0);
        scheduleAlarm("nutrition", checkNutrition.isChecked(), 12, 30);
        scheduleAlarm("sun", checkSun.isChecked(), 11, 40);
        scheduleAlarm("water", checkWater.isChecked(), 15, 0);
        scheduleAlarm("workout", checkWorkout.isChecked(), 17, 30);
    }

    private void scheduleAlarm(String type, boolean enable, int hour, int minute) {
        AlarmManager alarmManager = (AlarmManager) requireContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), NotificationReceiver.class);
        intent.putExtra(NotificationReceiver.EXTRA_TYPE, type);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), type.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (enable) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) calendar.add(Calendar.DAY_OF_YEAR, 1);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }

    private void fetchBibleVerse() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
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
                        textViewVerse.setText("O Senhor é o meu pastor, nada me faltará.");
                        textViewReference.setText("- Salmos 23:1");
                    });
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if (firstSelect) { firstSelect = false; return; }
        switch (position) {
            case 0: currentLanguage = "pt"; break;
            case 1: currentLanguage = "en"; break;
            case 2: currentLanguage = "de"; break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
}
