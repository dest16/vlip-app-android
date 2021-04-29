package com.vlip.app.fragment.publish;

import android.Manifest;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BasePresenter;
import com.zaaach.citypicker.model.LocatedCity;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

class PublishPresenter extends BasePresenter<PublishModel, PublishView> {

    PublishPresenter(PublishModel mModel, PublishView mView) {
        super(mModel, mView);
    }


    @AfterPermissionGranted(1)
    void getCurrentLocation() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(BaseApplication.getInstance(), permissions)) {
            getModel().startQueryCurrentLocation(new AMapLocationListener() {
                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    LocatedCity city = new LocatedCity(aMapLocation.getCity(), aMapLocation.getProvince(), aMapLocation.getCityCode());
                    getView().setupTitleCity(city);
                    getView().updateLocatedCity(city);
                    getModel().stopQueryCurrentLocation(this);
                }
            });
        } else {
            getView().askPermission("定位需要开启以下权限", permissions);
        }

    }


    void selectLocatedCity(String city) {
        getView().showCityPick(city);
        getCurrentLocation();
    }


}