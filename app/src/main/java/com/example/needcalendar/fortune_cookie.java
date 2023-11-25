package com.example.needcalendar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class fortune_cookie extends AppCompatActivity {

    Button button;
    TextView textView;

    List<String> wording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fortune_cookie);

        button=findViewById(R.id.cookie_button);
        textView=findViewById(R.id.textView);

        wording = new ArrayList<>();

        wording.add("그동안 노력한 결과가 빛을 보이는 시기입니다. 서두르지 말고 기다려 보세요!");
        wording.add("과감한 결단이 행운을 높이는 시기입니다. 묵은 것은 버리고 새로운 것을 시도해보세요!");
        wording.add("마음 먹은대로 되리라!");
        wording.add("물 속에 있던 용이 비로소 승천하는 격. 명예와 재물운이 상승합니다!");
        wording.add("가지고 싶은 것을 손에 쥐고 이루고 싶은 꿈을 이루게 됩니다.");
        wording.add("미뤄둔 일을 해결합시다. 어렵던 일도 오늘은 술술 풀릴거예요!");
        wording.add("당신을 사랑하는 사람이 늘어납니다!");
        wording.add("행운입니다! 오늘은 행복할거예요!");
        wording.add("바람이 불지 않으면 노를 저어요!");
        wording.add("노랗게 익은 벼를 바라보는 격이니, 수고한 보람을 느끼게 되는 하루가 됩니다!");

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Random r= new Random();

                String randomValue= wording.get(r.nextInt(wording.size()));

                textView.setText(randomValue);
            }
        });



    }
}
