package com.test.callmanager.classes;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.callmanager.R;
import com.test.callmanager.activities.SessionActiviy;
import com.test.callmanager.models.SessionInfo;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Activity activity;
    ArrayList<SessionInfo> sessionList;
    View rootView;

    public MainAdapter(Activity activity, ArrayList<SessionInfo> sessionList) {
        this.sessionList = sessionList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(activity);
        rootView = layoutInflater.inflate(R.layout.rv_item_main, parent, false);

        return new ViewHolder(rootView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvTitle.setText(sessionList.get(position).getTitle());
        holder.tvPhoneNumber.setText(sessionList.get(position).getPhoneNumber());

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.startActivity(new Intent(activity, SessionActiviy.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTitle;
        TextView tvPhoneNumber;


        public ViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tv_title);
            tvPhoneNumber = itemView.findViewById(R.id.tv_phone_number);


        }
    }
}
