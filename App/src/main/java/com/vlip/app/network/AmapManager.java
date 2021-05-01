package com.vlip.app.network;

import com.amap.api.services.geocoder.GeocodeSearch;
import com.vlip.app.BaseApplication;
import com.vlip.app.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class AmapManager {
    public GeocodeSearch mGeocodeSearch;

    private static volatile AmapManager manager;

    public static AmapManager getInstance() {
        if (manager == null) {
            synchronized (AmapManager.class) {
                if (manager == null) {
                    manager = new AmapManager();
                }
            }
        }
        return manager;
    }

    private AmapManager() {
        try {
            mGeocodeSearch = new GeocodeSearch(BaseApplication.getInstance());

        } catch (Exception ignored) {
        }
    }
}
