package com.vlip.app.activity.located;

import android.location.Location;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.vlip.app.BaseApplication;
import com.vlip.app.bean.Site;
import com.vlip.ui.mvp.base.BasePresenter;

import java.util.List;

public class LocatedPresenter extends BasePresenter<LocatedModel, LocatedView> {


    public LocatedPresenter(LocatedModel mModel, LocatedView mView) {
        super(mModel, mView);
    }

    public void getAddressInfo(LatLonPoint latLonPoint) {
        GeocodeSearch search = new GeocodeSearch(BaseApplication.getInstance());
        search.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                List<PoiItem> poiItems = address.getPois();
                for (int j = 0; j < poiItems.size(); j++) {
                    PoiItem item = poiItems.get(j);
                    Log.d("poiItems", "Title=" + item.getTitle() + ",Distance=" + item.getDistance() + ",AdName=" + item.getAdName() + ",Tel" + item.getTel()
                            + ",BusinessArea=" + item.getBusinessArea() + ",Snippet=" + item.getSnippet() + ",Direction" + item.getDirection()
                            + ",TypeDes" + item.getTypeDes());
                }
//                String title;
//                if (address.getAois().size() > 0) {
//                    title = address.getAois().get(0).getAoiName();
//                } else {
//                    title = address.getPois().get(0).getTitle();
//                }
                String address1 = poiItems.get(0).getTitle() + "附近";
                String address2 = address.getFormatAddress();
                getView().updateAddress(address1, address2);
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200, GeocodeSearch.AMAP);
        query.setExtensions(PoiSearch.EXTENSIONS_ALL);
        search.getFromLocationAsyn(query);
    }

    public void getCurrentLocation() {
        getModel().startQueryCurrentLocation(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation.getErrorCode() == 0) {
                    Location location = new Location(aMapLocation.getProvider());
                    //设置经纬度以及精度
                    location.setLatitude(aMapLocation.getLatitude());
                    location.setLongitude(aMapLocation.getLongitude());
                    location.setAccuracy(aMapLocation.getAccuracy());
//                    LatLng latLng = new LatLng(tencentLocation.getLatitude(), tencentLocation.getLongitude());
//                    addMarker(latLng);
                    //将位置信息返回给地图
//                    locationChangedListener.onLocationChanged(location);
//                    getView().updateAddress(location);
                }
            }
        });
    }


    public void getSiteMarkets() {
        List<Site> list = Site.list();
        getView().updateMarkers(list);
    }
}
