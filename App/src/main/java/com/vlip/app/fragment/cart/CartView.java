package com.vlip.app.fragment.cart;

import com.vlip.app.bean.Goods;
import com.vlip.app.room.entity.Cart;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface CartView extends BaseView {

    void setCartList(List<Cart> cartList);

    void setGoodsList(List<Goods> goodsList);
}
