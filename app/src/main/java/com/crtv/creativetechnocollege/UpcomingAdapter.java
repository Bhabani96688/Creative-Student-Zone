package com.crtv.creativetechnocollege;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativetechnocollege.R;

import java.util.ArrayList;
import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.TextContentViewHolder> {

    private ArrayList<String> textContentList;

    public UpcomingAdapter(ArrayList<String> textContentList) {
        this.textContentList = textContentList;
    }

    @NonNull
    @Override
    public TextContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_item, parent, false);
        return new TextContentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TextContentViewHolder holder, int position) {
        String textContent = textContentList.get(position);
        holder.textViewContent.setText(1+position+". "+textContent);
    }

    @Override
    public int getItemCount() {
        return textContentList.size();
    }

    public void setTextContentList(List<String> textContentList) {
        this.textContentList = (ArrayList<String>) textContentList;
    }

    public List<String> getTextContentList() {
        return textContentList;
    }

    public static class TextContentViewHolder extends RecyclerView.ViewHolder {
        TextView textViewContent;

        public TextContentViewHolder(View itemView) {
            super(itemView);
            textViewContent = itemView.findViewById(R.id.textViewContent);
        }
    }
}

