package com.test.job.jobtest.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.job.jobtest.R;
import com.test.job.jobtest.data.Callback;
import com.test.job.jobtest.data.Details;
import com.test.job.jobtest.data.UserData;

public class DetailListAdapter extends RecyclerView.Adapter<DetailListAdapter.DetailViewHolder> {
    private final Details[] details;

    class DetailViewHolder extends RecyclerView.ViewHolder {
        private TextView detailValue;
        private TextView detailDescription;

        DetailViewHolder(View itemView) {
            super(itemView);

            detailValue = itemView.findViewById(R.id.detail_value);
            detailDescription = itemView.findViewById(R.id.detail_description);
        }
    }

    public DetailListAdapter(UserData userData, Context context){
        details = new Details[4];
        details[0] = new Details(context.getString(R.string.name), userData.getUserFirstName());
        details[1] = new Details(context.getString(R.string.second_name), userData.getUserLastName());
        details[2] = new Details(context.getString(R.string.screen_name), userData.getScreenName());
        details[3] = new Details(context.getString(R.string.sex), userData.getGender(context));
    }

    @Override
    public @NonNull DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.human_detail_item, parent, false);

        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, int position) {
        Details detail = details[position];

        holder.detailValue.setText(detail.getDetailValue());
        holder.detailDescription.setText(detail.getDetailDescription());
    }

    @Override
    public int getItemCount() {
        return details.length;
    }
}
