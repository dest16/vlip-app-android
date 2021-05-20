package com.vlip.app.fragment.order2;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Member;
import com.vlip.app.fragment.login.LoginFragment;
import com.vlip.app.fragment.order_list.OrderListFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.ui.activity.ToolbarFragmentActivity;
import com.vlip.ui.adapter.viewpager.BaseFragmentPagerAdapter;
import com.vlip.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderFragment2 extends BaseFragment<OrderPresenter2> implements OrderView2 {

    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.login)
    View mLogin;

    List<String> mTitleList = new ArrayList<>();
    List<Fragment> mFragmentList = new ArrayList<>();
    BaseFragmentPagerAdapter mAdapter;

    @Override
    public int getViewId() {
        return R.layout.fragment_order2;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager, true);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        onEvent(new Event.LoginEvent());
//        assert getArguments() != null;
//        getToolbar().setTitle("我的订单");
        mTitleList.add("待接单");
        mTitleList.add("进行中");
        mTitleList.add("已完成");
        mTitleList.add("已取消");
        mFragmentList.add(OrderListFragment.newInstance(0));
        mFragmentList.add(OrderListFragment.newInstance(1));
        mFragmentList.add(OrderListFragment.newInstance(3));
        mFragmentList.add(OrderListFragment.newInstance(4));
        mAdapter.setFragment(mTitleList, mFragmentList);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
//        int item = getArguments().getInt(Constants.INTENT_KEY1, 0);
//        mViewPager.setCurrentItem(item, false);
        mViewPager.setCurrentItem(0, false);

    }

    @Override
    public OrderPresenter2 createPresenter() {
        return new OrderPresenter2(new OrderModel2(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.login})
    public void onClick() {
        ToolbarFragmentActivity.createFragment(requireContext(), LoginFragment.class);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LoginEvent event) {
        Member member = AppUtils.getMember();
        if (member != null) {
//            mGoLogin.setText(AppUtils.formatPhone(member.mobile));
            mTabLayout.setVisibility(View.VISIBLE);
            mViewPager.setVisibility(View.VISIBLE);
            mLogin.setVisibility(View.GONE);
        } else {
            mTabLayout.setVisibility(View.GONE);
            mViewPager.setVisibility(View.GONE);
            mLogin.setVisibility(View.VISIBLE);
        }
    }
}
