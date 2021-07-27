package com.vlip.app.fragment.update_pass;


import com.vlip.app.bean.ResultBean;
import com.vlip.app.kit.AppUtils;
import com.vlip.app.network.BaseResponse;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import java.util.HashMap;
import java.util.Map;

public class UpdatePassPresenter extends BasePresenter<UpdatePassModel, UpdatePassView> {
    UpdatePassPresenter(UpdatePassModel mModel, UpdatePassView mView) {
        super(mModel, mView);
    }

    void updatePass(String oldPass, String newPass) {
        Map<String, Object> params = new HashMap<>();
        params.put("newPassword", newPass);
        params.put("oldPassword", oldPass);
        params.put("username", AppUtils.getMember().mobile);
        getView().showLoading();
        getModel().update(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                getView().hideLoading();
                getView().goBack();
            }

            @Override
            public void onError(String errMsg) {
                getView().hideLoading();
                ToastUtils.showToast(errMsg);
            }
        });
    }
}
