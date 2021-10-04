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

        holder.tvSupportName.setText(context.getString(R.string.support) + sessionHistories.get(position).getSupportName());
        holder.tvPrice.setText(context.getString(R.string.amount) + sessionHistories.get(position).getPrice());
        holder.tvAgentName.setText(context.getString(R.string.agent_name) + sessionHistories.get(position).getAgentName());
        holder.tvPriority.setText(context.getString(R.string.priority) + " : " + sessionHistories.get(position).getPriority());

        switch (sessionHistories.get(position).getStatus()) {
            case MyConstant.OP:
                holder.tvStatus.setText(context.getString(R.string.status) + context.getString(R.string.off_phone));
                break;

            case MyConstant.NR:
                holder.tvStatus.setText(context.getString(R.string.status) + context.getString(R.string.no_response));

                break;

            case MyConstant.FT:
                holder.tvStatus.setText(context.getString(R.string.status) + context.getString(R.string.failure_transaction));

                break;

            case MyConstant.SF:
                holder.tvStatus.setText(context.getString(R.string.status) + context.getString(R.string.succsessfully_transaction));

                break;

        }

        holder.tvDate.setText(context.getString(R.string.date) + sessionHistories.get(position).getDate());
        holder.tvDuration.setText(context.getString(R.string.duratoin_meet) + " : " + sessionHistories.get(position).getDuration() + " دقیقه");
        holder.tvDescription.setText(context.getString(R.string.description) + ":\n" + sessionHistories.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return sessionHistories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvSupportName;
        TextView tvAgentName;
        TextView tvStatus;
        TextView tvPriority;
        TextView tvPrice;
        TextView tvDate;
        TextView tvDuration;
        TextView tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSupportName = itemView.findViewById(R.id.tv_support);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvAgentName = itemView.findViewById(R.id.tv_agent_name);
            tvPriority = itemView.findViewById(R.id.tv_priority_item);
            tvStatus = itemView.findViewById(R.id.tv_situation);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvDescription = itemView.findViewById(R.id.tv_description);


        }
    }
}
