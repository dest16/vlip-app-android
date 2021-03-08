package com.vlip.app.fragment.goods;

import com.vlip.app.bean.GoodsCategory;
import com.vlip.ui.mvp.base.BaseView;

import java.util.List;

public interface GoodsView extends BaseView {

    void setCategoryTitle(List<GoodsCategory> list);

}
