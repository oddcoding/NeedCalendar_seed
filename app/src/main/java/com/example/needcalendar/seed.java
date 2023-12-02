package com.example.needcalendar;

import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class seed extends AppCompatActivity {
    private Button button;
    private ImageView imageView;
    private TextView timeSeedTextView;

    private int[] imageArray = {R.drawable.red, R.drawable.yellow, R.drawable.cherry, R.drawable.heart};
    private int currentImageIndex = 0;
    private int imageRepetitionCount = 0;
    private boolean isButtonEnabled = true;

    private static final String IMAGE_INDEX_KEY = "image_index";
    private static final String LAST_BUTTON_PRESS_DATE_KEY = "last_button_press_date";
    private static final String BUTTON_ENABLED_KEY = "button_enabled";
    private static final long MILLIS_IN_A_DAY = 24 * 60 * 60 * 1000;

    private final Handler handler = new Handler();
    private Runnable updateTimeTask;
    private Runnable enableButtonTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seed);

        button = findViewById(R.id.seedButton);
        imageView = findViewById(R.id.seedIcon);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentImageIndex = preferences.getInt(IMAGE_INDEX_KEY, 0);
        imageView.setImageResource(imageArray[currentImageIndex]);

        long lastButtonPressDate = preferences.getLong(LAST_BUTTON_PRESS_DATE_KEY, 0);

        if (isOneDayPassed(lastButtonPressDate)) {
            isButtonEnabled = true;
        } else {
            disableButtonUntilSpecificTime();
            updateRemainingTime();
            enableButtonAfterSpecificTime();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isButtonEnabled) {
                    return;
                }

                setImageAndIncrementCount();

                if (imageRepetitionCount >= 4) {
                    imageRepetitionCount = 0;
                    currentImageIndex = (currentImageIndex + 1) % imageArray.length;
                    imageView.setImageResource(imageArray[currentImageIndex]);

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(IMAGE_INDEX_KEY, currentImageIndex);
                    editor.putLong(LAST_BUTTON_PRESS_DATE_KEY, Calendar.getInstance().getTimeInMillis());
                    editor.putBoolean(BUTTON_ENABLED_KEY, isButtonEnabled);
                    editor.apply();
                }

                disableButtonUntilSpecificTime();
                updateRemainingTime();
            }
        });
    }

    private void setImageAndIncrementCount() {
        imageView.setImageResource(imageArray[currentImageIndex]);
        imageRepetitionCount++;
    }

    private void disableButtonUntilSpecificTime() {
        button.setEnabled(false);
        isButtonEnabled = false;
        button.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long specificTimeInMillis = cal.getTimeInMillis();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isButtonEnabled = true;
                button.setEnabled(true);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(seed.this);
                preferences.edit().putBoolean(BUTTON_ENABLED_KEY, true).apply();
            }
        }, specificTimeInMillis - System.currentTimeMillis());
    }

    private boolean isOneDayPassed(long lastButtonPressDate) {
        if (lastButtonPressDate == 0) {
            return true;
        }

        long currentTime = Calendar.getInstance().getTimeInMillis();
        return (currentTime - lastButtonPressDate) >= MILLIS_IN_A_DAY;
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(BUTTON_ENABLED_KEY, isButtonEnabled);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        isButtonEnabled = preferences.getBoolean(BUTTON_ENABLED_KEY, true);
        if (!isButtonEnabled) {
            disableButtonUntilSpecificTime();
        }
    }

    private void updateRemainingTime() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long specificTimeInMillis = cal.getTimeInMillis();

        long remainingTimeInMillis = specificTimeInMillis - System.currentTimeMillis();
        long remainingHours = remainingTimeInMillis / (60 * 60 * 1000);
        long remainingMinutes = (remainingTimeInMillis % (60 * 60 * 1000)) / (60 * 1000);

        String remainingTimeString = "남은 시간: " + remainingHours + "시간 " + remainingMinutes + "분";
        timeSeedTextView.setText(remainingTimeString);
    }


    // 액티비티가 종료될 때 태스크를 제거
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateTimeTask);
        handler.removeCallbacks(enableButtonTask);
    }

    private void enableButtonAfterSpecificTime() {
        enableButtonTask = new Runnable() {
            @Override
            public void run() {
                button.setEnabled(true);

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(seed.this);
                preferences.edit().putBoolean(BUTTON_ENABLED_KEY, true).apply();
            }
        };

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long specificTimeInMillis = cal.getTimeInMillis();

        handler.postDelayed(enableButtonTask, specificTimeInMillis - System.currentTimeMillis());
    }
}
