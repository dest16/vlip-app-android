package com.vlip.app.fragment.publish;

import android.Manifest;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
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
            getModel().startQueryCurrentLocation(new TencentLocationListener() {
                @Override
                public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                    LocatedCity city = new LocatedCity(tencentLocation.getCity(), tencentLocation.getProvince(), tencentLocation.getCityCode());
                        getView().setupTitleCity(city);
                        getView().updateLocatedCity(city);
                    getModel().stopQueryCurrentLocation(this);
                }

                @Override
                public void onStatusUpdate(String s, int i, String s1) {

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