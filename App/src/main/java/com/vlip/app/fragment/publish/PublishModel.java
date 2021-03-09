package com.vlip.app.fragment.publish;

import android.os.Looper;

import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BaseModel;

import pub.devrel.easypermissions.EasyPermissions;

class PublishModel extends BaseModel {


    //    void getTreeCategory(Map<String, Object> params, BaseResponse observer) {
//        RetrofitManager.getInstance().mNetwrokService.queryTreeCategory(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }


    void startQueryCurrentLocation(TencentLocationListener listener) {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
        BaseApplication.getInstance().getLocationManager().requestLocationUpdates(request, listener, Looper.getMainLooper());
    }

    void stopQueryCurrentLocation(TencentLocationListener listener) {
        BaseApplication.getInstance().getLocationManager().removeUpdates(listener);
    }

    void requirePermission(EasyPermissions.PermissionCallbacks callbacks) {

    }


}
