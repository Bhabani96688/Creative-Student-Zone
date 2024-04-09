package com.crtv.creativetechnocollege;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.creativetechnocollege.R;

import java.util.List;

public class contactus_Adapter extends RecyclerView.Adapter<contactus_Adapter.ContactViewHolder> {
    private Context context;

    private List<ContactInfo> contactList;
    public contactus_Adapter(Context context, List<ContactInfo> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_contactus, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        ContactInfo contactInfo = contactList.get(position);
        holder.nameTextView.setText(contactInfo.getName());
        holder.designationTextView.setText(contactInfo.getDesignation());
//        holder.contactView.setText(contactInfo.getContact());
//        final String phoneNumber = contactInfo.getContact();

        holder.contactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialerApp(contactInfo.getContact());
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView designationTextView;
        ImageView contactView;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.Name);
            designationTextView = itemView.findViewById(R.id.Designation);
            contactView = itemView.findViewById(R.id.CallingImg);
        }
    }
    private void openDialerApp(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
}
