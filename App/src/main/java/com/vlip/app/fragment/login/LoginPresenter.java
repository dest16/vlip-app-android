package com.vlip.app.fragment.login;

import com.vlip.app.BaseApplication;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Member;
import com.vlip.app.bean.ResultBean;
import com.vlip.app.kit.AppUtils;
import com.vlip.app.network.BaseResponse;
import com.vlip.app.network.WechatLogin;
import com.vlip.app.room.entity.Cart;
import com.vlip.ui.mvp.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

class LoginPresenter extends BasePresenter<LoginModel, LoginView> {

    LoginPresenter(LoginModel mModel, LoginView mView) {
        super(mModel, mView);
    }

    void login(String mobile, String password) {
        Map<String, Object> params = new HashMap<>();
        params.put("username", mobile);
        params.put("password", password);
        getModel().login(params, new BaseResponse() {
            @Override
            public void onSuccess(ResultBean bean) {
                JSONObject data = bean.getJSONObject();
                String expire = data.optString("expiration");
                String token = data.optString("token");
                String imageUrl = data.optString("user.icon");
                String tokenHead = data.optString("tokenHead"); //token拼接串头部
                token = tokenHead + token;
//                JSONObject jsonMember = data.optJSONObject("member");
//                String memberId = jsonMember.optString("id");
//                String mobile = jsonMember.optString("mobile");
//                String sn = jsonMember.optString("sn");
                Member member = new Member();
                member.expire = expire;
                member.token = token;
                member.image = imageUrl;
//                member.memberId = memberId;
                member.mobile = mobile;
//                member.sn = sn;
                AppUtils.saveMember(member);

                //发射登录事件
                EventBus.getDefault().post(new Event.LoginEvent());
                getView().goHome();
//                JSONObject jsonCart = jsonMember.optJSONObject("cart");
//                if (jsonCart != null) {
//                    String cartKey = jsonCart.optString("cartKey");
//                    String jsonList = jsonCart.optString("list");
//                    List<CartItemProduct> cartItemProductList = FastjsonUtils.toList(jsonList, CartItemProduct.class);
//                    if (cartItemProductList != null) {
//                        List<Cart> cartList = new ArrayList<>();
//                        for (CartItemProduct item : cartItemProductList) {
//                            Cart cart = new Cart();
//                            cart.setCartItemId(item.id);
//                            cart.setProductId(item.productId);
//                            cart.setQuantity(item.quantity);
//                            cart.setCartKey(cartKey);
//                            cart.setName(item.name);
//                            cart.setImage(item.image);
//                            cart.setPrice(item.price);
//                            cart.setSpecificationValues(item.specificationValues);
//                            cartList.add(cart);
//                        }
//                        saveDB(cartList);
//                    } else {
//                        getView().goHome();
//                    }
//                } else {
//                    getView().goHome();
//                }
            }

            @Override
            public void onError(String errMsg) {

            }
        });
    }

    void wx_login() {
        WechatLogin.getInstance().login();
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
                        getView().goHome();
                    }
                })
                .subscribe();
    }

}
