package com.vlip.app.activity.select_spec;

import com.vlip.app.bean.Product;
import com.vlip.ui.mvp.base.BaseView;


public interface SelectSpecView extends BaseView {

    void setProduct(Product product);

    void goBack();

}
