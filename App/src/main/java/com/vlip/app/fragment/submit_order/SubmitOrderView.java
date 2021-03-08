package com.vlip.app.fragment.submit_order;

import com.vlip.app.bean.Address;
import com.vlip.app.bean.Order;
import com.vlip.ui.mvp.base.BaseView;

public interface SubmitOrderView extends BaseView {

    void setAddress(Address address);

    void goPay(Order order);

}
