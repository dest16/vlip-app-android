package com.vlip.app.activity.select_area;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.bean.Area;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectAreaPresenter extends BasePresenter<SelectAreaModel, SelectAreaView> {

    public SelectAreaPresenter(SelectAreaModel mModel, SelectAreaView mView) {
        super(mModel, mView);
    }

    void queryAreaByLevel(int level) {
        Map<String, Object> params = new HashMap<>();
        params.put("level", level);
        getModel().queryAreaByLevel(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<Area> areaList = FastjsonUtils.toList(jsonList, Area.class);
                getView().setLevelAreaList(areaList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void queryAreaByParentId(long parentId) {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        getModel().queryAreaByParentId(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<Area> areaList = FastjsonUtils.toList(jsonList, Area.class);
                getView().setParentAreaList(areaList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

}
