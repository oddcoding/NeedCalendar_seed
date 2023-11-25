package com.example.needcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton, registerButton, logoutButton;
    private DBHelper dbHelper;
    private long userId; // 로그인 사용자의 ID를 저장할 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.register);
        logoutButton = findViewById(R.id.logoutButton);

        dbHelper = new DBHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // 여기서 데이터베이스에서 이메일과 패스워드를 확인하는 코드를 추가합니다.
                boolean isValidUser = dbHelper.checkUser(email, password);

                if (isValidUser) {
                    // 메인 액티비티로 이동하거나 작업을 수행합니다.
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    // 로그인 성공 메시지 표시
                    Toast.makeText(login.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                } else {
                    // 로그인 실패
                    // 에러 메시지 표시 또는 작업을 수행합니다.
                    Toast.makeText(login.this, "로그인 실패. 이메일 또는 패스워드를 확인하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLogout();
            }
        });
    }

    private void performLogout() {
        // 여기에서 로그아웃 작업을 수행합니다.
        // 사용자 정보를 삭제하고 필요한 초기화 작업을 진행합니다.

        // 로그아웃 메시지 생성
        Toast.makeText(this, "로그아웃이 되었습니다", Toast.LENGTH_SHORT).show();

        // 메뉴 화면으로 이동
        Intent intent3 = new Intent(login.this, MenuViewActivity.class);
        startActivity(intent3);
        finish(); // 현재 액티비티를 종료하여 뒤로 가기 버튼으로 돌아갈 수 없게 합니다.
    }

}

