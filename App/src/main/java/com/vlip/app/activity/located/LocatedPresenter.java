package com.vlip.app.activity.located;

import com.vlip.ui.mvp.base.BasePresenter;

public class LocatedPresenter extends BasePresenter<LocatedModel,LocatedView> {


    public LocatedPresenter(LocatedView mView) {
        super(mView);
    }

    public LocatedPresenter(LocatedModel mModel, LocatedView mView) {
        super(mModel, mView);
    }
}
