package com.crtv.creativetechnocollege;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.creativetechnocollege.R;

public class Library_Option extends Fragment {


    Button VIEW,OPEN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View LibraryOption = inflater.inflate(R.layout.fragment_library__option, container, false);

        VIEW = LibraryOption.findViewById(R.id.library_management);
        OPEN = LibraryOption.findViewById(R.id.e_library);

        VIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new Library_issused_books_list())
                        .addToBackStack(null).commit();

            }
        });

        OPEN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new Elibrary())
                        .addToBackStack(null).commit();
            }
        });
        return LibraryOption;
    }
}