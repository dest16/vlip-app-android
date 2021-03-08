package com.vlip.app.fragment.order_list;

import com.vlip.app.bean.Order;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface OrderListView extends BaseView {

    void setOrderList(long currPage, long totalPage, List<Order> orderList);
}
