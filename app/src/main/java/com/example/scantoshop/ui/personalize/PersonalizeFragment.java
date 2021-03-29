package com.example.scantoshop.ui.personalize;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.scantoshop.R;

public class PersonalizeFragment extends Fragment {

    private PersonalizeViewModel personalizeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        personalizeViewModel =
                ViewModelProviders.of(this).get(PersonalizeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_personalize, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        personalizeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}