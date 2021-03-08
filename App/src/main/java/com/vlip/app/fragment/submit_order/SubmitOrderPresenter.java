package com.vlip.app.fragment.submit_order;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.BaseApplication;
import com.vlip.app.bean.Address;
import com.vlip.app.bean.BuyItem;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Order;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.room.entity.Cart;
import com.vlip.ui.mvp.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class SubmitOrderPresenter extends BasePresenter<SubmitOrderModel, SubmitOrderView> {


    public SubmitOrderPresenter(SubmitOrderModel mModel, SubmitOrderView mView) {
        super(mModel, mView);
    }

    void getDefaultAddress() {
        getModel().getDefaultAddress(new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String json = data.optString("address");
                Address address = FastjsonUtils.toObject(json, Address.class);
                getView().setAddress(address);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void submitOrder(Long addressId, String invoiceTitle, String invoiceContent, String memo, List<Cart> cartList) {
        Map<String, Object> params = new HashMap<>();
        params.put("addressId", addressId);
        params.put("invoiceTitle", invoiceTitle);
        params.put("invoiceContent", invoiceContent);
        params.put("memo", memo);
        List<BuyItem> buyItemList = new ArrayList<>();
        for (Cart cart : cartList) {
            BuyItem buyItem = new BuyItem();
            buyItem.cartItemId = cart.getCartItemId();
            buyItem.productId = cart.getProductId();
            buyItem.quantity = cart.getQuantity();
            buyItemList.add(buyItem);
        }
        params.put("buyList", buyItemList);
        getModel().submitOrder(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String json = data.optString("order");
                Order order = FastjsonUtils.toObject(json, Order.class);
                getView().goPay(order);
                clearCart(cartList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    private void clearCart(List<Cart> cartList) {
        Observable.empty()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        BaseApplication.getInstance().getCartDao().deleteList(cartList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().post(new Event.CartEvent());
                    }
                })
                .subscribe();
    }

}
