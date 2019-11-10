package com.test.job.jobtest.data;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Shell {
    @SerializedName("response")
    private Response response;

    public static Shell deserialize(String jsonString){
        return new GsonBuilder().create().fromJson(jsonString, Shell.class);
    }

    public Response getResponse() {
        return response;
    }
}
