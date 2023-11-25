package com.example.needcalendar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MultiDatePickerDialog extends DatePickerDialog{
    private List<OnDateSetListener> listeners = new ArrayList<>();

    public MultiDatePickerDialog(Context context, OnDateSetListener listener, int year, int month, int dayOfMonth) {
        super(context, listener, year, month, dayOfMonth);
        listeners.add(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout pickerParentLayout = (LinearLayout) getDatePicker().getParent();
        DatePicker datePicker = getDatePicker();
        pickerParentLayout.removeView(datePicker);

        for (int i = 1; i < 3; i++) {
            DatePicker newDatePicker = new DatePicker(getContext());
            newDatePicker.setCalendarViewShown(false);
            newDatePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                    new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            for (OnDateSetListener listener : listeners) {
                                listener.onDateSet(view, year, monthOfYear, dayOfMonth);
                            }
                        }
                    });

            pickerParentLayout.addView(newDatePicker);
        }
    }

    public void addOnDateSetListener(OnDateSetListener listener) {
        listeners.add(listener);
    }

    public void removeOnDateSetListener(OnDateSetListener listener) {
        listeners.remove(listener);
    }
}
