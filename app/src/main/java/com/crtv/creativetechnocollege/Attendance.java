package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.creativetechnocollege.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Attendance extends Fragment {

    ListView listView;
    Attendance_Adapter adapter;
    List<Pair<String, String>> dataList;
    TextView AName,AID,Present,Absent,Clases,Percentage,Show_Date;

    SharedPreferences sharedPreferences;
    CardView Callender,Ok;
    DatePicker datePicker;
    String student_id;
    String Course,Semester,table;
//    int countP = 0;
//    int countA = 0;
    String filter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View attendance = inflater.inflate(R.layout.fragment_attendance, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("Data", Context.MODE_PRIVATE);
        student_id = sharedPreferences.getString("ID", null);

        Callender = attendance.findViewById(R.id.calender);
        Callender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DIALOG();
            }
        });

//        request(student_id,filter);
        AName = attendance.findViewById(R.id.Asname);
        AID = attendance.findViewById(R.id.Asid);
        Present = attendance.findViewById(R.id.totalPreset);
        Absent = attendance.findViewById(R.id.totalAbsent);
        Clases = attendance.findViewById(R.id.totalClases);
        Percentage = attendance.findViewById(R.id.percentage);
        Show_Date = attendance.findViewById(R.id.month);
        Show_Date.setText("All");

            listView = attendance.findViewById(R.id.rectangles);  // Replace R.id.rectangles with the ID of your ListView
            dataList = new ArrayList<>();
            adapter = new Attendance_Adapter(getContext(), dataList);
            listView.setAdapter(adapter);
            requests(student_id,"");
//        Toast.makeText(getContext(), "Table Name: "+table, Toast.LENGTH_SHORT).show();


        return attendance;
    }

    void DIALOG(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.date_peaker);
        dialog.show();

        Ok = dialog.findViewById(R.id.dt);
        datePicker = dialog.findViewById(R.id.datepicker);

        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mm = String.valueOf(datePicker.getMonth()+1);
                String yyyy = String.valueOf(datePicker.getYear());
                int l = mm.length();
                String F_Date = "";
                if(l==2) {
                    F_Date = mm + "_" + yyyy;
                }
                else {
                    F_Date = "0"+mm + "_" + yyyy;
                }
                filter = "&month="+F_Date;
                Show_Date.setText(F_Date);
                dialog.cancel();
//                Toast.makeText(getContext(), filter, Toast.LENGTH_SHORT).show();
                requests(student_id,filter);
            }
        });


    }


    public void requests(String SID,String Filter) {
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                "https://creativecollege.in/app/profile.php?id=" + SID,
                null,
                response -> {
                    try {
                        Semester = response.getString("SEMESTER");
                        Course = response.getString("COURSE");

//                        Toast.makeText(getContext(), "=> "+Semester+Course, Toast.LENGTH_SHORT).show();
                        request("bsc_cs_3rd_attendance",Filter);
//                        Table(Semester,Course);

                        if(Course.equals("BCA") && Semester.equals("4th")||Semester.equals("3rd")){
                            request("bca_2nd_attendance",Filter);
                        } else if (Course.equals("BCA") && Semester.equals("5th")||Semester.equals("6th")) {
                            request("bca_3rd_attendance",Filter);
                        } else if (Course.equals("BCA") && Semester.equals("1st")||Semester.equals("2nd")) {
                            request("bca_1st_attendance",Filter);
                        } else if (Course.equals("BSC-CS(H)") && Semester.equals("4th")||Semester.equals("3rd")){

                            request("bsc_cs_2nd_attendance",Filter);
                        } else if (Course.equals("BSC-CS(H)") && Semester.equals("5th")||Semester.equals("6th")) {
                            table = "bsc_cs_3rd_attendance";
                            request("bsc_cs_2nd_attendance",Filter);
//                            Toast.makeText(getContext(), "Sucess Table Name"+table, Toast.LENGTH_SHORT).show();
                        } else if (Course.equals("BSC-CS(H)") && Semester.equals("1st")||Semester.equals("2nd")) {
                            request("bsc_cs_1st_attendance",Filter);
                        } else if (Course.equals("BBA") && Semester.equals("4th")||Semester.equals("3rd")){

                            request("bba_2nd_attendance",Filter);
                        } else if (Course.equals("BBA") && Semester.equals("5th")||Semester.equals("6th")) {
                            request("bba_3rd_attendance",Filter);
                        } else if (Course.equals("BBA") && Semester.equals("1st")||Semester.equals("2nd")) {
                            request("bba_1st_attendance",Filter);
                        }


//                        Toast.makeText(getContext(), "xxx"+Semester+" "+Course, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

//                    Toast.makeText(getContext(), "xxx"+Semester+" "+Course, Toast.LENGTH_SHORT).show();
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


    public void request(String table,String Filter) {


        String url = "https://creativecollege.in/Flutter/Attendance/month_retrieve.php?table="+table+"&student_id="+student_id+Filter;

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if (response != null) {
                                int countP = 0;
                                int countA = 0;
                                int f=0;
                                dataList.clear();  // Clear existing data
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject jsonObject = response.getJSONObject(i);

                                    Iterator<String> keys = jsonObject.keys();
                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        String value = jsonObject.getString(key);
                                        if(key.equals("sl")||key.equals("name")||key.equals("student_id")){

                                            AName.setText(jsonObject.getString("name"));
                                            AID.setText(jsonObject.getString("student_id"));

                                        }
                                        else {


                                            if(value.equals("1")) {
                                                countP++;
                                                dataList.add(new Pair<>(key, "P"));
                                            }else {
                                                countA++;
                                                dataList.add(new Pair<>(key, "A"));
                                            }

                                        }

                                    }
                                }

                                Present.setText(String.valueOf(countP));
                                Absent.setText(String.valueOf(countA));
                                int total = countA+countP;
                                Clases.setText(String.valueOf(total));

                                float p = countP;
                                float per = (p/total)*100;//(countP/total)*100;
                                if(per>=75.0){
                                    Percentage.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                                    Percentage.setText(String.valueOf(per)+" %");
                                }
                                else {
                                    Percentage.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                                    Percentage.setText(String.valueOf(per)+" %");
                                }


                                adapter.notifyDataSetChanged();  // Notify the adapter that the data has changed
                            } else {
                                Toast.makeText(getContext(), "Response is null", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String data = new String(error.networkResponse.data);
                            Toast.makeText(getContext(), "Error1: " + statusCode + "\n" + data, Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(getContext(), "Error2: " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        queue.add(jsonArrayRequest);
//        Toast.makeText(getContext(), "Send", Toast.LENGTH_SHORT).show();
    }

}
