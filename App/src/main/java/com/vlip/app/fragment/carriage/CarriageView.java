package com.vlip.app.fragment.carriage;

import com.vlip.app.bean.Order;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface CarriageView extends BaseView {

    void setOrderList(long currPage, long totalPage, List<Order> orderList);
}
