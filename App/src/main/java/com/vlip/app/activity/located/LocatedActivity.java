package com.vlip.app.activity.located;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.vlip.app.R;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.activity.base.BaseActivity;
import com.vlip.ui.row.RowSettingText;

import butterknife.BindView;
import butterknife.OnClick;

public class LocatedActivity extends BaseActivity<LocatedPresenter> implements LocatedView {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.address)
    RowSettingText mAdress;

    private AMap mAmap;
    private Location current;

    private final AMap.OnMyLocationChangeListener mOnMyLocationChangeListener = location -> {
        ToastUtils.showToast(location.toString());
    };

    @Override
    public int getViewId() {
        return R.layout.activity_located;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (mAmap == null)
            mAmap = mMapView.getMap();
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
        mAmap.setCustomMapStyle(
                new CustomMapStyleOptions()
                        .setEnable(true)
                        .setStyleId("22d591dd8593bacec3f550198b825f47")//官网控制台-自定义样式 获取
        );
        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAmap.addOnMyLocationChangeListener(mOnMyLocationChangeListener);
    }

    @Override
    public LocatedPresenter createPresenter() {
        return new LocatedPresenter(new LocatedModel(), this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.submit, R.id.fix_located})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                break;
            case R.id.fix_located:
                if (current != null)
                    mAmap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(current.getLatitude(), current.getLongitude())));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void updateAddress(Location location) {

    }
}
