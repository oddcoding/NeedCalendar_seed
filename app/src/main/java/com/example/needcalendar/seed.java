package com.example.needcalendar;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class seed extends AppCompatActivity {
    private Button button;
    private ImageView imageView;

    private int[] imageArray = {R.drawable.red, R.drawable.yellow, R.drawable.cherry, R.drawable.heart};
    private int currentImageIndex = 0;
    private int imageRepetitionCount = 0;
    private boolean isButtonEnabled = true;

    private static final String IMAGE_INDEX_KEY = "image_index";
    private static final String LAST_BUTTON_PRESS_DATE_KEY = "last_button_press_date";
    private static final String BUTTON_ENABLED_KEY = "button_enabled";
    private static final long MILLIS_IN_A_DAY = 24 * 60 * 60 * 1000; // 24시간을 밀리초로 표현

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seed);

        button = findViewById(R.id.seedButton);
        imageView = findViewById(R.id.seedIcon);

        // SharedPreferences에서 저장된 이미지 불러오기
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        currentImageIndex = preferences.getInt(IMAGE_INDEX_KEY, 0);
        imageView.setImageResource(imageArray[currentImageIndex]);

        // 마지막 버튼 누른 날짜를 가져옴
        long lastButtonPressDate = preferences.getLong(LAST_BUTTON_PRESS_DATE_KEY, 0);

        // 현재 날짜와 마지막 버튼 누른 날짜를 비교하여 버튼 상태 설정
        if (isOneDayPassed(lastButtonPressDate)) {
            isButtonEnabled = true; // 버튼이 활성화됨
        } else {
            disableButtonForOneDay(); // 버튼을 24시간 동안 비활성화
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

                    // 변경된 이미지 인덱스를 SharedPreferences에 저장
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putInt(IMAGE_INDEX_KEY, currentImageIndex);
                    editor.putLong(LAST_BUTTON_PRESS_DATE_KEY, Calendar.getInstance().getTimeInMillis());
                    editor.putBoolean(BUTTON_ENABLED_KEY, isButtonEnabled);
                    editor.apply();
                }

                disableButtonForOneDay(); // 버튼을 24시간 동안 비활성화
            }
        });
    }

    private void setImageAndIncrementCount() {
        imageView.setImageResource(imageArray[currentImageIndex]);
        imageRepetitionCount++;
    }

    private void disableButtonForOneDay() {
        button.setEnabled(false);
        isButtonEnabled = false;
        button.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));

        // 24시간 뒤에 버튼을 자동으로 활성화하기 위해 Handler를 사용
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isButtonEnabled = true;
                button.setEnabled(true);

                // 버튼 상태를 저장
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(seed.this);
                preferences.edit().putBoolean(BUTTON_ENABLED_KEY, true).apply();
            }
        }, MILLIS_IN_A_DAY);
    }

    private boolean isOneDayPassed(long lastButtonPressDate) {
        if (lastButtonPressDate == 0) {
            return true; // 마지막 버튼 누른 날짜가 없으면 24시간이 지났다고 간주
        }

        long currentTime = Calendar.getInstance().getTimeInMillis();
        // 현재 시간과 마지막 버튼 누른 시간을 비교하여 24시간이 지났는지 확인
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
            disableButtonForOneDay(); // 저장된 상태에 따라 버튼 비활성화
        }
    }
}
