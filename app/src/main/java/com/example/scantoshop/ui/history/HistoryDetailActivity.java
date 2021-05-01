package com.example.scantoshop.ui.history;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.scantoshop.R;

public class HistoryDetailActivity extends AppCompatActivity {
    private Button btn3;
    private Button btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);

        btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener()
        {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count % 2 == 0) {
                    btn3.setActivated(false);;
                    count++;
                } else {
                    btn3.setActivated(true);;
                    count++;
                }

            }
        });


        btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(new View.OnClickListener()
        {
            int count = 0;
            @Override
            public void onClick(View view) {
                if (count % 2 == 0) {
                    btn4.setActivated(false);;
                    count++;
                } else {
                    btn4.setActivated(true);;
                    count++;
                }

            }
        });
    }
}