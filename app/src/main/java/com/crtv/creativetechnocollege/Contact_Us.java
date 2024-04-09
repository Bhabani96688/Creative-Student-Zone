package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.motion.widget.DesignTool;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Contact_Us extends Fragment implements View.OnClickListener {



    RecyclerView rView;
    contactus_Adapter adapter;
    ArrayList<ContactInfo> contactList=new ArrayList<>();
    RequestQueue requestQueue;
    public static final String API= "https://creativecollege.in/Feedback/Contact.php";


    Button locate_office, locate_college;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact__us, container, false);


        //Recycler View
        rView=view.findViewById(R.id.RviewContactUs);
        rView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //set adapter
        adapter=new contactus_Adapter(getActivity(),contactList);
        rView.setAdapter(adapter);

        requestQueue= Volley.newRequestQueue(getActivity());

        //data fetch from the json file
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, API, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 1; i < response.length(); i++) {
                        JSONObject item = response.getJSONObject(i);
                        String name =item.getString("Name");
                        String designation =item.getString("Designation");
                        String Contact= item.getString("Contact No");

                        ContactInfo contactInfo=new ContactInfo(name, designation,Contact);
                        contactList.add(contactInfo);
                    }

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("JSON Parsing Error", "Error: " + e.getMessage());
                    // Handle the JSON parsing error here
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle network errors or API errors
                Log.e("API Error", "Error: " + error.toString());
                Toast.makeText(getActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();

            }
        });






        locate_office = view.findViewById(R.id.locate_office);
        locate_college = view.findViewById(R.id.locate_college);

        locate_office.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOfficeMap();
            }
        });

        locate_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCollegeMap();
            }
        });

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
        return view;
    }

    private void openOfficeMap() {
        Uri uri1 = Uri.parse("geo:0, 0?q=Creative Techno College Tamrit colony Angul Odisha");
        Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
        intent1.setPackage("com.google.android.apps.maps");
        startActivity(intent1);
    }

    private void openCollegeMap() {
        Uri uri = Uri.parse("geo:0, 0?q=Creative Techno College Baluakata Odisha");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

    }




}