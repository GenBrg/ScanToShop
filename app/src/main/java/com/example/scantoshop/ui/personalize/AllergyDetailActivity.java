package com.example.scantoshop.ui.personalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;

public class AllergyDetailActivity extends AppCompatActivity {
    private CheckBox milkView;
    private CheckBox eggView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy_detail);

//        final TextView textView = findViewById(R.id.textview);
//        textView.setText("welcome");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MyDatabase")
                .createFromAsset("database/scan2shopDB.db")
                .allowMainThreadQueries().build();
        ProfileDAO profileDao = db.profileDAO();
        Profile user = profileDao.loadAllProfiles()[0];

        milkView = (CheckBox)findViewById(R.id.milk_checkbox);
        if(user.allergy_1){
            milkView.setChecked(true);
        }else{
            milkView.setChecked(false);
        }
        milkView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    user.allergy_1 = true;
                    profileDao.insertProfiles(user);
                } else {
                    user.allergy_1 = false;
                    profileDao.insertProfiles(user);
                }
            }
        });

        eggView = (CheckBox)findViewById(R.id.egg_checkbox);
        if(user.allergy_2){
            eggView.setChecked(true);
        }else{
            eggView.setChecked(false);
        }
        eggView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CompoundButton) view).isChecked()){
                    user.allergy_2 = true;
                    profileDao.insertProfiles(user);
                } else {
                    user.allergy_2 = false;
                    profileDao.insertProfiles(user);
                }
            }
        });
    }
}