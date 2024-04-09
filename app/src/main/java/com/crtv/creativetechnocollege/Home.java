package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Home extends Fragment {

    // LinearLayout widgets
    LinearLayout result, syllabus, LMS, issuBook, E_Library, Contacts, About, Feedback,Attendances;
    LinearLayout profile;
    CardView notice, suggestion;
    TextView Name;
    private Home_img_Adapter imageAdapter;
    private ArrayList<String> textContentList;

    private List<Bitmap> imageList = new ArrayList<>();
    SharedPreferences sharedPreferences;

    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        result = view.findViewById(R.id.result);
        syllabus = view.findViewById(R.id.syllabus);
        LMS = view.findViewById(R.id.lms);
//        issuBook = view.findViewById(R.id.book_issu);
//        E_Library = view.findViewById(R.id.elibrary);
        Contacts = view.findViewById(R.id.contacts);
        About = view.findViewById(R.id.about);
        Feedback = view.findViewById(R.id.feedback);
        notice = view.findViewById(R.id.notice);
        suggestion = view.findViewById(R.id.suggestion);
        profile = view.findViewById(R.id.profile);
        Name = view.findViewById(R.id.name);
        Attendances = view.findViewById(R.id.attend);

        sharedPreferences = requireActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("ID", null);

        request(s);

        profile.setOnClickListener(v -> openFragment(new Profile()));

        notice.setOnClickListener(v -> openFragment(new Digital_Notice_Board()));

        suggestion.setOnClickListener(v -> openFragment(new Suggestion_Box()));

        result.setOnClickListener(v -> openFragment(new Result()));

        syllabus.setOnClickListener(v -> openFragment(new Syllabus()));

        LMS.setOnClickListener(v -> openFragment(new Library_Option()));

//        issuBook.setOnClickListener(v -> openFragment(new Library_issused_books_list()));
//
//        E_Library.setOnClickListener(v -> openFragment(new Elibrary()));

        Contacts.setOnClickListener(v -> openFragment(new Contact_Us()));

        About.setOnClickListener(v -> openFragment(new About_us()));

        Feedback.setOnClickListener(v -> openFragment(new Feedback_input_data()));

        Attendances.setOnClickListener(v -> openFragment(new Attendance()));

        recyclerView = view.findViewById(R.id.horizontalRecyclerView);

        imageAdapter = new Home_img_Adapter(imageList, new Home_img_Adapter.OnImageClickListener() {
            @Override
            public void onImageClick(int position) {
                // Handle the click event, open the Digital Notice Board fragment
                openFragment(new Digital_Notice_Board());
            }
        });

        recyclerView.setAdapter(imageAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        fetchImages();

        return view;
    }

    // Method to open a new fragment
    private void openFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void request(String SID) {
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://creativecollege.in/app/profile.php?id=" + SID,
                null,
                response -> {
                    try {
                        String name = "Hi, " + response.getString("NAME")+" 👋";
                        Name.setText(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String data = new String(error.networkResponse.data);

                    } else {

                    }
                }
        );

        queue.add(jsonObjectRequest);
    }

    private void fetchImages() {
        String url = "https://creativecollege.in/DNB/retrive.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] imageStrings = response.split("\\r?\\n");
                        ListIterator<String> iterator = Arrays.asList(imageStrings).listIterator(imageStrings.length);

                        while (iterator.hasPrevious()) {
                            String imageString = iterator.previous();
                            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                            Bitmap originalBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                            // Determine image orientation and rotate if in landscape
                            Bitmap rotatedBitmap = determineAndRotateBitmap(originalBitmap);

                            imageList.add(rotatedBitmap);
                        }

                        imageAdapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error fetching images", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(getContext()).add(stringRequest);
    }

    private Bitmap determineAndRotateBitmap(Bitmap originalBitmap) {
        if (originalBitmap.getWidth() > originalBitmap.getHeight()) {
            // Landscape orientation
            return rotateBitmap(originalBitmap, 90);
        } else {
            // Portrait orientation or square
            return originalBitmap;
        }
    }

    private Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
