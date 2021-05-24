package com.vlip.app.fragment.accept;

import com.vlip.app.bean.Order2;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface AcceptView extends BaseView {

    void setOrderList(long currPage, long totalPage, List<Order2> orderList);

    void refresh();
}
