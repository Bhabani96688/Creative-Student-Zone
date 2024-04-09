package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.creativetechnocollege.R;

public class About_us extends Fragment {

    Button more;

    ImageView shankarlink, souravlink, priyalink, subodhlink, subhasishlink, tusharlink, asutoshlink , nikhillink,chandralink,
            sushreelink,subhamlink
            ,technocrat;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        more = view.findViewById(R.id.more);

        shankarlink = view.findViewById(R.id.shankarlink);
        souravlink = view.findViewById(R.id.souravlink);
        priyalink = view.findViewById(R.id.priyalink);
        subodhlink = view.findViewById(R.id.subodhlink);
        subhasishlink = view.findViewById(R.id.subhasishlink);
        asutoshlink = view.findViewById(R.id.asutoshlink);
        nikhillink = view.findViewById(R.id.nikhillink);
        chandralink = view.findViewById(R.id.chandralink);
        sushreelink = view.findViewById(R.id.sushreelink);
        subhamlink = view.findViewById(R.id.subhamlink);


        tusharlink = view.findViewById(R.id.tusharlink);
        technocrat = view.findViewById(R.id.technocrat);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new About_More())
                        .addToBackStack(null).commit();
            }
        });

        shankarlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/shankarchjena");
            }
        });

        souravlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/sourav-ranjan-sahoo-583066246/");
            }
        });

        priyalink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/priyadarsan-pradhan-039496240");
            }
        });

        subodhlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/subodha-kumar-sahu-5ba257269/");
            }
        });

        subhasishlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/pradhan-subhasish/");
            }
        });

        tusharlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/tushar-kanta-swain-0b89ab263/");
            }
        });


        asutoshlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/asutosh-sahu-b3219426b?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
            }
        });
        nikhillink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/nikhil-nath-291aa0287");
            }
        });
        chandralink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/chandrakant-muduli-981b13295?utm_source=share&utm_campaign=share_via&utm_content=profile&utm_medium=android_app");
            }
        });
        sushreelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/sushree-smita");
            }
        });
        subhamlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLinkedin("https://www.linkedin.com/in/subham-moharana");
            }
        });













        technocrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.fragment_container,new Technocrat())
                        .addToBackStack(null).commit();
            }
        });



        return view;
    }

    private void openLinkedin(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}