package com.vlip.app.fragment.order_detail;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailModel extends BaseModel {
    void acceptOrder(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.acceptOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
