package com.vlip.app.activity.select_area;

import com.vlip.app.bean.Area;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface SelectAreaView extends BaseView {

    void setLevelAreaList(List<Area> areaList);

    void setParentAreaList(List<Area> areaList);

}
