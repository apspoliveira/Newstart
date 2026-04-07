package newstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;

import newstart.data.DatabaseHelper;
import newstart.fragments.Fragment_Air;
import newstart.fragments.Fragment_Nutrition;
import newstart.fragments.Fragment_Water;
import newstart.fragments.Fragment_Workout;
import newstart.fragments.Fragment_Settings;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Main extends AppCompatActivity {

    public String date;
    private int currentFragmentID = 0;
    public DatabaseHelper databaseHelper;


    private void setFragmentNutrition(String date) {
        Fragment_Nutrition fragment = new Fragment_Nutrition();
        Bundle args = new Bundle();
        args.putString("date", date);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragmentAir() {
        Fragment_Air fragment = new Fragment_Air();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragmentWater() {
        Fragment_Water fragment = new Fragment_Water();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragmentWorkout() {
        Fragment_Workout fragment = new Fragment_Workout();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private void setFragmentSettings() {
        Fragment_Settings fragment = new Fragment_Settings();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }

    private static void updateLanguage(Context context, String language) {

        if (language.equals("system")) {
            return;
        }

        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        databaseHelper = new DatabaseHelper(Activity_Main.this);

        // Update language
        Cursor cursor = databaseHelper.getSettingsLanguage();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            updateLanguage(this, cursor.getString(0));
        }
        cursor.close();

        // -----------------------------------------------------------------------------------------
        // Get data if activity was started by another activity
        Intent intent = getIntent();

        // Get current date
        if (getIntent().hasExtra("date")) {
            date = intent.getStringExtra("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter.format(new Date());
        }

        // If there is a fragmentID submitted take it. Else keep previously set or default one (=0)
        if (getIntent().hasExtra("fragmentID")) {
            currentFragmentID = intent.getIntExtra("fragmentID", 0);
        }

        // Set current fragment based on fragmentID
        switch (currentFragmentID) {
            case 0:
                setFragmentWorkout(); // Home shows workout now? Or we could create a real home.
                break;
            case 1:
                setFragmentNutrition(date);
                break;
            case 4:
                setFragmentAir();
                break;
            case 5:
                setFragmentWater();
                break;
            case 3:
                setFragmentSettings();
                break;
            default:
                setFragmentNutrition(date);
                break;
        }

        // -----------------------------------------------------------------------------------------
        // Setup navigation bar
        BottomNavigationView navBar = findViewById(R.id.bottom_navigation);
        navBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_bar_home) {
                    if (currentFragmentID != 0) {
                        setFragmentWorkout();
                        currentFragmentID = 0;
                    }
                    return true;
                } else if (itemId == R.id.nav_bar_air) {
                    if (currentFragmentID != 4) {
                        setFragmentAir();
                        currentFragmentID = 4;
                    }
                    return true;
                } else if (itemId == R.id.nav_bar_water) {
                    if (currentFragmentID != 5) {
                        setFragmentWater();
                        currentFragmentID = 5;
                    }
                    return true;
                } else if (itemId == R.id.nav_bar_meal) {
                    if (currentFragmentID != 1) {
                        setFragmentNutrition(date);
                        currentFragmentID = 1;
                    }
                    return true;
                } else if (itemId == R.id.nav_bar_settings) {
                    if (currentFragmentID != 3) {
                        setFragmentSettings();
                        currentFragmentID = 3;
                    }
                    return true;
                }
                return false;
            }
        });

        // Set selected item in nav bar
        if (currentFragmentID == 0) navBar.setSelectedItemId(R.id.nav_bar_home);
        else if (currentFragmentID == 1) navBar.setSelectedItemId(R.id.nav_bar_meal);
        else if (currentFragmentID == 4) navBar.setSelectedItemId(R.id.nav_bar_air);
        else if (currentFragmentID == 5) navBar.setSelectedItemId(R.id.nav_bar_water);
        else if (currentFragmentID == 3) navBar.setSelectedItemId(R.id.nav_bar_settings);
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroy();
    }
}
