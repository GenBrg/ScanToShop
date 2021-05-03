package com.example.scantoshop.ui.personalize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.scantoshop.R;

public class NutritionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

        final TextView textView = findViewById(R.id.textview2);
        textView.setText("nutrition");
    }
}