package com.vlip.app.fragment.cart;

import android.arch.lifecycle.LifecycleOwner;

import com.vlip.app.BaseApplication;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.RetrofitManager;
import com.vlip.app.room.entity.Cart;
import com.vlip.ui.mvp.IPresenter;
import com.vlip.ui.mvp.base.BaseModel;

import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

class CartModel extends BaseModel implements IPresenter {

    CompositeDisposable mDisposable = new CompositeDisposable();

    void queryGoodsByLike(Map<String, Object> params, BaseResponse observer) {
        RetrofitManager.getInstance().mNetwrokService.queryGoodsByLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    void queryAllCart(Consumer<List<Cart>> consumer) {
        mDisposable.add(BaseApplication.getInstance()
                .getCartDao()
                .queryAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(consumer)
                .subscribe());
    }

    @Override
    public void onDestroy(LifecycleOwner owner) {
        super.onDestroy(owner);
        mDisposable.clear();
    }
}
