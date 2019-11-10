package com.test.job.jobtest.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.test.job.jobtest.MainActivity;
import com.test.job.jobtest.R;
import com.test.job.jobtest.adapters.SearchAdapter;
import com.test.job.jobtest.data.Callback;
import com.test.job.jobtest.data.Shell;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

public class SearchFragment extends Fragment {
    private final static int usersInPage = 20;

    private SearchAdapter searchAdapter;
    private int offset = 0;
    private String userQuery;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View searchFragment = inflater.inflate(R.layout.search_fragment, container, false);

        RecyclerView recyclerView = searchFragment.findViewById(R.id.humans_list);
        Toolbar toolbar = searchFragment.findViewById(R.id.search_fragment_toolbar);
        SearchView searchView = toolbar.findViewById(R.id.search_users);
        Button cancelButton = toolbar.findViewById(R.id.cancel_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(searchFragment.getContext()));

        searchAdapter = new SearchAdapter(new Callback() {
            @Override
            public void callingBack() {
                sendRequest(searchFragment.getContext(), userQuery);
            }
        });

        recyclerView.setAdapter(searchAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                userQuery = query;
                clearRequestValues();
                sendRequest(searchFragment.getContext(), query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearRequestValues();
            }
        });

        ((MainActivity) searchFragment.getContext()).setSupportActionBar(toolbar);

        return searchFragment;
    }

    private void sendRequest(Context context, String query){
        final ProgressDialog dialog = new ProgressDialog(context, R.style.AlertDialogTheme);
        dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();

        VKRequest request = VKApi.users().search(VKParameters.from(VKApiConst.FIELDS, "first_name, last_name, full_name, photo_50, photo_100, sex, screen_name, relation", VKApiConst.Q, query, VKApiConst.COUNT, usersInPage, VKApiConst.OFFSET, offset));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                Shell shell = Shell.deserialize(response.json.toString());

                searchAdapter.setUsersData(shell.getResponse().getUserData());
                offset += usersInPage;
                searchAdapter.setOffset(offset);

                dialog.dismiss();
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }

    private void clearRequestValues(){
        offset = 0;
        searchAdapter.clearUsersData();
    }
}
