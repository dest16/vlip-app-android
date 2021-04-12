package com.vlip.app.activity.located;

import android.os.Looper;

import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BaseModel;

public class LocatedModel extends BaseModel {
    void startQueryCurrentLocation(TencentLocationListener listener) {
        TencentLocationRequest request = TencentLocationRequest.create();
        request.setInterval(3000);
        BaseApplication.getInstance().getLocationManager().requestLocationUpdates(request, listener, Looper.getMainLooper());
    }

    void stopQueryCurrentLocation(TencentLocationListener listener) {
        BaseApplication.getInstance().getLocationManager().removeUpdates(listener);
    }
}
