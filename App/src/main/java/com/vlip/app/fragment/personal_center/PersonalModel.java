package com.vlip.app.fragment.personal_center;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class PersonalModel extends BaseModel {

    void updateIcon(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.updateHeader(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
