package com.vlip.app.activity.located;

import android.location.Location;

import com.vlip.app.bean.Site;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface LocatedView extends BaseView {
    void updateAddress(String title,String subTitle);

    void moveMap(Location latLng);

    void updateMarkers(List<Site> list);

    void removeMarkers();
}
