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



    private boolean isLoggedIn = false;
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
                    performLogout();
                } else {

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

    private void performLogout() {
        isLoggedIn = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_REQUEST_CODE && resultCode == RESULT_OK) {

            isLoggedIn = true;

        }
    }

}



