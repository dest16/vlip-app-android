package com.vlip.app.fragment.select_pay;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectPayModel extends BaseModel {

    void alipay(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.alipay(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
