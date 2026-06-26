package newstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import newstart.data.DatabaseHelper;
import newstart.fragments.Fragment_Air;
import newstart.fragments.Fragment_Nutrition;
import newstart.fragments.Fragment_Sun;
import newstart.fragments.Fragment_Water;
import newstart.fragments.Fragment_Workout;
import newstart.fragments.Fragment_Settings;
import newstart.fragments.Fragment_Trust;
import newstart.fragments.Fragment_Temperance;
import newstart.fragments.Fragment_Rest;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.appbar.MaterialToolbar;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    static {
        // Disable Night Mode globally - Removing dark theme option
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() { return null; }
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
            };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
        } catch (Exception ignored) {}
    }

    public String date;
    private int currentFragmentID = 0;
    public DatabaseHelper databaseHelper;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar toolbar;

    private void setFragmentNutrition(String date) {
        Fragment_Nutrition fragment = new Fragment_Nutrition();
        Bundle args = new Bundle();
        args.putString("date", date);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_meal);
    }

    private void setFragmentAir() {
        Fragment_Air fragment = new Fragment_Air();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_air);
    }

    private void setFragmentWater() {
        Fragment_Water fragment = new Fragment_Water();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_water);
    }

    private void setFragmentSun() {
        Fragment_Sun fragment = new Fragment_Sun();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_sun);
    }

    private void setFragmentWorkout() {
        Fragment_Workout fragment = new Fragment_Workout();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_exercise);
    }

    private void setFragmentTrust() {
        Fragment_Trust fragment = new Fragment_Trust();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_trust);
    }

    private void setFragmentTemperance() {
        Fragment_Temperance fragment = new Fragment_Temperance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_temperance);
    }

    private void setFragmentRest() {
        Fragment_Rest fragment = new Fragment_Rest();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_rest);
    }

    private void setFragmentSettings() {
        Fragment_Settings fragment = new Fragment_Settings();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        toolbar.setTitle(R.string.nav_settings);
    }

    private static void updateLanguage(Context context, String language) {
        if (language == null || language.equals("system")) return;
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        databaseHelper = new DatabaseHelper(Activity_Main.this);
        Cursor cursor = databaseHelper.getSettingsLanguage();
        if (cursor != null && cursor.moveToFirst()) {
            updateLanguage(this, cursor.getString(1));
            cursor.close();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.button_text_save, R.string.button_text_cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        updateNavHeader();

        Intent intent = getIntent();
        if (getIntent().hasExtra("date")) {
            date = intent.getStringExtra("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter.format(new Date());
        }

        if (getIntent().hasExtra("fragmentID")) {
            currentFragmentID = intent.getIntExtra("fragmentID", 0);
        }

        displaySelectedScreen(currentFragmentID);
    }

    private void updateNavHeader() {
        if (navigationView != null && navigationView.getHeaderCount() > 0) {
            View headerView = navigationView.getHeaderView(0);
            TextView textViewName = headerView.findViewById(R.id.textViewHeaderName);
            TextView textViewStats = headerView.findViewById(R.id.textViewHeaderStats);

            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String name = prefs.getString("user_name", "User");
            int age = prefs.getInt("user_age", 0);
            float weight = prefs.getFloat("user_weight", 0f);
            float height = prefs.getFloat("user_height", 0f);

            if (textViewName != null) {
                textViewName.setText(name);
            }
            if (textViewStats != null) {
                String stats = String.format(Locale.getDefault(), "%d anos | %.1f kg | %.0f cm", age, weight, height);
                textViewStats.setText(stats);
            }
        }
    }

    private void displaySelectedScreen(int itemId) {
        if (itemId == 0 || itemId == R.id.nav_nutrition) {
            setFragmentNutrition(date);
            navigationView.setCheckedItem(R.id.nav_nutrition);
        } else if (itemId == 1 || itemId == R.id.nav_exercise) {
            setFragmentWorkout();
            navigationView.setCheckedItem(R.id.nav_exercise);
        } else if (itemId == 2 || itemId == R.id.nav_water) {
            setFragmentWater();
            navigationView.setCheckedItem(R.id.nav_water);
        } else if (itemId == 3 || itemId == R.id.nav_sun) {
            setFragmentSun();
            navigationView.setCheckedItem(R.id.nav_sun);
        } else if (itemId == 4 || itemId == R.id.nav_temperance) {
            setFragmentTemperance();
            navigationView.setCheckedItem(R.id.nav_temperance);
        } else if (itemId == 5 || itemId == R.id.nav_air) {
            setFragmentAir();
            navigationView.setCheckedItem(R.id.nav_air);
        } else if (itemId == 6 || itemId == R.id.nav_rest) {
            setFragmentRest();
            navigationView.setCheckedItem(R.id.nav_rest);
        } else if (itemId == 7 || itemId == R.id.nav_trust) {
            setFragmentTrust();
            navigationView.setCheckedItem(R.id.nav_trust);
        } else if (itemId == 8 || itemId == R.id.nav_settings) {
            setFragmentSettings();
            navigationView.setCheckedItem(R.id.nav_settings);
        } else {
            setFragmentNutrition(date);
            navigationView.setCheckedItem(R.id.nav_nutrition);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (databaseHelper != null) databaseHelper.close();
        super.onDestroy();
    }
}
