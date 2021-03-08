package com.vlip.app.activity.map;

import com.vlip.ui.mvp.base.BasePresenter;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class LocatedPresenter extends BasePresenter<LocatedModel, LocatedView> {

    public LocatedPresenter(LocatedModel mModel, LocatedView mView) {
        super(mModel, mView);
    }

    void getBadgeCount() {
        getModel().getBadgeCount(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onSuccess(Integer count) {
                getView().setBadgeCount(count);
            }

            @Override
            public void onError(Throwable e) {
                getView().setBadgeCount(0);
            }
        });
    }

}
