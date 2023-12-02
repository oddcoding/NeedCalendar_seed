package com.example.needcalendar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText, nameEditText;
    private Button registerButton,validateButton;;
    private DBHelper DBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        validateButton = findViewById(R.id.validateButton);

        emailEditText = findViewById(R.id.register_email);
        passwordEditText = findViewById(R.id.register_password);
        nameEditText = findViewById(R.id.register_name);
        registerButton = findViewById(R.id.btn_register);

        DBHelper = new DBHelper(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String name = nameEditText.getText().toString().trim();
                validateButton = findViewById(R.id.validateButton);

                boolean success = DBHelper.addUser(email, password, name);
                if (success) {
                    Intent intent = new Intent(RegisterActivity.this, login.class);
                    startActivity(intent);
                    finish();

                    Toast.makeText(RegisterActivity.this, "회원가입이 정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();

                if (isEmailValid(email)) {
                    boolean isEmailTaken = DBHelper.isEmailTaken(email);
                    if (isEmailTaken) {
                        Toast.makeText(RegisterActivity.this, "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
