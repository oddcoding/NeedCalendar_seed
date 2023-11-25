package com.example.needcalendar;

import static com.example.needcalendar.CalendarUtils.daysInMonthArray;
import static com.example.needcalendar.CalendarUtils.monthYearFromDate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener
{


    private ImageButton imageButton;


    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();

        // 월별 화면 연결
        imageButton = findViewById(R.id.monthbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent1);
            }
        });

        // 체크리스트 일정 저장화면
        imageButton = findViewById(R.id.checklistbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(getApplicationContext(), add_checklist.class);

                startActivity(intent2);
            }
        });


        // 체크리스트 화면 연결
        imageButton = findViewById(R.id.todaybutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(getApplicationContext(), TodayCalendar.class);

                startActivity(intent3);
            }
        });

        // 메뉴 화면 연결
        imageButton = findViewById(R.id.menuButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent4 = new Intent(getApplicationContext(), MenuViewActivity.class);

                startActivity(intent4);
            }
        });

        // 일정추가 화면 연결
        imageButton = findViewById(R.id.addbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(getApplicationContext(), add_schedule.class);

                startActivity(intent5);
            }
        });

        // 타이머 화면 연결
        imageButton = findViewById(R.id.timerbutton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent5 = new Intent(getApplicationContext(), timer.class);

                startActivity(intent5);
            }
        });


    }

    private void initWidgets()
    {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    // 다른 클래스 연결해서 월별화면 보여주는 메소드.
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        DBHelper dbHelper = new DBHelper(this); // 또는 해당 컨텍스트에 맞게 인스턴스화

        CalendarAdapter calendarAdapter = new CalendarAdapter(this,daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }


    // 이전 달 출력.
    public void previousMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    // 다음 달 출력.
    public void nextMonthAction(View view)
    {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    // 날짜 클릭시 해당 날 선택.
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date != null)
        {
            CalendarUtils.selectedDate = date;
            setMonthView();
        }
    }
}