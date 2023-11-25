package com.example.needcalendar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AmbilWarnaDialog {


    public AmbilWarnaDialog(ColorPicker colorPicker, int tColor, OnAmbilWarnaListener onAmbilWarnaListener) {
    }

    public AmbilWarnaDialog(add_schedule.ColorPicker colorPicker, int tColor, OnAmbilWarnaListener onAmbilWarnaListener) {
    }


    public interface OnAmbilWarnaListener {
        void onCancel(AmbilWarnaDialog dialog);

        void onOk(AmbilWarnaDialog dialog, int color);
    }
    public class ColorPicker extends AppCompatActivity {
        private final String TAG=this.getClass().getSimpleName();
        private int tColor; // 직전 선택한 색상
        private Button btnColorPicker;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.add_schedule);


            btnColorPicker = (Button)findViewById(R.id.btn_color);
            btnColorPicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { Log.e(TAG,"choice() onClick");
                    openColorPicker();
                }
            });


        }


        private void openColorPicker() {
            AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, tColor, new OnAmbilWarnaListener() {
                @Override
                public void onCancel(AmbilWarnaDialog dialog) {
                }

                @Override
                public void onOk(AmbilWarnaDialog dialog, int color) {

                    tColor = color; // 직전 선택한 색상

                    // int to String
                    String hexColor = Integer.toHexString(color).substring(2);

                    // 투명도 조절
                    hexColor = "#6F"+hexColor;
                    Log.e(TAG,"hexColor:"+hexColor); }
            })
                    ;}}}// 9자리

// 원하는 곳 색상 변경
//  btnColorPicker.setBackgroundColor(Color.parseColor(hexColor));

//   }