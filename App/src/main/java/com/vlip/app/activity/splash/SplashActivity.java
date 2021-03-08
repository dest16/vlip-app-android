package com.vlip.app.activity.splash;

import android.os.Bundle;

import com.vlip.app.R;
import com.vlip.app.activity.home.HomeActivity;
import com.vlip.ui.activity.base.BaseActivity;
import com.vlip.ui.mvp.IPresenter;

public class SplashActivity extends BaseActivity {

    @Override
    public int getViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

    @Override
    public void initData() {
        goIntent(HomeActivity.class);
        finish();
    }

}
