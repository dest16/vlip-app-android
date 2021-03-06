package com.vlip.app.activity.select_spec;

import com.vlip.kit.FastjsonUtils;
import com.vlip.app.BaseApplication;
import com.vlip.app.bean.CartItemProduct;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Product;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.bean.SpecificationValue;
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
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

class SelectSpecPresenter extends BasePresenter<SelectSpecModel, SelectSpecView> {

    SelectSpecPresenter(SelectSpecModel mModel, SelectSpecView mView) {
        super(mModel, mView);
    }

    void getProductBySpec(Long goodsId, List<SpecificationValue> specValueList) {
        Map<String, Object> params = new HashMap<>();
        params.put("goodsId", goodsId);
        params.put("specValueList", specValueList);
        getModel().getProductBySpec(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String json = data.optString("product");
                Product product = FastjsonUtils.toObject(json, Product.class);
                getView().setProduct(product);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void saveCartWithDB(Long productId, int quantity) {
        getModel().getKeyCart(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            @Override
            public void onSuccess(String cartKey) { // ????????????
                saveCart(cartKey, productId, quantity);
            }

            @Override
            public void onError(Throwable e) { // ???????????????
                saveCart("", productId, quantity);
            }
        });
    }

    private void saveCart(String cartKey, Long productId, int quantity) {
        Map<String, Object> params = new HashMap<>();
        params.put("productId", productId);
        params.put("quantity", quantity);
        getModel().saveCart(cartKey, params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String jsonList = data.optString("list");
                List<Cart> cartList = new ArrayList<>();
                List<CartItemProduct> cartItemProductList = FastjsonUtils.toList(jsonList, CartItemProduct.class);
                for (CartItemProduct item : cartItemProductList) {
                    if (!productId.equals(item.productId)) {
                        continue;
                    }
                    Cart cart = new Cart();
                    cart.setCartItemId(item.id);
                    cart.setProductId(item.productId);
                    cart.setQuantity(item.quantity);
                    cart.setCartKey(cartKey);
                    cart.setName(item.name);
                    cart.setImage(item.image);
                    cart.setPrice(item.price);
                    cart.setSpecificationValues(item.specificationValues);
                    cartList.add(cart);
                }
                saveDB(cartList);
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    private void saveDB(List<Cart> cartList) {
        Observable.empty()
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        BaseApplication.getInstance().getCartDao().addOrReplaceList(cartList);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        EventBus.getDefault().post(new Event.CartEvent());
                        getView().goBack();
                    }
                })
                .subscribe();
    }
}
