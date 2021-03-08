package com.vlip.app.activity.home;

import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BaseModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeModel extends BaseModel {

    void getBadgeCount(SingleObserver<Integer> observer) {
        BaseApplication.getInstance()
                .getCartDao()
                .getBadgeCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
