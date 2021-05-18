package com.vlip.app.fragment.carriage;

import com.vlip.app.bean.Order;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.kit.FastjsonUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CarriagePresenter extends BasePresenter<OrderListModel, CarriageView> {

    public CarriagePresenter(OrderListModel mModel, CarriageView mView) {
        super(mModel, mView);
    }

    void queryOrderList(int status, int page, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("page", page);
        params.put("limit", limit);
        getModel().queryOrderByStatus(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                JSONObject page = data.optJSONObject("page");
                String jsonList = page.optString("list");
                long currPage = page.optLong("currPage");
                long totalPage = page.optLong("totalPage");
                List<Order> orderList = FastjsonUtils.toList(jsonList, Order.class);
                getView().setOrderList(currPage, totalPage, orderList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

}
