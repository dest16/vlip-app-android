package com.vlip.app.fragment.address_edit;

import com.vlip.app.bean.Area;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface AddressEditView extends BaseView {

    void saveUpdateSuccess();

    void setSelectAreaList(List<Area> areaList);

}
