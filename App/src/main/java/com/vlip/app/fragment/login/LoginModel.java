package com.vlip.app.fragment.login;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class LoginModel extends BaseModel {

    void login(Map<String, Object> params, BaseResponse observer) {
//        RetrofitManager.getInstance().mNetwrokService.login(params)
        RetrofitManager.getInstance().mNetwrokService.login1(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
