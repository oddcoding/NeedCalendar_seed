package com.example.needcalendar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class add_schedule extends AppCompatActivity {


    Button btn_start_date, btn_start_time ,btn_end_date, btn_end_time;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    MultiDatePickerDialog multiDatePickerDialog;
    TextView textView;

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private Button okButton;
    private DatabaseHelper dbHelper;
    private Button btn_help;
    private RecyclerView recyclerView1; // 체크박스가 체크된 경우의 리사이클러뷰
    private RecyclerView recyclerView2; // 체크박스가 해제된 경우의 리사이클러뷰
    private checklist adapter1; // recyclerView1의 어댑터
    private checklist adapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_schedule);

        btn_end_date = findViewById(R.id.btn_end_date);
        btn_end_time = findViewById(R.id.btn_end_time);
        btn_start_date = findViewById(R.id.btn_start_date);
        btn_start_time = findViewById(R.id.btn_start_time);
        textView = findViewById(R.id.textView);

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        okButton = findViewById(R.id.ok);

        // 데이터베이스 도우미를 초기화합니다.
        dbHelper = new DatabaseHelper(this);


        // OK 버튼에 클릭 리스너를 설정합니다.
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // EditText에서 텍스트를 가져옵니다.
                String title = editText1.getText().toString();
                String place = editText2.getText().toString();
                String memo = editText3.getText().toString();

                /**
                 * 수정
                 */
                long eventId = dbHelper.insertData(title, place, memo, btn_start_date.getText().toString(), btn_end_date.getText().toString());

                // 데이터베이스에 데이터를 추가합니다.
                // 추가 결과를 확인하고 필요한 작업을 수행합니다.
                if (eventId != -1) {
                    // 데이터가 성공적으로 추가됨
                    // 추가 후에 필요한 작업을 수행하세요
                    Toast.makeText(add_schedule.this, "일정이 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(add_schedule.this, MainActivity.class);
                    startActivity(intent); // 메인 화면으로 이동

                } else {
                    // 데이터 추가 실패
                    // 실패한 경우 필요한 작업을 수행하세요
                    Toast.makeText(add_schedule.this, "일정 추가에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }


//                editText1.setText("");
//                editText2.setText("");
//                editText3.setText("");

                TextView titleTextView = findViewById(R.id.editText1);
                TextView placeTextView = findViewById(R.id.editText2);
                TextView memoTextView = findViewById(R.id.editText3);

                title = editText1.getText().toString();
                place = editText2.getText().toString();
                memo = editText3.getText().toString();

                titleTextView.setText(title);
                placeTextView.setText(place);
                memoTextView.setText(memo);

            }

        });
    }

    public void onClick(View view) {

        if (view == btn_start_date) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            btn_start_date.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                        }
                    }, mYear,mMonth,mDay);
            datePickerDialog.show();
        }
        if (view == btn_start_time) {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btn_start_time.setText(String.format("%02d:%02d",hourOfDay, minute));

                        }
                    },mHour,mMinute, false);
            timePickerDialog.show();
        }


        if (view == btn_end_date) {
            final Calendar c = Calendar.getInstance();
            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            btn_end_date.setText(year + " / " + (month + 1) + " / " + dayOfMonth);
                        }
                    }, mYear,mMonth,mDay);
            datePickerDialog.show();
        }
        if (view == btn_end_time) {
            final Calendar c = Calendar.getInstance();
            int mHour = c.get(Calendar.HOUR);
            int mMinute = c.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            btn_end_time.setText(String.format("%02d:%02d",hourOfDay, minute));
                        }
                    },mHour,mMinute, false);
            timePickerDialog.show();
        }



    }
}



