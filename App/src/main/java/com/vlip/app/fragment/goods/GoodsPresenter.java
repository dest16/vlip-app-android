package com.vlip.app.fragment.goods;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.bean.GoodsCategory;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GoodsPresenter extends BasePresenter<GoodsModel, GoodsView> {

    GoodsPresenter(GoodsModel mModel, GoodsView mView) {
        super(mModel, mView);
    }

    void queryTreeCategory() {
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", "1");
        getModel().getTreeCategory(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<GoodsCategory> categoryList = FastjsonUtils.toList(jsonList, GoodsCategory.class);
                getView().setCategoryTitle(categoryList);
            }
            @Override
            public void onError(String errMsg) {

            }
        });
    }
}