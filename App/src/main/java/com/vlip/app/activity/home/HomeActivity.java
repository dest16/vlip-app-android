package com.vlip.app.activity.home;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.fragment.cart.CartFragment;
import com.vlip.app.fragment.me.MeFragment;
import com.vlip.app.fragment.publish.PublishFragment;
import com.vlip.ui.activity.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView{

    @BindView(R.id.nav_view)
    BottomNavigationView mNavView;

    int mPrevIndex = 0;
    List<Fragment> mFragmentList;

    Badge mBdage;

    private BottomNavigationView.OnNavigationItemSelectedListener
            mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                switchFragmentPosition(0);
                return true;
            case R.id.navigation_cart:
                switchFragmentPosition(1);
                return true;
            case R.id.navigation_me:
                switchFragmentPosition(2);
                return true;
        }
        return false;
    };

    @Override
    public int getViewId() {
        return R.layout.activity_home;
    }


    public void initView(Bundle savedInstanceState) {
        mNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter(new HomeModel(), this);
    }

    @Override
    public void initData() {
        requirePermission();
        setSupportEventBus();
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new PublishFragment());
//        mFragmentList.add(new GoodsFragment());
        mFragmentList.add(new CartFragment());
        mFragmentList.add(new MeFragment());
        switchFragmentPosition(0);
        BottomNavigationMenuView menuView = null;
        for (int i = 0; i < mNavView.getChildCount(); i++) {
            View child = mNavView.getChildAt(i);
            if (child instanceof BottomNavigationMenuView) {
                menuView = (BottomNavigationMenuView) child;
                break;
            }
        }
        if (menuView != null) {
            BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(1);
            mBdage = new QBadgeView(getBaseContext()).bindTarget(itemView);
            mBdage.setBadgeGravity(Gravity.TOP | Gravity.END);
            mBdage.setGravityOffset(25, 0, true);
        }
        getPresenter().getBadgeCount();
    }

    private void switchFragmentPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment currentFragment = mFragmentList.get(position);
        Fragment prevFragment = mFragmentList.get(mPrevIndex);
        mPrevIndex = position;
        ft.hide(prevFragment);
        if (!currentFragment.isAdded()) {
            ft.add(R.id.frame_layout, currentFragment);
        }
        ft.show(currentFragment);
        ft.commitAllowingStateLoss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.CartEvent event) {
        getPresenter().getBadgeCount();
    }

    @Override
    public void setBadgeCount(Integer count) {
        mBdage.setBadgeNumber(count);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @AfterPermissionGranted(1)
    private void requirePermission() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        String[] permissionsForQ = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.ACCESS_BACKGROUND_LOCATION, //target为Q时，动态请求后台定位权限
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (Build.VERSION.SDK_INT >= 29 ? EasyPermissions.hasPermissions(this, permissionsForQ) :
                EasyPermissions.hasPermissions(this, permissions)) {
            Toast.makeText(this, "权限OK", Toast.LENGTH_LONG).show();
        } else {
            EasyPermissions.requestPermissions(this, "需要权限",
                    1, Build.VERSION.SDK_INT >= 29 ? permissionsForQ : permissions);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
