package com.vlip.app.activity.located;

import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.CustomMapStyleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Position;
import com.vlip.app.bean.Site;
import com.vlip.kit.DPUtils;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.activity.base.BaseActivity;
import com.vlip.ui.row.RowInputEdit;
import com.vlip.ui.row.RowSettingText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LocatedActivity extends BaseActivity<LocatedPresenter> implements LocatedView, MaterialSearchView.OnQueryTextListener, Inputtips.InputtipsListener, AdapterView.OnItemClickListener {
    @BindView(R.id.map_view)
    MapView mMapView;
    @BindView(R.id.address)
    RowSettingText mAddress;
    @BindView(R.id.remarks)
    RowInputEdit mRemarks;
    @BindView(R.id.person)
    RowInputEdit mPerson;
    @BindView(R.id.phone)
    RowInputEdit mPhone;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_view)
    MaterialSearchView searchView;

    private AMap mAmap;
    private Location current;
    private List<Marker> mMarkers = new ArrayList<>();
    private List<Site> mSites;
    private Marker locationMarker;
    private LocatedSearchAdapter mTipsAdapter;
    private int pinResId;

    private final AMap.OnMyLocationChangeListener mOnMyLocationChangeListener = location -> {
        if (current == null) {
            moveMap(new LatLng(location.getLatitude(), location.getLongitude()));
            getPresenter().getAddressInfo(new LatLonPoint(location.getLatitude(), location.getLongitude()));
        }
        current = location;
    };
    private final AMap.OnCameraChangeListener onCameraChangeListener = new AMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {

        }

        @Override
        public void onCameraChangeFinish(CameraPosition cameraPosition) {
            if (locationMarker != null) {
                getPresenter().getAddressInfo(new LatLonPoint(locationMarker.getPosition().latitude, locationMarker.getPosition().longitude));
            }
        }
    };

    @Override
    public int getViewId() {
        return R.layout.activity_located;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        searchView.setOnQueryTextListener(this);
        searchView.setOnItemClickListener(this);
        return true;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        pinResId = getIntent().getExtras().getString(Constants.INTENT_KEY1).equals("from") ? R.mipmap.pin_start_128 : R.mipmap.pin_end_128;
        int space = DPUtils.dp2px(getResources(), 10);
        mToolbar.setContentInsetsRelative(space, space);
        mToolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(com.vlip.ui.R.drawable.ic_left_arrow);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mMapView.onCreate(savedInstanceState);
        if (mAmap == null)
            mAmap = mMapView.getMap();
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(17.5f));
        mAmap.setCustomMapStyle(
                new CustomMapStyleOptions()
                        .setEnable(true)
                        .setStyleId("22d591dd8593bacec3f550198b825f47")//???????????????-??????????????? ??????
        );
        MyLocationStyle myLocationStyle = new MyLocationStyle();//??????????????????????????????myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????1???1???????????????????????????myLocationType????????????????????????????????????
        myLocationStyle.interval(3000); //???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.showMyLocation(false);
        mAmap.getUiSettings().setTiltGesturesEnabled(false);
        mAmap.getUiSettings().setRotateGesturesEnabled(false);
        mAmap.getUiSettings().setGestureScaleByMapCenter(true);
        mAmap.setMyLocationStyle(myLocationStyle);//?????????????????????Style
        mAmap.getUiSettings().setMyLocationButtonEnabled(true); //?????????????????????????????????????????????????????????
        mAmap.setMyLocationEnabled(true);// ?????????true?????????????????????????????????false??????????????????????????????????????????????????????false???
        mAmap.addOnMyLocationChangeListener(mOnMyLocationChangeListener);
        mAmap.addOnCameraChangeListener(onCameraChangeListener);
        mAmap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                updateAddress(marker.getTitle(), marker.getSnippet());
                mAddress.setTag(marker);
                return false;
            }
        });
        mAmap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
            @Override
            public void onMapLoaded() {
                addMarkerInScreenCenter();
//                markerOverlay = new MarkerOverlay(mAmap, )
//                getPresenter().getSiteMarkets();
            }
        });
    }

    @Override
    public LocatedPresenter createPresenter() {
        return new LocatedPresenter(new LocatedModel(), this);
    }

    @Override
    public void initData() {
    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                Position p = new Position();
//                Marker marker = (Marker) mAddress.getTag();
                Marker marker = locationMarker;
                p.lat = marker.getPosition().latitude;
                p.lon = marker.getPosition().longitude;
                p.title= mAddress.getTitle();
                p.subTitle= mAddress.getSummary();
//                p.title = marker.getTitle();
//                p.subTitle = marker.getSnippet();
                p.name = mPerson.getText().trim();
                p.phone = mPhone.getText().trim();
                p.type = getIntent().getExtras().getString(Constants.INTENT_KEY1);
                if (p.name.isEmpty() || p.phone.isEmpty()) {
                    ToastUtils.showToast("????????????????????????");
                } else {
                    EventBus.getDefault().post(new Event.LocationEvent(p));
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //???activity??????onDestroy?????????mMapView.onDestroy()???????????????
        if (mMapView != null)
            mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //???activity??????onResume?????????mMapView.onResume ()???????????????????????????
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //???activity??????onPause?????????mMapView.onPause ()????????????????????????
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //???activity??????onSaveInstanceState?????????mMapView.onSaveInstanceState (outState)??????????????????????????????
        mMapView.onSaveInstanceState(outState);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void updateAddress(String title, String subTitle) {
        mAddress.setTitle(title);
        mAddress.setSummary(subTitle);
    }

    @Override
    public void moveMap(LatLng location) {
        mAmap.animateCamera(CameraUpdateFactory.changeLatLng(location));
    }

    @Override
    public void updateMarkers(List<Site> list) {
        mSites = list;
        removeMarkers();
        for (int i = 0; i < list.size(); i++) {
            Marker marker = mAmap.addMarker(new MarkerOptions()
                    .position(new LatLng(list.get(i).lat, list.get(i).lon))
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            marker.setTitle(list.get(i).title);
            marker.setSnippet(list.get(i).subTitle);
            marker.setObject(i);
            mMarkers.add(marker);
        }
        zoomToSpan();
    }

    @Override
    public void removeMarkers() {
        for (Marker mark : mMarkers) {
            mark.remove();
        }
        mMarkers.clear();
    }

    /**
     * ??????????????????????????????????????????marker?????????????????????
     */
    public void zoomToSpan() {
        if (mSites != null && mSites.size() > 0) {
            if (mAmap == null)
                return;
            LatLngBounds bounds = getLatLngBounds(getLatLngFromSites(mSites));
            mAmap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        }
    }

    /**
     * ?????????????????????????????????bounds
     */
    private LatLngBounds getLatLngBounds(List<LatLng> pointList) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < pointList.size(); i++) {
            LatLng p = pointList.get(i);
            b.include(p);
        }
        return b.build();
    }

    private List<LatLng> getLatLngFromSites(List<Site> sites) {
        List<LatLng> list = new ArrayList<>();
        for (Site site : sites) {
            LatLng la = new LatLng(site.lat, site.lon);
            list.add(la);
        }
        return list;
    }

    private void addMarkerInScreenCenter() {
        Point screenPosition = mAmap.getProjection().toScreenLocation(mAmap.getCameraPosition().target);
        locationMarker = mAmap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(pinResId)));
        //??????Marker????????????,?????????????????????
        locationMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
        locationMarker.setZIndex(1);
    }

    private boolean IsEmptyOrNullString(String s) {
        return (s == null) || (s.trim().length() == 0);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!IsEmptyOrNullString(newText)) {
            InputtipsQuery inputquery = new InputtipsQuery(newText, "??????");
            Inputtips inputTips = new Inputtips(getApplicationContext(), inputquery);
            inputTips.setInputtipsListener(this);
            inputTips.requestInputtipsAsyn();
        } else {
            if (mTipsAdapter != null) {
                mTipsAdapter.notifyData(null);
            }
        }
        return false;
    }

    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == 1000) {// ????????????
            List<Tip> tempList = new ArrayList<>();
            for (Tip tip : tipList) {
                if (tip.getPoint() != null) {
                    tempList.add(tip);
                }
            }
            if (mTipsAdapter == null) {
                mTipsAdapter = new LocatedSearchAdapter(getApplicationContext(), tempList);
                searchView.setAdapter(mTipsAdapter);
            } else {
                mTipsAdapter.notifyData(tempList);
            }
            searchView.showSuggestions();
        } else {
            ToastUtils.showToast("??????:" + rCode);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LatLonPoint selected = mTipsAdapter.getItem(position).getPoint();
        moveMap(new LatLng(selected.getLatitude(), selected.getLongitude()));
        searchView.closeSearch();
    }
}
