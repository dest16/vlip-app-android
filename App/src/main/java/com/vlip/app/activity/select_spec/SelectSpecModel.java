package com.vlip.app.activity.select_spec;

import com.vlip.app.BaseApplication;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectSpecModel extends BaseModel {

    void getProductBySpec(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.getProductBySpec(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void getKeyCart(SingleObserver<String> observer) {
        BaseApplication.getInstance()
                .getCartDao()
                .getCartKey()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void saveCart(String cartKey, Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.saveCart(cartKey, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
