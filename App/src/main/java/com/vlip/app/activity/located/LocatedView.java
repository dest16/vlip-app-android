package com.vlip.app.activity.located;

import android.location.Location;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.vlip.app.bean.Site;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface LocatedView extends BaseView {
    void updateAddress(RegeocodeAddress address);

    void moveMap(Location latLng);

    void updateMarkers(List<Site> list);

    void removeMarkers();
}
