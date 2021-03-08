package com.vlip.ui.mvp.base;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.vlip.ui.mvp.IModel;

/**
 * 数据层基类
 * @author zm
 */
public class BaseModel implements IModel {

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy(LifecycleOwner owner) {
        owner.getLifecycle().removeObserver(this);
    }

}
