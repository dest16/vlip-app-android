package com.vlip.app.activity.located;

import android.location.Location;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.vlip.ui.mvp.base.BaseView;

public interface LocatedView extends BaseView {
    public void updateAddress(RegeocodeAddress address);

    public void moveMap(Location latLng);
}
