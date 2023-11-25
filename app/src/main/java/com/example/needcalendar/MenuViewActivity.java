package com.example.needcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MenuViewActivity extends AppCompatActivity {
    private Button loginButton;
    private Button fortuneButton;

    private Button seedButton;



    private boolean isLoggedIn = false; // 로그인 상태를 나타내는 변수
    private static final int LOGIN_REQUEST_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        loginButton = findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isLoggedIn) {
                    // 이미 로그인되어 있을 경우
                    // 로그아웃 로직을 수행하거나 원하는 작업을 수행합니다.
                    // 여기에서는 로그아웃 로직을 가정합니다.
                    performLogout();
                } else {
                    // 로그인 화면으로 전환
                    Intent loginIntent = new Intent(MenuViewActivity.this, login.class);
                    startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);
                }
            }

        });

        seedButton = findViewById(R.id.seed);
        seedButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent intent11 = new Intent(getApplicationContext(), seed.class);
                startActivity(intent11);

            }
        });

        fortuneButton = findViewById(R.id.cookie);
        fortuneButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                Intent intent10 = new Intent(getApplicationContext(), fortune_cookie.class);
                startActivity(intent10);

            }
        });

    }


    // 로그아웃 로직을 구현할 수 있습니다.
    private void performLogout() {
        // 로그아웃 작업을 수행합니다.
        // 예: 로그아웃 API 호출 또는 로그아웃 관련 작업 수행

        // 로그아웃 성공 시 로그인 상태를 변경하고 버튼 텍스트를 업데이트합니다.
        isLoggedIn = false;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {
            // 로그인 화면에서 로그인이 성공적으로 이루어졌을 경우
            isLoggedIn = true;

        }
    }

}



