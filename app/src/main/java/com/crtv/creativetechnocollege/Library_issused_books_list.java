package com.crtv.creativetechnocollege;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

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

public class Library_issused_books_list extends Fragment {

    SharedPreferences sharedPreferences;

    ListView rView;
    TextView Sid;
    String s;
    final String API = "https://creativecollege.in/app/lms_book_issued_list";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Issu_Book_List = inflater.inflate(R.layout.fragment_library_issused_books_list, container, false);

        rView = Issu_Book_List.findViewById(R.id.library_list);

        sharedPreferences = requireActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        s = sharedPreferences.getString("ID", null);
        Sid = Issu_Book_List.findViewById(R.id.sid);
        Sid.setText(s.toString());
        // Call the method to fetch and display the issued books
        fetchIssuedBooks(s);

        return Issu_Book_List;
    }

    private void fetchIssuedBooks(String studentId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(requireActivity());

        // Define the JSON array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API + "?roll=" + studentId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Process the JSON response and populate the ListView
                        processResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors
                Toast.makeText(requireActivity(), "Error fetching data", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(jsonArrayRequest);
    }

    private void processResponse(JSONArray jsonArray) {
        ArrayList<BookDetails> bookDetails = new ArrayList<>();

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject bookObject = jsonArray.getJSONObject(i);
                String bookName = bookObject.getString("book_name");
                String issueDate = bookObject.getString("issued_date");
                String bookId = bookObject.getString("book_id");
                String bookEdition = bookObject.getString("book_edition");
                String bookCategory = bookObject.getString("book_category");
                String bookPublisher = bookObject.getString("book_publisher");

                bookDetails.add(new BookDetails(issueDate,s, bookName, bookId, bookEdition, bookCategory, bookPublisher));
            }

            // Populate the ListView with the bookDetails using the adapter
            Issu_Book_Detail_Adapter adapter = new Issu_Book_Detail_Adapter(requireContext(), bookDetails);
            rView.setAdapter(adapter);

        } catch (JSONException e) {
            Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
