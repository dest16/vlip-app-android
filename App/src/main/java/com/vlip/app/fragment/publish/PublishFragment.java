package com.vlip.app.fragment.publish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.activity.located.LocatedActivity;
import com.vlip.app.bean.Car;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Position;
import com.vlip.app.fragment.car.CarFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.kit.FastjsonUtils;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.adapter.viewpager.BaseFragmentPagerAdapter;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowSettingText;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 购物车
 *
 * @author zm
 */
public class PublishFragment extends BaseFragment<PublishPresenter> implements PublishView, EasyPermissions.PermissionCallbacks {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbarTitle)
    TextView mTitle;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTab;
    @BindView(R.id.from)
    RowSettingText fromView;
    @BindView(R.id.to)
    RowSettingText toView;
    BaseFragmentPagerAdapter mAdapter;
    CityPicker mCityPicker;

    List<Car> mCarList;
    List<Fragment> mFragmentList;
    List<String> mTitleList;

    private final View.OnClickListener mTitleClickListener = view -> getPresenter().selectLocatedCity(mTitle.getText().toString());

    private final OnPickListener mOnPickListener = new OnPickListener() {

        @Override
        public void onPick(int position, City data) {
            mTitle.setText(data.getName());
            mTitle.setTag(data);
        }

        @Override
        public void onLocate() {
            getPresenter().getCurrentLocation();
        }

        @Override
        public void onCancel() {

        }
    };

    @Override
    public int getViewId() {
        return R.layout.fragment_publish;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mAdapter = new BaseFragmentPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTab.setupWithViewPager(mViewPager, true);
        mCityPicker = CityPicker.from(this);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getPresenter().getCurrentLocation();
        mCarList = FastjsonUtils.toList(AppUtils.readAssetsText("cars.json"), Car.class);
        mTitle.setOnClickListener(mTitleClickListener);
        mCityPicker.enableAnimation(true).setOnPickListener(mOnPickListener);
        if (mViewPager != null && mCarList != null && mCarList.size() > 0) {
            this.mTitleList = new ArrayList<>();
            this.mFragmentList = new ArrayList<>();
            for (Car item : this.mCarList) {
                mTitleList.add(item.name);
                mFragmentList.add(CarFragment.newInstance(item));
            }
            mAdapter.setFragment(mTitleList, mFragmentList);
            mViewPager.setOffscreenPageLimit(mFragmentList.size());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LocationEvent event) {
        switch (event.position.type) {
            case "from":
                fromView.setTitle(event.position.title);
                fromView.setSummary(event.position.subTitle);
                break;
            case "to":
                toView.setTitle(event.position.title);
                toView.setSummary(event.position.subTitle);
                break;
        }
    }

    @Override
    public PublishPresenter createPresenter() {
        return new PublishPresenter(new PublishModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void setupTitleCity(LocatedCity city) {
        if (mTitle.getText().length() == 0) {
            mTitle.setText(city.getName());
            mTitle.setTag(city);
        }
    }

    @Override
    public void updateLocatedCity(LocatedCity city) {
        mCityPicker.locateComplete(city, LocateState.SUCCESS);
    }

    @Override
    public void askPermission(String title, String... permissions) {
        EasyPermissions.requestPermissions(this, title, 1, permissions);
    }

    @Override
    public void showCityPick(String s) {
        mCityPicker.show();
    }

    @Override
    public void closeApp() {
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        getPresenter().onPermissionRequest(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        ToastUtils.showToast("onPermissionsGranted");
        getPresenter().getCurrentLocation();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        finish();
    }

    @OnClick({R.id.submit, R.id.from, R.id.to})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Position from = (Position) fromView.getTag();
                Position to = (Position) toView.getTag();
                getPresenter().publishCargo(from, to, mCarList.get(mViewPager.getCurrentItem()).name);
                break;
            case R.id.from:
                Bundle a1 = new Bundle();
                a1.putString(Constants.INTENT_KEY1, "from");
                goIntent(LocatedActivity.class, a1);
                break;
            case R.id.to:
                Bundle a2 = new Bundle();
                a2.putString(Constants.INTENT_KEY1, "to");
                goIntent(LocatedActivity.class, a2);
                break;
        }
//        ToolbarFragmentActivity.createFragment(requireContext(), SupportMapFragment.class);
    }
}
