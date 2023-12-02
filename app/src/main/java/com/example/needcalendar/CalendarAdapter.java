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
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        List<ListItem> items = dbHelper.getAllItems();


        String startDate = "";
        String endDate = "";

        String eventText = "";

        for (int i = 0; i < items.size(); i++) {
            startDate = items.get(i).getStartDate();
            endDate = items.get(i).getEndDate();

            String formatStartDate = startDate.trim().replace(" / ", "-");
            String formatEndDate = endDate.trim().replace(" / ", "-");

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-M-d");

            LocalDate sDate = LocalDate.parse(formatStartDate, inputFormatter);
            LocalDate eDate = LocalDate.parse(formatEndDate, inputFormatter);


            final LocalDate date = days.get(position);
            if (date == null)
                holder.dayOfMonth.setText("");
            else {
                holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));


                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#00A5FF"));
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#DC6093"));
                } else {
                    holder.dayOfMonth.setTextColor(Color.BLACK);
                }

                if (date.equals(CalendarUtils.selectedDate))
                    holder.parentView.setBackgroundColor(Color.LTGRAY);



                if (date.isBefore(eDate) && date.isAfter(sDate)) {
                    eventText += items.get(i).getTitle() + "\n";
                }

                if (date.isEqual(sDate) || date.isEqual(eDate)) {
                    eventText += items.get(i).getTitle() + "\n";
                }
            }
        }

        if (items.isEmpty()) {
            final LocalDate date = days.get(position);
            if (date == null)
                holder.dayOfMonth.setText("");
            else {
                holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));


                DayOfWeek dayOfWeek = date.getDayOfWeek();
                if (dayOfWeek == DayOfWeek.SATURDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#00A5FF"));
                } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                    holder.dayOfMonth.setTextColor(Color.parseColor("#DC6093"));
                } else {
                    holder.dayOfMonth.setTextColor(Color.BLACK);
                }


                if (date.equals(CalendarUtils.selectedDate))
                    holder.parentView.setBackgroundColor(Color.LTGRAY);
            }

        }
        holder.eventIndicator.setText(eventText);
    }



    @Override
    public int getItemCount() {
        return days.size();
    }

    public void getContents() {
        Log.i("##INFO", "getContents(): testmessaeg");
        DatabaseHelper dbHelper = new DatabaseHelper(context);
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