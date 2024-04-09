package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

public class Digital_Notice_Board extends Fragment implements ImageAdapter.OnImageClickListener {

    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout relativeLayout;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    Button btnUpcoming;
    BottomSheetDialog sheetDialog;
    private List<Bitmap> imageList = new ArrayList<>();
    private boolean isDataLoaded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_digital_notice_board, container, false);

        shimmerFrameLayout = view.findViewById(R.id.shimmerViewContainer);
        relativeLayout = view.findViewById(R.id.relativeLayout);
        btnUpcoming = view.findViewById(R.id.btnUpcoming);
        recyclerView = view.findViewById(R.id.imageRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false));
        imageAdapter = new ImageAdapter(imageList, this);
        recyclerView.setAdapter(imageAdapter);

        btnUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Upcoming());
            }
        });

        if (!isDataLoaded) {
            // Show shimmer animation only if data is not loaded yet
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
            fetchImages();
            isDataLoaded = true;
        }

        return view;
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
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                            imageList.add(bitmap);
                        }
                        // Notify adapter after adding images
                        imageAdapter.notifyDataSetChanged();
                        // Hide shimmer animation after data is loaded
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error if occurred
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onImageClick(Bitmap image) {
        ImageDetailFragment fragment = new ImageDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("clicked_image", image);
        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
