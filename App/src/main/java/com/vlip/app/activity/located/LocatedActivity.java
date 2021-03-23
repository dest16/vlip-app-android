package com.vlip.app.activity.located;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.vlip.app.R;
import com.vlip.ui.activity.base.BaseActivity;

public class LocatedActivity extends BaseActivity<LocatedPresenter> {
    @Override
    public int getViewId() {
        return R.layout.activity_located;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public LocatedPresenter createPresenter() {
        return null;
    }

    @Override
    public void initData() {
        initFragment();
    }

    public void initFragment() {
        String tag = SupportMapFragment.class.getName();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frame_map, SupportMapFragment.newInstance(getBaseContext()), tag);
        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if (count > 1) {
            backStackFragment();
        } else {
            finish();
        }

    }

    public void backStackFragment() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStackImmediate();
    }

}
