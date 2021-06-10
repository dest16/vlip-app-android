package com.vlip.app.fragment.order_detail;

import com.vlip.app.bean.Event;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailPresenter extends BasePresenter<OrderDetailModel, OrderDetailView> {

    public OrderDetailPresenter(OrderDetailModel mModel, OrderDetailView mView) {
        super(mModel, mView);
    }

    void acceptOrder(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("oid", id);
        getModel().acceptOrder(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
//                getView().refresh();
                EventBus.getDefault().post(new Event.TakeOrdersEvent());
                getView().goBack();
            }

            @Override
            public void onError(String errMsg) {
                ToastUtils.showToast(errMsg);
            }
        });
    }


    void cancelOrder(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("oid", id);
        getModel().cancelOrder(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
//                getView().refresh();
                EventBus.getDefault().post(new Event.CancelOrdersEvent());
                getView().goBack();
            }

            @Override
            public void onError(String errMsg) {
                ToastUtils.showToast(errMsg);
            }
        });
    }
}
