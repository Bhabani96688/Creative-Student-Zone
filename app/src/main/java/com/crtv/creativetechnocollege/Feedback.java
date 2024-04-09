package com.crtv.creativetechnocollege;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends Fragment implements AdapterView.OnItemSelectedListener {

    TextView textView;
    String subject, id, name, course;
    Button button;
    Spinner spinner1, spinner2, spinner3, spinner4, spinner5, spinner6, spinner7, spinner8, spinner9, spinner10;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);

        textView = rootView.findViewById(R.id.course_set);
        progressBar = rootView.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            subject = bundle.getString("tittle");
            id = bundle.getString("id");
            name = bundle.getString("name");
            course = bundle.getString("course");
        }

        button = rootView.findViewById(R.id.button);

        textView.setText(subject);

        spinner1 = rootView.findViewById(R.id.spinner1);
        spinner2 = rootView.findViewById(R.id.spinner2);
        spinner3 = rootView.findViewById(R.id.spinner3);
        spinner4 = rootView.findViewById(R.id.spinner4);
        spinner5 = rootView.findViewById(R.id.spinner5);
        spinner6 = rootView.findViewById(R.id.spinner6);
        spinner7 = rootView.findViewById(R.id.spinner7);
        spinner8 = rootView.findViewById(R.id.spinner8);
        spinner9 = rootView.findViewById(R.id.spinner9);
        spinner10 = rootView.findViewById(R.id.spinner10);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(rootView.getContext(), R.array.NUMBERS, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setAdapter(adapter);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setAdapter(adapter);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setAdapter(adapter);
        spinner5.setOnItemSelectedListener(this);
        spinner6.setAdapter(adapter);
        spinner6.setOnItemSelectedListener(this);
        spinner7.setAdapter(adapter);
        spinner7.setOnItemSelectedListener(this);
        spinner8.setAdapter(adapter);
        spinner8.setOnItemSelectedListener(this);
        spinner9.setAdapter(adapter);
        spinner9.setOnItemSelectedListener(this);
        spinner10.setAdapter(adapter);
        spinner10.setOnItemSelectedListener(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if (isNetworkAvailable()) {
                    feedback();
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Handle spinner item selection if needed
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle case where nothing is selected in the spinner
    }

    private void feedback() {
        String value1 = spinner1.getSelectedItem() != null ? spinner1.getSelectedItem().toString() : "";
        String value2 = spinner2.getSelectedItem() != null ? spinner2.getSelectedItem().toString() : "";
        String value3 = spinner3.getSelectedItem() != null ? spinner3.getSelectedItem().toString() : "";
        String value4 = spinner4.getSelectedItem() != null ? spinner4.getSelectedItem().toString() : "";
        String value5 = spinner5.getSelectedItem() != null ? spinner5.getSelectedItem().toString() : "";
        String value6 = spinner6.getSelectedItem() != null ? spinner6.getSelectedItem().toString() : "";
        String value7 = spinner7.getSelectedItem() != null ? spinner7.getSelectedItem().toString() : "";
        String value8 = spinner8.getSelectedItem() != null ? spinner8.getSelectedItem().toString() : "";
        String value9 = spinner9.getSelectedItem() != null ? spinner9.getSelectedItem().toString() : "";
        String value10 = spinner10.getSelectedItem() != null ? spinner10.getSelectedItem().toString() : "";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://creativecollege.in/Feedback/feedback.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                        getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new Home()).commit();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                String errorMessage = (error.networkResponse != null) ? new String(error.networkResponse.data) : "Unknown error";
                Toast.makeText(getActivity(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("question1", value1);
                params.put("question2", value2);
                params.put("question3", value3);
                params.put("question4", value4);
                params.put("question5", value5);
                params.put("question6", value6);
                params.put("question7", value7);
                params.put("question8", value8);
                params.put("question9", value9);
                params.put("question10", value10);
                params.put("id", id);
                params.put("subject", subject.trim());
                params.put("course", course);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}
