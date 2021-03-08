package com.vlip.app.fragment.goods;

import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class GoodsModel extends BaseModel {

    void getTreeCategory(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryTreeCategory(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
