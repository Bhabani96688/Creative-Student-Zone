package com.crtv.creativetechnocollege;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.creativetechnocollege.R;

import java.util.List;

public class Attendance_Adapter extends ArrayAdapter<Pair<String, String>> {

    private List<Pair<String, String>> dataList;
    private LayoutInflater inflater;

    public Attendance_Adapter(Context context, List<Pair<String, String>> dataList) {
        super(context, R.layout.attendance_adapter, dataList);
        this.dataList = dataList;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDataList(List<Pair<String, String>> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.attendance_adapter, parent, false);

            holder = new ViewHolder();
            holder.keyTextView = convertView.findViewById(R.id.keyTextView);
            holder.valueTextView = convertView.findViewById(R.id.valueTextView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Pair<String, String> pair = dataList.get(position);
        if(pair.second.equals("P")){
            holder.keyTextView.setText(pair.first);
            holder.valueTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
            holder.valueTextView.setText(pair.second);
        }
        else{
            holder.keyTextView.setText(pair.first);
            holder.valueTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
            holder.valueTextView.setText(pair.second);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView keyTextView;
        TextView valueTextView;
    }
}
