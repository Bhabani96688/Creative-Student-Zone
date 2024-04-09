package com.crtv.creativetechnocollege;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Upcoming extends Fragment {

    private RecyclerView textContentList;
    private UpcomingAdapter adapter;

    public Upcoming() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);

        textContentList = view.findViewById(R.id.textContentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        textContentList.setLayoutManager(layoutManager);

        List<String> textContentListData = new ArrayList<>();
        adapter = new UpcomingAdapter((ArrayList<String>) textContentListData);
        textContentList.setAdapter(adapter);

        fetchUpcoming();

        return view;
    }

    private void fetchUpcoming() {
        String API_URL = "https://creativecollege.in/DNB/Upcoming.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> textContentListData = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String textContent = response.getString(i);
                                textContentListData.add(textContent);
                            }

                            adapter.setTextContentList(textContentListData);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error fetching Text", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
    }
}
