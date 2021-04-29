package com.vlip.app.activity.located;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BaseModel;

public class LocatedModel extends BaseModel {
    void startQueryCurrentLocation(AMapLocationListener listener) {
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        AMapLocationClient client = BaseApplication.getInstance().getLocationClient();
        if (null != client) {
            client.setLocationOption(option);
            client.setLocationListener(listener);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            client.stopLocation();
            client.startLocation();
        }
    }

    void stopQueryCurrentLocation(AMapLocationListener listener) {
        AMapLocationClient client = BaseApplication.getInstance().getLocationClient();
        if (null != client) {
            client.unRegisterLocationListener(listener);
            client.stopLocation();
        }
    }

    void queryAddressInfoByPoi() {

    }
}
