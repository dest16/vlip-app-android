package com.vlip.app.fragment.publish;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.kit.AppUtils;
import com.vlip.kit.DPUtils;
import com.vlip.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 购物车
 *
 * @author zm
 */
public class PublishFragment extends BaseFragment<PublishPresenter> implements PublishView {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getViewId() {
        return R.layout.fragment_publish;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        LinearLayoutHelper linearLayoutHelper = new LinearLayoutHelper();
        int space = DPUtils.dp2px(getResources(), 10);
        linearLayoutHelper.setBgColor(AppUtils.getColor(R.color.colorLine));
        linearLayoutHelper.setMarginBottom(space);
        linearLayoutHelper.setDividerHeight(DPUtils.dp2px(getResources(), 1));
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getPresenter().getCurrentLocation();
//        if (getArguments() != null && getArguments().getBoolean(Constants.INTENT_KEY1, false)) {
//            int space = DPUtils.dp2px(getResources(), 10);
//            mToolbar.setContentInsetsRelative(space, space);
//            mToolbar.setContentInsetStartWithNavigation(0);
//            mToolbar.setNavigationIcon(com.vlip.ui.R.drawable.ic_left_arrow);
//            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }
//        mToolbar.setTitle("购物车");
//        getPresenter().queryAllCart();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Event.LocationEvent event) {

    }

    @Override
    public PublishPresenter createPresenter() {
        return new PublishPresenter(new PublishModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }


    @Override
    public void setLocationCity(String city) {
        mToolbar.setTitle(city);
    }
}
