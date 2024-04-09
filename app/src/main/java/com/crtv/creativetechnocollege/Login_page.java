package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crtv.creativetechnocollege.Home;
import com.example.creativetechnocollege.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.HashMap;
import java.util.Map;

public class Login_page extends Fragment {


    EditText editText1, editText2;
    Button button;
    ShimmerFrameLayout shimmerFrameLayout;
    LinearLayout dataLayout;
    RelativeLayout data;

    SharedPreferences sharedPreferences;
    boolean passVisible;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_page, container, false);


        editText1 = view.findViewById(R.id.userid);
        editText2 = view.findViewById(R.id.userpwd);
        button = view.findViewById(R.id.ok);

        shimmerFrameLayout = view.findViewById(R.id.data_view);
        dataLayout = view.findViewById(R.id.data_view1);
        data = view.findViewById(R.id.data);

        // Initialize the passVisible flag to true (password is initially visible)
        passVisible = true;

        // Set a click listener to toggle password visibility
        editText2.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (motionEvent.getRawX() >= (editText2.getRight() - editText2.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        togglePasswordVisibility();
                        return true;
                    }
                }
                return false;
            }
        });

        sharedPreferences = getActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);

        // Check if the user is already logged in
        if (isUserLoggedIn()) {
            navigateToHomeFragment();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = editText1.getText().toString().trim();
                String userPwd = editText2.getText().toString().trim();

                showLoading();

                // Perform login
                login(userId, userPwd);
            }
        });

        return view;
    }

    // Function to toggle password visibility
    private void togglePasswordVisibility() {
        passVisible = !passVisible;
        if (passVisible) {
            editText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            editText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        // Move the cursor to the end of the text
        int selection = editText2.getText().length();
        editText2.setSelection(selection);
    }

    // Function to check if the user is already logged in
    private boolean isUserLoggedIn() {
        String type = sharedPreferences.getString("ID", "");
        return !type.isEmpty();
    }

    // Function to show the loading UI
    private void showLoading() {
        data.setVisibility(View.INVISIBLE);
        dataLayout.setVisibility(View.INVISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    // Function to navigate to the home fragment
    private void navigateToHomeFragment() {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, new Home());
        fragmentTransaction.commit();
    }

    private void login(String userId, String userPwd) {
        if (userId.isEmpty()) {
            Toast.makeText(getActivity(), "Enter ID", Toast.LENGTH_SHORT).show();
            hideLoading();
            return;
        } else if (userPwd.isEmpty()) {
            Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_SHORT).show();
            hideLoading();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://creativecollege.in/Feedback/login.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("found")) {
                            // Save user login data
                            saveUserLoginData(userId, userPwd);
                            hideLoading();
                            Toast.makeText(getActivity(), "LOGIN SUCCESS", Toast.LENGTH_SHORT).show();
                            navigateToHomeFragment();
                        } else if (response.equalsIgnoreCase("not found")) {
                            hideLoading();
                            Toast.makeText(getActivity(), "LOGIN FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideLoading();
                Toast.makeText(getActivity(), "CONNECTION FAILED", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userId);
                params.put("pwd", userPwd);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    // Function to hide loading UI
    private void hideLoading() {
        data.setVisibility(View.VISIBLE);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.INVISIBLE);
    }

    // Function to save user login data
    private void saveUserLoginData(String userId, String userPwd) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ID", userId);
        editor.putString("Password", userPwd);
        editor.apply();
    }
}
