package com.test.job.jobtest.data;

import com.google.gson.annotations.SerializedName;

public class Response {
    private int count;
    @SerializedName("items")
    private UserData[] userData;

    public UserData[] getUserData() {
        return userData;
    }
}
