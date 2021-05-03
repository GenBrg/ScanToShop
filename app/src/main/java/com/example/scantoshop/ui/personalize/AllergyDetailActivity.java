package com.example.scantoshop.ui.personalize;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.scantoshop.R;

public class AllergyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_detail);

        final TextView textView = findViewById(R.id.textview);
        textView.setText("welcome");
    }
}