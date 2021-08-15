package com.test.callmanager.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.callmanager.R;
import com.test.callmanager.models.SessionInfo;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.ViewHolder> {

    Context context;
    ArrayList<SessionInfo> sessionList;

    public MyAdaptor(Context context, ArrayList<SessionInfo> sessionList) {
        this.sessionList = sessionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View View = layoutInflater.inflate(R.layout.rv_item, parent, false);

        return new ViewHolder(View);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tvName.setText(sessionList.get(position).getName());
        holder.tvDescription.setText(sessionList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


       TextView tvName;
       TextView tvDescription;


        public ViewHolder(View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.tv_name);
            tvDescription=itemView.findViewById(R.id.tv_description);


        }
    }
}
