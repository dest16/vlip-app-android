package com.vlip.app.fragment.address;

import com.vlip.app.bean.Address;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface AddressView extends BaseView {

    void setAddressList(List<Address> addressList);
}
