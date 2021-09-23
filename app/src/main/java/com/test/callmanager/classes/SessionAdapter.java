package com.test.callmanager.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.callmanager.R;
import com.test.callmanager.models.SessionHistory;
import com.test.callmanager.models.SessionInfo;

import java.util.ArrayList;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder> {

    Context context;
    ArrayList<SessionHistory> sessionHistories;

    public SessionAdapter(Context context, ArrayList<SessionHistory> sessionHistories) {
        this.sessionHistories = sessionHistories;
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

       holder.tvSupportName.setText("پشتیبان:"+sessionHistories.get(position).getSupportName());
    holder.tvAmount.setText("مبلغ:"+sessionHistories.get(position).getPrice());
    holder.tvAgentName.setText("نماینده:"+sessionHistories.get(position).getAgentName());
    holder.tvPriority.setText("اولویت:"+sessionHistories.get(position).getPriority());

    switch (sessionHistories.get(position).getSituation()){
        case "op":
            holder.tvStatus.setText("وضعیت:"+"خاموش بودن تلفن");
            break;

        case "nr":
            holder.tvStatus.setText("وضعیت:"+"برنداشتن تلفن");

            break;

        case "ft":
            holder.tvStatus.setText("وضعیت:"+"عدم انجام تراکنش");

            break;

        case "sf":
            holder.tvStatus.setText("وضعیت:"+"تراکنش موفق");

            break;

    }

    holder.tvDate.setText("تاریخ:"+sessionHistories.get(position).getDate());
    holder.tvDuration.setText("مدت جلسه:"+sessionHistories.get(position).getDuration()+" دقیقه");
    holder.tvDescription.setText("توضیحات:\n"+sessionHistories.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return sessionHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvSupportName;
        TextView tvAmount;
        TextView tvAgentName;
        TextView tvPriority;
        TextView tvStatus;
        TextView tvDate;
        TextView tvDuration;
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSupportName = itemView.findViewById(R.id.tv_support);
            tvAmount = itemView.findViewById(R.id.tv_price);
            tvAgentName = itemView.findViewById(R.id.tv_agent_name);
            tvPriority = itemView.findViewById(R.id.tv_priority_item);
            tvStatus = itemView.findViewById(R.id.tv_situation);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvDescription = itemView.findViewById(R.id.tv_description);



        }
    }
}
