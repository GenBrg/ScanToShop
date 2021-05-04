package com.example.scantoshop.ui.personalize;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.scantoshop.DAO.ProfileDAO;
import com.example.scantoshop.Entity.Profile;
import com.example.scantoshop.R;
import com.example.scantoshop.ui.history.HistoryDetailActivity;
import com.example.scantoshop.util.AppDatabase;

public class PersonalizeFragment extends Fragment {

    private PersonalizeViewModel personalizeViewModel;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TextView user_name;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalizeViewModel =
                ViewModelProviders.of(this).get(PersonalizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personalize, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
        user_name = root.findViewById(R.id.user_name);
        AppDatabase db = AppDatabase.getInstance(getContext());
        ProfileDAO profileDao = db.profileDAO();
        Profile user = profileDao.loadAllProfiles()[0];
        user_name.setText(user.uname);
//        personalizeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        btn1 = (Button) root.findViewById(R.id.allergy_button);
        btn1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AllergyDetailActivity.class);
                startActivity(intent);
            }
        });
        btn2 = (Button) root.findViewById(R.id.nutrition_button);
        btn2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), NutritionActivity.class);
                startActivity(intent);
            }
        });
        btn3 = (Button) root.findViewById(R.id.change_name_button);
        btn3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                EditText editText = root.findViewById(R.id.edit_text);
                String new_name = editText.getText().toString();
                user.uname = new_name;
                profileDao.insertProfiles(user);
                user_name.setText(new_name);
            }
        });
        return root;
    }
}