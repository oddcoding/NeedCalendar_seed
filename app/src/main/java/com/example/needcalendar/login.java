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
    private long userId;


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

                boolean isValidUser = dbHelper.checkUser(email, password);

                if (isValidUser) {
                    Intent intent = new Intent(login.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(login.this, "로그인 성공!", Toast.LENGTH_SHORT).show();
                } else {
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

        Toast.makeText(this, "로그아웃이 되었습니다", Toast.LENGTH_SHORT).show();

        Intent intent3 = new Intent(login.this, MenuViewActivity.class);
        startActivity(intent3);
        finish();
    }

}

