package com.vlip.app.fragment.goods_list;

import com.vlip.app.bean.Goods;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface GoodsListView extends BaseView {

    void setGoodsList(long currPage, long totalPage, List<Goods> goodsList);

}
