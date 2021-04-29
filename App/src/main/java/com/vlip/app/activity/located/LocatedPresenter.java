package com.vlip.app.activity.located;

import android.location.Location;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.vlip.ui.mvp.base.BasePresenter;

public class LocatedPresenter extends BasePresenter<LocatedModel, LocatedView> {


    public LocatedPresenter(LocatedModel mModel, LocatedView mView) {
        super(mModel, mView);
    }

    public void getAddressInfo(LatLonPoint latLonPoint) {
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
    }

    public void getCurrentLocation() {
        getModel().startQueryCurrentLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    Location location = new Location(aMapLocation.getProvider());
                    //设置经纬度以及精度
                    location.setLatitude(aMapLocation.getLatitude());
                    location.setLongitude(aMapLocation.getLongitude());
                    location.setAccuracy(aMapLocation.getAccuracy());
//                    LatLng latLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
//                    addMarker(latLng);
                    //将位置信息返回给地图
//                    locationChangedListener.onLocationChanged(location);
                    getView().updateAddress(location);
                }
            }
        });
    }
}
