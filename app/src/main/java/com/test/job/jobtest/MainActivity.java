package com.test.job.jobtest;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;

import com.test.job.jobtest.data.Shell;
import com.test.job.jobtest.data.Response;
import com.test.job.jobtest.data.UserData;
import com.test.job.jobtest.fragments.DetailFragment;
import com.test.job.jobtest.fragments.SearchFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCaptchaDialog;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.VKSdkListener;
import com.vk.sdk.VKUIHelper;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKList;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private ViewAnimator viewAnimator;

    private Animation slideInFromLeft;
    private Animation slideOutToRight;
    private Animation slideInFromRight;
    private Animation slideOutToLeft;

    private static final String[] scope =  new String[]{VKScope.FRIENDS, VKScope.WALL, VKScope.PHOTOS, VKScope.NOHTTPS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        VKUIHelper.onCreate(this);
        VKSdk.initialize(sdkListener, getString(R.string.com_vk_sdk_AppId), VKAccessToken.tokenFromSharedPreferences(this, getString(R.string.token)));
        VKSdk.authorize(scope, true, false);

        viewAnimator = findViewById(R.id.view_animator);

        slideInFromLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_left);
        slideOutToRight = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_right);
        slideInFromRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_from_right);
        slideOutToLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left);
    }

    public void showDetailFragment(UserData userData) {
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setUserData(userData);

        getFragmentManager().beginTransaction().replace(R.id.detail_container, detailFragment).commit();

        viewAnimator.setInAnimation(slideInFromLeft);
        viewAnimator.setOutAnimation(slideOutToRight);
        viewAnimator.showNext();
    }

    public void showSearchFragment(){
        viewAnimator.setInAnimation(slideInFromRight);
        viewAnimator.setOutAnimation(slideOutToLeft);
        viewAnimator.showPrevious();
    }

    @Override
    protected void onResume() {
        super.onResume();
        VKUIHelper.onResume(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VKUIHelper.onDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        VKUIHelper.onActivityResult(requestCode, resultCode, data);
    }

    private VKSdkListener sdkListener = new VKSdkListener() {
        @Override
        public void onCaptchaError(VKError captchaError) {
            new VKCaptchaDialog(captchaError).show();
        }

        @Override
        public void onTokenExpired(VKAccessToken expiredToken) {
            VKSdk.authorize(scope);
        }

        @Override
        public void onAccessDenied(VKError authorizationError) {
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage(authorizationError.errorMessage)
                    .show();
        }

        @Override
        public void onReceiveNewToken(VKAccessToken newToken) {
            newToken.saveTokenToSharedPreferences(MainActivity.this, getString(R.string.token));
            addSearchFragment();
        }

        @Override
        public void onAcceptUserToken(VKAccessToken token) {
            addSearchFragment();
        }

        private void addSearchFragment() {
            getFragmentManager().beginTransaction().replace(R.id.main_container, new SearchFragment()).commit();
        }
    };
}
