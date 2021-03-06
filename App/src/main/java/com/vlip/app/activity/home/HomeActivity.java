package com.vlip.app.activity.home;

import android.os.Bundle;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;

import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Member;
import com.vlip.app.fragment.accept.AcceptFragment;
import com.vlip.app.fragment.me.MeFragment;
import com.vlip.app.fragment.order2.OrderFragment2;
import com.vlip.app.fragment.publish.PublishFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.ui.activity.base.BaseActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView {

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
            case R.id.navigation_order:
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
        setSupportEventBus();
        mFragmentList = new ArrayList<>();
        if (AppUtils.getMember().role == 0) {
            mFragmentList.add(new PublishFragment());
        } else {
            mFragmentList.add(new AcceptFragment());
        }
//        mFragmentList.add(new PublishFragment());
//        mFragmentList.add(new GoodsFragment());
//        mFragmentList.add(new CartFragment());
        mFragmentList.add(new OrderFragment2());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LoginEvent event) {
        Member member = AppUtils.getMember();
        if (member != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (member.role == 0) {
                mFragmentList.set(0, new PublishFragment());
            } else {
                mFragmentList.set(0, new AcceptFragment());
            }
            ft.replace(0, mFragmentList.get(0));
            ft.commitAllowingStateLoss();
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.ShowOrdersEvent event) {
        switchFragmentPosition(1);
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

}
