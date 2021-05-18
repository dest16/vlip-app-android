package com.vlip.app.fragment.register;

import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.WechatLogin;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class RegisterPresenter extends BasePresenter<RegisterModel, RegisterView> {

    RegisterPresenter(RegisterModel mModel, RegisterView mView) {
        super(mModel, mView);
    }


    /*
  "email": "string",
  "icon": "string",
  "nickName": "string",
  "note": "string",
  "password": "string",
  "username": "string"
     */
    void register(String username, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        params.put("email", "123@123.com");
        params.put("icon", "https://imgsa.baidu.com/forum/pic/item/d9f9d72a6059252daecdfc36309b033b5bb5b92e.jpg");
        params.put("note", "note");
        params.put("nickName", username);
        getView().showLoading();
        getModel().register(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                int code = data.optInt("code");
                if (code != 0) {
                    String message = data.optString("message");
                    ToastUtils.showToast(message);
                }else{
                    ToastUtils.showToast("注册成功");
                    getView().hideLoading();
                    getView().backToLogin();
                }

            }

            @Override
            public void onError(String errMsg) {
                ToastUtils.showToast(errMsg);
            }
        });
    }

    void wx_login() {
        WechatLogin.getInstance().login();
    }


}
