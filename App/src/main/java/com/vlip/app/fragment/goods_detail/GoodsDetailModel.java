package com.vlip.app.fragment.goods_detail;

import com.vlip.app.BaseApplication;
import com.vlip.ui.mvp.base.BaseModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GoodsDetailModel extends BaseModel {

    void getBadgeCount(SingleObserver<Integer> observer) {
        BaseApplication.getInstance()
                .getCartDao()
                .getBadgeCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
