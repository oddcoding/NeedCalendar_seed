package com.example.needcalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Help_btn extends AppCompatActivity{
    Button btn_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        btn_help = (Button) findViewById(R.id.btn_help);
        btn_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder d = new AlertDialog.Builder(Help_btn.this);

                d.setTitle("나의 씨앗보기");
                d.setMessage("출석체크로 성장한 나의 씨앗들을 볼 수 있습니다.");

                d.show();
            }
        });

    }


}
