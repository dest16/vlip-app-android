package com.vlip.app.fragment.address;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.bean.Address;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.List;

public class AddressPresenter extends BasePresenter<AddressModel, AddressView> {

    public AddressPresenter(AddressModel mModel, AddressView mView) {
        super(mModel, mView);
    }

    void queryAddressList() {
        getModel().queryAddressList(new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<Address> addressList = FastjsonUtils.toList(jsonList, Address.class);
                getView().setAddressList(addressList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

}
