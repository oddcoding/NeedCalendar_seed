package com.example.needcalendar;//package com.example.needcalendar;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.text.Layout;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.content.ContextCompat;
//
//import com.example.needcalendar.R;
//
//import yuku.ambilwarna.AmbilWarnaDialog;
//
//public class Color extends AppCompatActivity {
//    Button button;
//    RelativeLayout relativeLayout;
//    int defaultColor;
//
//    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
//    @Override
//    protected void onCreate(Bundle saveInstanceState) {
//        super.onCreate(saveInstanceState);
//        setContentView(R.layout.activity_main);
//
//        button = findViewById(R.id.btn_color);
//        relativeLayout = findViewById(R.id.layout);
//
//        defaultColor = ContextCompat.getColor(Color.this, R.color.colorPrimary);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openColorPicker();
//
//            }
//        });
//    }
//
//    public void openColorPicker()
//    {
//        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
//            @Override
//            public void onCancel(AmbilWarnaDialog dialog) {
//
//            }
//
//            @Override
//            public void onOk(AmbilWarnaDialog dialog, int color) {
//                defaultColor = color;
//                relativeLayout.setBackgroundColor(defaultColor);
//
//            }
//        });
//        ambilWarnaDialog.show();
//    }
//}