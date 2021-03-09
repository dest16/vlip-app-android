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

    //    void queryTreeCategory() {
//        Map<String, Object> params = new HashMap<>();
//        params.put("parentId", "1");
//        getModel().getTreeCategory(params, new BaseResponse() {
//            @Override
//            public void onSuccess(ResultBean bean) {
//                JSONObject data = bean.getJSONObject();
//                String jsonList = data.optString("list");
//                List<GoodsCategory> categoryList = FastjsonUtils.toList(jsonList, GoodsCategory.class);
////                getView().setCategoryTitle(categoryList);
//            }
//            @Override
//            public void onError(String errMsg) {
//
//            }
//        });
//    }

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
                        getView().showTitleCity(city);
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

    void onPermissionRequest(int requestCode, String[] permissions, int[] grantResults) {
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, new EasyPermissions.PermissionCallbacks() {
//            @Override
//            public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
//                getCurrentLocation();
//            }
//
//            @Override
//            public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
//                //closeApp
//                getView().closeApp();
//            }
//
//            @Override
//            public void onRequestPermissionsResult(int i, @NonNull String[] strings, @NonNull int[] ints) {
//
//            }
//        });
    }


    void selectLocatedCity(String city) {
        getView().showCityPick(city);
        getCurrentLocation();
    }


}