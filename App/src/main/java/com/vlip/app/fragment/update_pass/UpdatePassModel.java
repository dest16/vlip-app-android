package com.vlip.app.fragment.update_pass;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePassModel extends BaseModel {

    void update(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.updatePassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
