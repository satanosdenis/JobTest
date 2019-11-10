package com.test.job.jobtest.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.job.jobtest.MainActivity;
import com.test.job.jobtest.data.Callback;
import com.test.job.jobtest.data.UserData;
import com.test.job.jobtest.R;

import java.util.ArrayList;
import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{
    private final ArrayList<UserData> usersData;
    private final Callback callback;
    private int offset;

    class SearchViewHolder extends RecyclerView.ViewHolder {
        private TextView humanName;
        private ImageButton showDetailButton;
        private CircleImageView humanImage;

        SearchViewHolder(View itemView) {
            super(itemView);

            humanImage = itemView.findViewById(R.id.human_image);
            humanName = itemView.findViewById(R.id.human_name);
            showDetailButton = itemView.findViewById(R.id.show_detail_button);
        }
    }

    public SearchAdapter(Callback callback){
        this.callback = callback;
        usersData = new ArrayList<>();
    }

    @Override
    public @NonNull SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.human_info_item, parent, false);

        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        final UserData userData = usersData.get(position);

        holder.showDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)v.getContext()).showDetailFragment(userData);
            }
        });

        holder.humanName.setText(userData.getFullName());
        Picasso.get().load(userData.getUserSmallImage()).into(holder.humanImage);

        if(offset - 1 == position){
            callback.callingBack();
        }
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getItemCount() {
        return usersData.size();
    }

    public void setUsersData(UserData[] usersData){
        this.usersData.addAll(Arrays.asList(usersData));
        notifyDataSetChanged();
    }

    public void clearUsersData(){
        usersData.clear();
        setOffset(0);
        notifyDataSetChanged();
    }
}
