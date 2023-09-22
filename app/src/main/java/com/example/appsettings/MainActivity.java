package com.example.appsettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SharedPreferences sharedPreferences;
    private TextView textView;
    private int currentCount = 0;
    private static final String PREFS_NAME = "MyPrefs";
    private static final String COUNT_KEY = "count";
    private static final String BACKGROUND_COLOR_KEY = "background_color";
    private static final String COUNT_SWITCH_KEY = "count_switch";
    private static final String BACKGROUND_SWITCH_KEY = "background_switch";
    private int backgroundColor = Color.WHITE;
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(COUNT_SWITCH_KEY) || key.equals(BACKGROUND_SWITCH_KEY)) {
            boolean countSwitchValue = sharedPreferences.getBoolean(COUNT_SWITCH_KEY, true);
            boolean backgroundSwitchValue = sharedPreferences.getBoolean(BACKGROUND_SWITCH_KEY, true);

            if (!countSwitchValue) {
                clearSharedPreferencesCount();
            }
            if (!backgroundSwitchValue) {
                clearSharedPreferencesBackground();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        textView = findViewById(R.id.textView);
        loadSharedPreferences();
        textView.setText(String.valueOf(currentCount));
        Button buttonCount = findViewById(R.id.buttonCount);
        buttonCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCount++;
                textView.setText(String.valueOf(currentCount));
            }
        });
        Button buttonBlack = findViewById(R.id.buttonBlack);
        buttonBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = Color.BLACK;
                textView.setBackgroundColor(backgroundColor);
                saveSharedPreferences();
            }
        }); Button buttonRed = findViewById(R.id.buttonRed);
        buttonRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = Color.RED;
                textView.setBackgroundColor(backgroundColor);
                saveSharedPreferences();
            }
        }); Button buttonBlue = findViewById(R.id.buttonBlue);
        buttonBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = Color.BLUE;
                textView.setBackgroundColor(backgroundColor);
                saveSharedPreferences();
            }
        }); Button buttonGreen = findViewById(R.id.buttonGreen);
        buttonGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundColor = Color.GREEN;
                textView.setBackgroundColor(backgroundColor);
                saveSharedPreferences();
            }
        });
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        saveSharedPreferences();

        // Kiểm tra giá trị của count_switch và background_switch
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean countSwitchValue = sharedPreferences.getBoolean("count_switch", true);
        boolean backgroundSwitchValue = sharedPreferences.getBoolean("background_switch", true);

        if (!countSwitchValue) {
            clearSharedPreferencesCount();
        }
        if (!backgroundSwitchValue) {
            clearSharedPreferencesBackground();
        }
    }
    private void saveSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COUNT_KEY, currentCount);
        editor.putInt(BACKGROUND_COLOR_KEY, backgroundColor);
        editor.apply();
    }

    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        currentCount = sharedPreferences.getInt(COUNT_KEY, 0);
        backgroundColor = sharedPreferences.getInt(BACKGROUND_COLOR_KEY, Color.WHITE);
        textView.setText(String.valueOf(currentCount));
        textView.setBackgroundColor(backgroundColor);
    }
    private void clearSharedPreferencesCount() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(COUNT_KEY);
        editor.apply();
        currentCount = 0;
        textView.setText(String.valueOf(currentCount));
    }
    private void clearSharedPreferencesBackground() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(BACKGROUND_COLOR_KEY);
        editor.apply();
        backgroundColor = Color.WHITE;
        textView.setBackgroundColor(backgroundColor);
    }
}