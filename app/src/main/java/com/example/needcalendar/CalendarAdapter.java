package com.example.needcalendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private Context context;


    public CalendarAdapter(Context ctx, ArrayList<LocalDate> days, OnItemListener onItemListener) {
        this.context = ctx;
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (days.size() > 15) //month view
            layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        else // week view
            layoutParams.height = (int) parent.getHeight();

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        DatabaseHelper dbHelper = new DatabaseHelper(context); // 또는 해당 컨텍스트에 맞게 인스턴스화
        List<ListItem> items = dbHelper.getAllItems();  // 모든 아이템을 가져옴


        String startDate = "";  // 시작 날짜를 저장할 변수 초기화
        String endDate = "";        // 종료 날짜를 저장할 변수 초기화

        String eventText = ""; // 이벤트 텍스트를 저장할 변수 초기화

        for (int i = 0; i < items.size(); i++) {        // 모든 아이템을 순회하며 시작 날짜와 종료 날짜를 가져옴
            startDate = items.get(i).getStartDate();    // 시작 날짜를 가져옴
            endDate = items.get(i).getEndDate();    // 종료 날짜를 가져옴

            String formatStartDate = startDate.trim().replace(" / ", "-");  // 시작 날짜의 공백과 /를 -로 변경
            String formatEndDate = endDate.trim().replace(" / ", "-");      // 종료 날짜의 공백과 /를 -로 변경

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");     // 날짜 형식을 지정

            LocalDate sDate = LocalDate.parse(formatStartDate, inputFormatter);     // 시작 날짜를 LocalDate 형식으로 변환
            LocalDate eDate = LocalDate.parse(formatEndDate, inputFormatter);       // 종료 날짜를 LocalDate 형식으로 변환


            final LocalDate date = days.get(position);      // 해당 날짜를 가져옴
            if (date == null)
                holder.dayOfMonth.setText("");
            else {
                holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

                // 날짜 토요일, 일요일 색상 변경 커스텀.
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#00A5FF")); // 토요일은 파란색
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#DC6093")); // 일요일은 빨간색
                } else {
                    holder.dayOfMonth.setTextColor(Color.BLACK); // 그 외의 날짜는 검정색
                }

                if (date.equals(CalendarUtils.selectedDate))
                    holder.parentView.setBackgroundColor(Color.LTGRAY);



                if (date.isBefore(eDate) && date.isAfter(sDate)) {      // 시작 날짜와 종료 날짜 사이에 있는 날짜라면 이벤트 텍스트를 추가
                    eventText += items.get(i).getTitle() + "\n"; // 이벤트 텍스트를 추가
                }

                if (date.isEqual(sDate) || date.isEqual(eDate)) {       // 시작 날짜와 종료 날짜가 같다면 이벤트 텍스트를 추가
                    eventText += items.get(i).getTitle() + "\n"; // 이벤트 텍스트를 추가
                }
            }
        }

        if (items.isEmpty()) {      // 아이템이 없다면 이벤트 텍스트를 추가하지 않음
            final LocalDate date = days.get(position);
            if (date == null)
                holder.dayOfMonth.setText("");
            else {
                holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));

                // 날짜 토요일, 일요일 색상 변경 커스텀.
                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#00A5FF")); // 토요일은 파란색
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#DC6093")); // 일요일은 빨간색
                } else {
                    holder.dayOfMonth.setTextColor(Color.BLACK); // 그 외의 날짜는 검정색
                }


                if (date.equals(CalendarUtils.selectedDate))
                    holder.parentView.setBackgroundColor(Color.LTGRAY);
            }

        }
        holder.eventIndicator.setText(eventText); // 모든 이벤트 텍스트를 설정
    }



    @Override
    public int getItemCount() {
        return days.size();
    }

    public void getContents() {
        Log.i("##INFO", "getContents(): testmessaeg");
        DatabaseHelper dbHelper = new DatabaseHelper(context); // 또는 해당 컨텍스트에 맞게 인스턴스화
        List<ListItem> items = dbHelper.getAllItems();
        Log.i("##INFO", "getContents(): item.size = " + items.size());

        String startDate = "";
        String endDate = "";

        for (int i = 0; i < items.size(); i++) {
            startDate = items.get(i).getStartDate();
            endDate = items.get(i).getEndDate();
            Log.i("##INFO", "getContents(): startDate = " + startDate);
            Log.i("##INFO", "getContents(): endDate = " + endDate);
        }

    }


    public interface OnItemListener {

        void onItemClick(int position, LocalDate dayText);
    }
}