package com.vlip.app.fragment.address_edit;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressEditModel extends BaseModel {

    void saveAddress(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.saveAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void updateAddress(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.updateAddress(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void queryCurrentLevelAreaById(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryCurrentLevelAreaById(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
