package com.vlip.app.fragment.select_pay;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.ui.mvp.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class SelectPayPresenter extends BasePresenter<SelectPayModel, SelectPayView> {

    public SelectPayPresenter(SelectPayModel mModel, SelectPayView mView) {
        super(mModel, mView);
    }

    void alipay(Long orderId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        getModel().alipay(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                String result = bean.getData();
                getView().alipayResult(result);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }
}
