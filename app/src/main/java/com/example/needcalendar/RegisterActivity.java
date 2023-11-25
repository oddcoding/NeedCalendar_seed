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

                // 데이터베이스에 사용자 정보 추가
                boolean success = DBHelper.addUser(email, password, name);
                if (success) {
                    // 회원가입 성공
                    // 원하는 화면으로 이동하거나 작업 수행
                    // 예를 들어, 로그인 화면으로 이동
                    Intent intent = new Intent(RegisterActivity.this, login.class);
                    startActivity(intent);
                    finish();

                    // 회원가입 성공 메시지 표시
                    Toast.makeText(RegisterActivity.this, "회원가입이 정상적으로 처리되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    // 회원가입 실패
                    // 에러 메시지 표시 또는 작업 수행
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
                        // 이미 사용 중인 이메일인 경우
                        Toast.makeText(RegisterActivity.this, "이미 사용 중인 이메일입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        // 사용 가능한 이메일인 경우
                        Toast.makeText(RegisterActivity.this, "사용 가능한 이메일입니다.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // 이메일 형식이 유효하지 않은 경우
                    Toast.makeText(RegisterActivity.this, "유효한 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 이메일 형식이 유효한지 확인
    private boolean isEmailValid(String email) {
        // 여기에 이메일 형식을 확인하는 로직 추가
        // 예를 들어, 정규식을 사용하여 이메일 형식 검사
        // 정규식 패턴을 사용하여 이메일 형식을 확인합니다.
        // 이메일 형식에 대한 정확한 정규식 패턴은 다를 수 있습니다.
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
}
