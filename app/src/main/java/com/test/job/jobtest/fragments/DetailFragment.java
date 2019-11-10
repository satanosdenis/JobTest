package com.test.job.jobtest.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.job.jobtest.MainActivity;
import com.test.job.jobtest.R;
import com.test.job.jobtest.adapters.DetailListAdapter;
import com.test.job.jobtest.data.UserData;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailFragment extends Fragment{
    private UserData userData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View detailFragment = inflater.inflate(R.layout.detail_fragment, container, false);
        RecyclerView recyclerView = detailFragment.findViewById(R.id.human_detail_list);

        recyclerView.setAdapter(new DetailListAdapter(userData, detailFragment.getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(detailFragment.getContext()));

        Toolbar toolbar = detailFragment.findViewById(R.id.detail_fragment_toolbar);
        TextView userName = toolbar.findViewById(R.id.user_name);
        ImageButton backButton = toolbar.findViewById(R.id.back_button);
        CircleImageView humanLargeImage = detailFragment.findViewById(R.id.human_large_image);

        userName.setText(userData.getFullName());
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).showSearchFragment();
            }
        });

        Picasso.get().load(userData.getUserLargeImage()).into(humanLargeImage);
        ((MainActivity)getContext()).setSupportActionBar(toolbar);

        return detailFragment;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
