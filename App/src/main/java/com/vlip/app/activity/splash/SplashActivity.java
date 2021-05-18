package com.vlip.app.activity.splash;

import android.os.Bundle;

import com.vlip.app.R;
import com.vlip.app.activity.home.HomeActivity;
import com.vlip.app.bean.Member;
import com.vlip.app.fragment.login.LoginFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.ui.activity.ToolbarFragmentActivity;
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
        Member member = AppUtils.getMember();
        if (member != null) {
            goIntent(HomeActivity.class);
        } else {
            ToolbarFragmentActivity.createFragment(this, LoginFragment.class);
        }
        finish();


    }

}
