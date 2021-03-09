package com.vlip.app.fragment.publish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.fragment.BaseFragment;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
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

    CityPicker mCityPicker;

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
        mTitle.setOnClickListener(mTitleClickListener);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getPresenter().getCurrentLocation();
        mCityPicker = CityPicker.from(this).enableAnimation(true).setOnPickListener(mOnPickListener);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LocationEvent event) {

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
}
