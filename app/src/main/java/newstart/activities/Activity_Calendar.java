package newstart.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import newstart.Activity_Main;
import newstart.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Activity_Calendar extends AppCompatActivity {

    private String date;
    private String newDate;
    private int fragmentID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Force Light Mode
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        // Get current date and fragment context
        Intent intent = getIntent();
        if (getIntent().hasExtra("date")) {
            date = intent.getStringExtra("date");
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            date = formatter.format(new Date());
        }
        newDate = date;
        fragmentID = intent.getIntExtra("fragmentID", 0);

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbarActivityCalendar);
        toolbar.setTitle(getString(R.string.settings_goals)); 
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        
        updateThemeColors(toolbar);

        Button buttonConfirm = findViewById(R.id.buttonConfirmSetDate);
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Activity_Main.class);
                intent.putExtra("date", newDate);
                intent.putExtra("fragmentID", fragmentID);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION); 
                startActivity(intent);
                finish();
            }
        });

        Button buttonCancel = findViewById(R.id.buttonCancelSetDate);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String dayString = (day < 10) ? "0" + day : String.valueOf(day);
                String monthString = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                newDate = dayString + "-" + monthString + "-" + year;
                buttonConfirm.setAlpha(1.0f);
            }
        });
    }

    private void updateThemeColors(Toolbar toolbar) {
        int colorRes = R.color.nutrition_primary;
        switch (fragmentID) {
            case 0: colorRes = R.color.nutrition_primary; break;
            case 1: colorRes = R.color.workout_primary; break;
            case 2: colorRes = R.color.water_primary; break;
            case 3: colorRes = R.color.sun_primary; break;
            case 4: colorRes = R.color.temperance_primary; break;
            case 5: colorRes = R.color.air_primary; break;
            case 6: colorRes = R.color.rest_primary; break;
            case 7: colorRes = R.color.trust_primary; break;
            case 8: colorRes = R.color.settings_primary; break;
        }

        int color = ContextCompat.getColor(this, colorRes);
        toolbar.setBackgroundColor(color);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }

        findViewById(R.id.buttonConfirmSetDate).setBackgroundTintList(ContextCompat.getColorStateList(this, colorRes));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
