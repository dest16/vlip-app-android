package com.vlip.app.fragment.cart;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.bean.Goods;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.room.entity.Cart;
import com.vlip.ui.mvp.base.BasePresenter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.functions.Consumer;

class CartPresenter extends BasePresenter<CartModel, CartView> {

    CartPresenter(CartModel mModel, CartView mView) {
        super(mModel, mView);
    }

    void queryGoodsByLike(long page, long limit) {
        Map<String, Object> params = new HashMap<>();
        params.put("page", page);
        params.put("limit", limit);
        getModel().queryGoodsByLike(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                JSONObject page = data.optJSONObject("page");
                String jsonList = page.optString("list");
                List<Goods> goodsList = FastjsonUtils.toList(jsonList, Goods.class);
                getView().setGoodsList(goodsList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void queryAllCart() {
        getModel().queryAllCart(new Consumer<List<Cart>>() {
            @Override
            public void accept(List<Cart> cartList) throws Exception {
                getView().setCartList(cartList);
            }
        });
    }
}
