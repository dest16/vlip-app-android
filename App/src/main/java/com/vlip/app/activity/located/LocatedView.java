package com.vlip.app.activity.located;

import android.location.Location;

import com.vlip.ui.mvp.base.BaseView;

public interface LocatedView extends BaseView {
    public void updateLocation(Location location);
}
