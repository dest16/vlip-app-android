package com.vlip.app.activity.located;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tencent.tencentmap.mapsdk.maps.SupportMapFragment;
import com.vlip.app.R;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.activity.base.BaseActivity;

public class LocatedActivity extends BaseActivity<LocatedPresenter> implements LocatedView {
    @Override
    public int getViewId() {
        return R.layout.activity_located;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public LocatedPresenter createPresenter() {
        return new LocatedPresenter(new LocatedModel(), this);
    }

    @Override
    public void initData() {
        initFragment();
        getPresenter().getCurrentLocation();
    }

    public void initFragment() {
        String tag = SupportMapFragment.class.getName();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        SupportMapFragment mapFragment = SupportMapFragment.newInstance(getBaseContext());
        mapFragment.getMap().setMyLocationEnabled(true);
        mapFragment.getMap().setLocationSource(this);
        ft.add(R.id.frame_map,mapFragment , tag);
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

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        ToastUtils.showToast("OnLocationChangedListener");
    }

    @Override
    public void deactivate() {

    }
}
