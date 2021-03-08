package com.vlip.app.fragment.publish;

import android.util.Log;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.vlip.ui.mvp.base.BasePresenter;

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

    void getCurrentLocation() {
        getModel().getCurrentLocation(new TencentLocationListener() {
            @Override
            public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
                Log.d("onLocationChanged",tencentLocation.toString());
                getView().setLocationCity(tencentLocation.getCity());
            }

            @Override
            public void onStatusUpdate(String s, int i, String s1) {
                Log.d("onStatusUpdate",s);
            }
        });
    }

}