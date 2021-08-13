package com.vlip.app.fragment.order_detail;

import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.bean.Order2;
import com.vlip.ui.dialog.CommonDialog;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowSettingText;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailFragment extends BaseFragment<OrderDetailPresenter> implements OrderDetailView {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.take)
    View mTakeView;
    @BindView(R.id.from)
    RowSettingText fromView;
    @BindView(R.id.to)
    RowSettingText toView;
    @BindView(R.id.cancel)
    View cancelView;
    CommonDialog dialog;

    private AMap mAmap;
    private Order2 mOrder;

    @Override
    public int getViewId() {
        return R.layout.fragment_order_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);
        if (mAmap == null)
            mAmap = mMapView.getMap();
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.getUiSettings().setTiltGesturesEnabled(false);
        mAmap.getUiSettings().setRotateGesturesEnabled(false);
        dialog = new CommonDialog(getContext());
        dialog.setTitle("删除订单");
        dialog.setContent("确定要删除该订单吗？");
    }

    @Override
    public void initData() {
        assert getArguments() != null;
        mOrder = (Order2) getArguments().getSerializable(Constants.INTENT_KEY1);
        boolean showTake = getArguments().getBoolean(Constants.INTENT_KEY2);
        String title = "";
        switch (mOrder.status) {
            case 0:
                title = "待接单";
                cancelView.setVisibility(View.VISIBLE);
                break;
            case 1:
                title = "进行中";
                break;
            case 3:
                title = "已完成";
                break;
            case 4:
                title = "已取消";
                break;
            default:
                break;
        }
        getToolbar().setTitle(title);
        mTakeView.setVisibility(showTake ? View.VISIBLE : View.GONE);
        addMarkers(mOrder);
        fromView.setRightImage(false);
        toView.setRightImage(false);
        fromView.setTitle(mOrder.fromName);
        fromView.setStatus(mOrder.fromSite + "  ");
        fromView.setSummary(mOrder.fromPhone);
        toView.setTitle(mOrder.toName);
        toView.setStatus(mOrder.toSite + "  ");
        toView.setSummary(mOrder.toPhone);
    }

    @Override
    public OrderDetailPresenter createPresenter() {
        return new OrderDetailPresenter(new OrderDetailModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.take, R.id.cancel})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.take:
                getPresenter().acceptOrder(mOrder.id);
                break;
            case R.id.cancel:
                dialog.setOnClickSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getPresenter().cancelOrder(mOrder.id);
                    }
                });
                dialog.show();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }


    /**
     * 根据自定义内容获取缩放bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }


    private void addMarkers(Order2 o) {
        if (mAmap == null)
            return;
        LatLngBounds.Builder b = LatLngBounds.builder();
        LatLng from = new LatLng(o.fromLat, o.fromLon);
        LatLng to = new LatLng(o.toLat, o.toLon);
        b.include(from).include(to);

        mAmap.addMarker(new MarkerOptions()
                .position(from)
                .title(o.fromSite)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_start_128)));
        mAmap.addMarker(new MarkerOptions()
                .position(to)
                .title(o.toSite)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin_end_128)));

        mAmap.moveCamera(CameraUpdateFactory.newLatLngBounds(b.build(), 100));
    }


    @Override
    public void goBack() {
        finish();
    }
}
