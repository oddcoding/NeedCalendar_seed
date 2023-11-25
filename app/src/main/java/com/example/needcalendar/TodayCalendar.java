package com.example.needcalendar;
import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodayCalendar extends AppCompatActivity {

    Context context;
    private RecyclerView recyclerView2;
    private ChecklistAdapter adapter;
    private List<ListCheck> checklistItems;
    private DBcheck dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily);


        recyclerView2 = findViewById(R.id.list_rv);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBcheck(this);
        checklistItems = dbHelper.getAllItems();

        adapter = new ChecklistAdapter(checklistItems, context);
        recyclerView2.setAdapter(adapter);



    }
}
