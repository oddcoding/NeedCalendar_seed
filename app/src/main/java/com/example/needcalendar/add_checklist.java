package com.example.needcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class add_checklist extends AppCompatActivity {

    EditText checkTitle;
    EditText checkMemo;
    EditText checkPlace;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_checklist);

        checkTitle = findViewById(R.id.check_title);
        checkMemo = findViewById(R.id.check_memo);
        checkPlace = findViewById(R.id.check_place);
        saveButton = findViewById(R.id.check_ok);
        CheckBox checkBox = findViewById(R.id.myCheckBox);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = checkTitle.getText().toString();
                String memo = checkMemo.getText().toString();
                String place = checkPlace.getText().toString();
                boolean isCheckBox = checkBox.isChecked();

                // DBcheck 클래스를 사용하여 데이터베이스에 데이터 저장
                DBcheck dbcheck = new DBcheck(add_checklist.this); // 액티비티 컨텍스트 전달
                long result = dbcheck.insertData(title, place, memo, isCheckBox);

                if (result != -1) {
                    // 저장 성공
                    Toast.makeText(add_checklist.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    // 원하는 처리 추가

                    Intent intent = new Intent(add_checklist.this, TodayCalendar.class);
                    startActivity(intent);

                } else {
                    // 저장 실패
                    Toast.makeText(add_checklist.this, "저장에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}