package com.vlip.app.fragment.address;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddressModel extends BaseModel {

    void queryAddressList(BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryAddressList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
