package com.vlip.app.fragment.personal_center;

import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

class PersonalPresenter extends BasePresenter<PersonalModel, PersonalView> {

    PersonalPresenter(PersonalModel mModel, PersonalView mView) {
        super(mModel, mView);
    }

//    @AfterPermissionGranted(1)
//    void getCamera() {
//        String[] permissions = {
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.READ_PHONE_STATE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//        };
//        if (EasyPermissions.hasPermissions(BaseApplication.getInstance(), permissions)) {
//            getModel().startQueryCurrentLocation(new AMapLocationListener() {
//                @Override
//                public void onLocationChanged(AMapLocation aMapLocation) {
//                    LocatedCity city = new LocatedCity(aMapLocation.getCity(), aMapLocation.getProvince(), aMapLocation.getCityCode());
//                    getView().setupTitleCity(city);
//                    getView().updateLocatedCity(city);
//                    getModel().stopQueryCurrentLocation(this);
//                }
//            });
//        } else {
//            getView().askPermission("定位需要开启以下权限", permissions);
//        }
//
//    }

    void updateUserHeader(String id, String base64) {
        Map<String, Object> params = new HashMap<>();
        params.put("icon", base64);
        params.put("id", id);
        getModel().updateIcon(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                ToastUtils.showToast("修改成功！");
            }

            @Override
            public void onError(String errMsg) {
                ToastUtils.showToast(errMsg);
            }
        });
    }
}
