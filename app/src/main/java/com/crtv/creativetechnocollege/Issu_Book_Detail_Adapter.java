package com.crtv.creativetechnocollege;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.creativetechnocollege.R;

import java.util.List;

public class Issu_Book_Detail_Adapter extends BaseAdapter {

    private Context context;
    private List<BookDetails> data;

    public Issu_Book_Detail_Adapter(Context context, List<BookDetails> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            // Inflate the layout for each list item
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.issu_book_details, null);

            // Create a ViewHolder to store a reference to the views within the layout
            holder = new ViewHolder();
            holder.bsDateTextView = convertView.findViewById(R.id.Bs_Date);
            holder.bNameTextView = convertView.findViewById(R.id.B_name);
            holder.bIdTextView = convertView.findViewById(R.id.B_Id);
            holder.bEditionTextView = convertView.findViewById(R.id.B_Edition);
            holder.bCatagoryTextView = convertView.findViewById(R.id.B_Catagory);
            holder.bPublisherTextView = convertView.findViewById(R.id.B_Publisher);

            convertView.setTag(holder);
        } else {
            // Reuse the existing convertView
            holder = (ViewHolder) convertView.getTag();
        }

        // Set the data for the current position
        BookDetails bookDetails = data.get(position);
        holder.bsDateTextView.setText(bookDetails.getIssueDate()); //"Book Issue Date: "
        holder.bNameTextView.setText(bookDetails.getBookName()); //"Name of Book: "
        holder.bIdTextView.setText(bookDetails.getBookId()); //"Book ID: "
        holder.bEditionTextView.setText(bookDetails.getBookEdition()); //"Book Edition: "
        holder.bCatagoryTextView.setText(bookDetails.getBookCategory()); //"Book Category: "
        holder.bPublisherTextView.setText(bookDetails.getBookPublisher()); //"Book Publisher: "

        return convertView;
    }

    // ViewHolder pattern to improve performance by recycling views
    private static class ViewHolder {
        TextView bsDateTextView;
        TextView bNameTextView;
        TextView bIdTextView;
        TextView bEditionTextView;
        TextView bCatagoryTextView;
        TextView bPublisherTextView;
    }
}
