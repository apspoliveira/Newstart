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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import newstart.Activity_Main;
import newstart.R;
import newstart.notifications.NotificationReceiver;

import java.util.Calendar;

public class Fragment_Settings extends Fragment {

    private String[] languages;
    private String currentLanguage = "pt";

    private Button saveButton;
    private CheckBox checkAir, checkNutrition, checkSun, checkWater, checkWorkout, checkTrust, checkTemperance, checkRest;
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
        AutoCompleteTextView spinner = view.findViewById(R.id.spinnerLanguages);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_item_purple_middle, languages);
        spinner.setAdapter(adapter);

        checkAir = view.findViewById(R.id.checkNotifAir);
        checkNutrition = view.findViewById(R.id.checkNotifNutrition);
        checkSun = view.findViewById(R.id.checkNotifSun);
        checkWater = view.findViewById(R.id.checkNotifWater);
        checkWorkout = view.findViewById(R.id.checkNotifWorkout);
        checkTrust = view.findViewById(R.id.checkNotifTrust);
        checkTemperance = view.findViewById(R.id.checkNotifTemperance);
        checkRest = view.findViewById(R.id.checkNotifRest);

        checkAir.setChecked(sharedPrefs.getBoolean("notif_air", false));
        checkNutrition.setChecked(sharedPrefs.getBoolean("notif_nutrition", false));
        checkSun.setChecked(sharedPrefs.getBoolean("notif_sun", false));
        checkWater.setChecked(sharedPrefs.getBoolean("notif_water", false));
        checkWorkout.setChecked(sharedPrefs.getBoolean("notif_workout", false));
        checkTrust.setChecked(sharedPrefs.getBoolean("notif_trust", false));
        checkTemperance.setChecked(sharedPrefs.getBoolean("notif_temp", false));
        checkRest.setChecked(sharedPrefs.getBoolean("notif_rest", false));

        View.OnClickListener notifClickListener = v -> checkAndRequestNotificationPermission();
        checkAir.setOnClickListener(notifClickListener);
        checkNutrition.setOnClickListener(notifClickListener);
        checkSun.setOnClickListener(notifClickListener);
        checkWater.setOnClickListener(notifClickListener);
        checkWorkout.setOnClickListener(notifClickListener);
        checkTrust.setOnClickListener(notifClickListener);
        checkTemperance.setOnClickListener(notifClickListener);
        checkRest.setOnClickListener(notifClickListener);

        Cursor cursor = ((Activity_Main) requireActivity()).databaseHelper.getSettingsLanguage();
        if (cursor != null && cursor.moveToFirst()) {
            currentLanguage = cursor.getString(1);
            int selection = 0; // Default PT
            if ("en".equals(currentLanguage)) selection = 1;
            else if ("de".equals(currentLanguage)) selection = 2;
            spinner.setText(languages[selection], false);
            cursor.close();
        } else {
            spinner.setText(languages[0], false);
        }

        spinner.setOnItemClickListener((parent, v, position, id) -> {
            switch (position) {
                case 0: currentLanguage = "pt"; break;
                case 1: currentLanguage = "en"; break;
                case 2: currentLanguage = "de"; break;
            }
        });

        saveButton = view.findViewById(R.id.buttonSaveSettings);
        saveButton.setOnClickListener(v -> {
            ((Activity_Main) requireActivity()).databaseHelper.setSettingsLanguage(currentLanguage);
            saveNotificationSettings();
            Toast.makeText(getContext(), "Configurações salvas!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(requireActivity(), Activity_Main.class);
            intent.putExtra("fragmentID", 8); // Settings fragment
            startActivity(intent);
            requireActivity().finish();
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
        checkTrust.setChecked(false);
        checkTemperance.setChecked(false);
        checkRest.setChecked(false);
    }

    private void saveNotificationSettings() {
        sharedPrefs.edit()
                .putBoolean("notif_air", checkAir.isChecked())
                .putBoolean("notif_nutrition", checkNutrition.isChecked())
                .putBoolean("notif_sun", checkSun.isChecked())
                .putBoolean("notif_water", checkWater.isChecked())
                .putBoolean("notif_workout", checkWorkout.isChecked())
                .putBoolean("notif_trust", checkTrust.isChecked())
                .putBoolean("notif_temp", checkTemperance.isChecked())
                .putBoolean("notif_rest", checkRest.isChecked())
                .apply();
        updateAlarms();
    }

    private void updateAlarms() {
        scheduleAlarm("air", checkAir.isChecked(), 9, 0);
        scheduleAlarm("nutrition", checkNutrition.isChecked(), 12, 30);
        scheduleAlarm("sun", checkSun.isChecked(), 11, 40);
        scheduleAlarm("water", checkWater.isChecked(), 15, 0);
        scheduleAlarm("workout", checkWorkout.isChecked(), 17, 30);
        scheduleAlarm("temp", checkTemperance.isChecked(), 10, 0);
        scheduleAlarm("rest", checkRest.isChecked(), 22, 0);
        scheduleAlarm("trust", checkTrust.isChecked(), 20, 0);
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
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        } else {
            alarmManager.cancel(pendingIntent);
        }
    }
}
