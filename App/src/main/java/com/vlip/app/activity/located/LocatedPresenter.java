package com.vlip.app.activity.located;

import android.location.Location;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.vlip.ui.mvp.base.BasePresenter;

public class LocatedPresenter extends BasePresenter<LocatedModel,LocatedView> {



    public LocatedPresenter(LocatedModel mModel, LocatedView mView) {
        super(mModel, mView);
    }

    public void getCurrentLocation(){
        getModel().startQueryCurrentLocation(new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                if(i == TencentLocation.ERROR_OK ){
                    Location location = new Location(tencentLocation.getProvider());
                    //设置经纬度
                    location.setLatitude(tencentLocation.getLatitude());
                    location.setLongitude(tencentLocation.getLongitude());
                    //设置精度，这个值会被设置为定位点上表示精度的圆形半径
                    location.setAccuracy(tencentLocation.getAccuracy());
                    //设置定位标的旋转角度，注意 tencentLocation.getBearing() 只有在 gps 时才有可能获取
                    location.setBearing((float) tencentLocation.getBearing());
                    //将位置信息返回给地图
//                    locationChangedListener.onLocationChanged(location);

                }
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {

            }
        });
    }
}
