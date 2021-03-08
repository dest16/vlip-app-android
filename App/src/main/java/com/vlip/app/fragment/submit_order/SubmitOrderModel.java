package com.vlip.app.fragment.submit_order;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubmitOrderModel extends BaseModel {

    void getDefaultAddress(BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.getDefaultAddress()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void submitOrder(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.submitOrder(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
