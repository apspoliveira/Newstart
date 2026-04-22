package newstart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import newstart.data.DatabaseHelper;
import newstart.fragments.Fragment_Air;
import newstart.fragments.Fragment_Nutrition;
import newstart.fragments.Fragment_Sun;
import newstart.fragments.Fragment_Water;
import newstart.fragments.Fragment_Workout;
import newstart.fragments.Fragment_Settings;

import com.google.android.material.tabs.TabLayout;

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

    private void setFragmentSun() {
        Fragment_Sun fragment = new Fragment_Sun();
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
            case 0: setFragmentWorkout(); break;
            case 1: setFragmentNutrition(date); break;
            case 4: setFragmentAir(); break;
            case 5: setFragmentWater(); break;
            case 6: setFragmentSun(); break;
            case 3: setFragmentSettings(); break;
            default: setFragmentNutrition(date); break;
        }

        // -----------------------------------------------------------------------------------------
        // Setup navigation bar (using TabLayout to support >5 items)
        TabLayout tabLayout = findViewById(R.id.bottom_navigation_tabs);
        
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0: // Home
                        if (currentFragmentID != 0) {
                            setFragmentWorkout();
                            currentFragmentID = 0;
                        }
                        break;
                    case 1: // Air
                        if (currentFragmentID != 4) {
                            setFragmentAir();
                            currentFragmentID = 4;
                        }
                        break;
                    case 2: // Water
                        if (currentFragmentID != 5) {
                            setFragmentWater();
                            currentFragmentID = 5;
                        }
                        break;
                    case 3: // Sun
                        if (currentFragmentID != 6) {
                            setFragmentSun();
                            currentFragmentID = 6;
                        }
                        break;
                    case 4: // Meal
                        if (currentFragmentID != 1) {
                            setFragmentNutrition(date);
                            currentFragmentID = 1;
                        }
                        break;
                    case 5: // Settings
                        if (currentFragmentID != 3) {
                            setFragmentSettings();
                            currentFragmentID = 3;
                        }
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        // Set initial tab selection based on currentFragmentID
        TabLayout.Tab initialTab = null;
        switch (currentFragmentID) {
            case 0: initialTab = tabLayout.getTabAt(0); break;
            case 1: initialTab = tabLayout.getTabAt(4); break;
            case 4: initialTab = tabLayout.getTabAt(1); break;
            case 5: initialTab = tabLayout.getTabAt(2); break;
            case 6: initialTab = tabLayout.getTabAt(3); break;
            case 3: initialTab = tabLayout.getTabAt(5); break;
        }
        if (initialTab != null) initialTab.select();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            setFragmentSettings();
            currentFragmentID = 3;
            // Update tab selection
            TabLayout tabLayout = findViewById(R.id.bottom_navigation_tabs);
            TabLayout.Tab settingsTab = tabLayout.getTabAt(5);
            if (settingsTab != null) settingsTab.select();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
        super.onDestroy();
    }
}
