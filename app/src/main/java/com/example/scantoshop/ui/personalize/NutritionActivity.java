package com.example.scantoshop.ui.personalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.R;
import com.example.scantoshop.util.AppDatabase;

public class NutritionActivity extends AppCompatActivity {
    private Button btn;
    private AlertDialog.Builder adbuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition);

//        final TextView textView = findViewById(R.id.textview2);
//        textView.setText("nutrition");

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "MyDatabase")
                .createFromAsset("database/scan2shopDB.db")
                .allowMainThreadQueries().build();
        ProfileDAO profileDao = db.profileDAO();
        Profile user = profileDao.loadAllProfiles()[0];

        btn = (Button) findViewById(R.id.save);
        adbuilder = new AlertDialog.Builder(this);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                EditText fatEditText = findViewById(R.id.fat_edit_text);
                String fat = fatEditText.getText().toString();
                EditText sugarEditText = findViewById(R.id.sugar_edit_text);
                String sugar = sugarEditText.getText().toString();

                float fatValue = 0;
                try {
                    fatValue = Float.parseFloat(fat);
                    float sugarValue = 0;
                    try{
                        sugarValue = Float.parseFloat(sugar);
                        user.nutrient_1 = fatValue;
                        user.nutrient_2 = sugarValue;
                        profileDao.insertProfiles(user);
                    }catch(NumberFormatException ex){
                        adbuilder.setMessage("Please type in some valid values. Thank you!")
                                .setCancelable(false)
                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();
                                    }
                                });
                        //Creating dialog box
                        AlertDialog alert = adbuilder.create();
                        //Setting the title manually
                        alert.setTitle("Alert");
                        alert.show();
                    }
                } catch (NumberFormatException ex) {
                    adbuilder.setMessage("Please type in some valid values. Thank you!")
                            .setCancelable(false)
                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = adbuilder.create();
                    //Setting the title manually
                    alert.setTitle("Alert");
                    alert.show();
                }
                fatEditText.setText("");
                sugarEditText.setText("");
            }
        });
    }
}