package com.vlip.app.fragment.publish;

import com.vlip.ui.mvp.base.BaseView;
import com.zaaach.citypicker.model.LocatedCity;

public interface PublishView extends BaseView {

    void showTitleCity(LocatedCity city);

    void updateLocatedCity(LocatedCity city);

    void askPermission(String title, String... permissions);

    void showCityPick(String city);

    void closeApp();
}
