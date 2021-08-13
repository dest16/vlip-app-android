package com.vlip.app.fragment.order_list;

import com.vlip.app.bean.Order2;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.kit.FastjsonUtils;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderListPresenter extends BasePresenter<OrderListModel, OrderListView> {

    public OrderListPresenter(OrderListModel mModel, OrderListView mView) {
        super(mModel, mView);
    }

    void queryOrderList(int status, int page, int limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("status", status);
        params.put("pageNum", page);
        params.put("pageSize", limit);
        getModel().queryOrdersByStatus(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
//                JSONObject page = data.optJSONObject("data");
                String jsonList = data.optString("list");
                long currPage = data.optLong("pageNum");
                long totalPage = data.optLong("totalPage");
                List<Order2> orderList = FastjsonUtils.toList(jsonList, Order2.class);
                getView().setOrderList(currPage, totalPage, orderList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }


    void cancelOrder(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("oid", id);
        getModel().cancelOrder(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                getView().refresh();

            }

            @Override
            public void onError(String errMsg) {
                ToastUtils.showToast(errMsg);
            }
        });
    }

}
