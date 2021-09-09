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

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    Context context;
    ArrayList<SessionInfo> sessionList;

    public SessionAdapter(Context context, ArrayList<SessionInfo> sessionList) {
        this.sessionList = sessionList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View View = layoutInflater.inflate(R.layout.rv_item_session, parent, false);

        return new ViewHolder(View);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.tvTitle.setText(sessionList.get(position).getTitle());
//        holder.tvPhoneNumber.setText(sessionList.get(position).getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return 20;
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
