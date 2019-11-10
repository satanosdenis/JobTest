package com.test.job.jobtest.data;

import android.content.Context;

import com.google.gson.annotations.SerializedName;
import com.test.job.jobtest.R;

public class UserData {
    private final static int genderMan = 2;
    private final static int genderWoman = 1;

    @SerializedName("first_name")
    private String userFirstName;
    @SerializedName("last_name")
    private String userLastName;
    private int id;
    @SerializedName("screen_name")
    private String screenName;
    @SerializedName("photo_50")
    private String userSmallImage;
    @SerializedName("photo_100")
    private String userLargeImage;
    private int sex;
    private int relation;

    public String getFullName() {
        return userFirstName + " " + userLastName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public int getId() {
        return id;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getUserSmallImage() {
        return userSmallImage;
    }

    public String getUserLargeImage() {
        return userLargeImage;
    }

    public String getGender(Context context){
        String gender;

        switch (sex)
        {
            case genderMan:
                gender = context.getString(R.string.gender_man);
                break;

            case genderWoman:
                gender = context.getString(R.string.gender_woman);
                break;

                default:
                    gender = context.getString(R.string.gender_it);
                    break;
        }

        return gender;
    }

    public int getRelation() {
        return relation;
    }
}
