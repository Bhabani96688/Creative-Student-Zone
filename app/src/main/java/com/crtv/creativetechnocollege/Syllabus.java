package com.crtv.creativetechnocollege;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.creativetechnocollege.R;

public class Syllabus extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View SYLLABUS = inflater.inflate(R.layout.fragment_syllabus, container, false);
        Button btn1 = SYLLABUS.findViewById(R.id.btn1);
        Button btn2 = SYLLABUS.findViewById(R.id.btn2);
        Button btn3 = SYLLABUS.findViewById(R.id.btn3);

        // Set OnClickListener for the first button
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.creativecollege.in/Syllabus/BSc-CS-(H)-2019-20%20NEW.pdf");
            }
        });

        // Set OnClickListener for the second button
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.creativecollege.in/Syllabus/BCA-2019-20.pdf");
            }
        });

        // Set OnClickListener for the third button
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUrl("https://www.creativecollege.in/Syllabus/BBA%20-%20syllabus.pdf");
            }
        });

        return SYLLABUS;
    }
    // Function to open a URL in a web browser
    private void openUrl(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(intent);
    }
}