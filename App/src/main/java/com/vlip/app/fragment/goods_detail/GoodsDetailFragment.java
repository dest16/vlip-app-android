package com.vlip.app.fragment.goods_detail;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vlip.kit.FastjsonUtils;
import com.vlip.kit.StatusBarUtils;
import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.activity.select_spec.SelectSpecActivity;
import com.vlip.app.bean.Event;
import com.vlip.app.bean.Goods;
import com.vlip.app.bean.GoodsImage;
import com.vlip.app.fragment.cart.CartFragment;
import com.vlip.app.fragment.submit_order.SubmitOrderFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.ui.activity.BaseFragmentActivity;
import com.vlip.ui.adapter.viewpager.BaseBannerPagerAdapter;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.viewpager.BannerViewPager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class GoodsDetailFragment extends BaseFragment<GoodsDetailPresenter> implements GoodsDetailView {

    @BindView(R.id.banner_view_pager)
    BannerViewPager mBannerViewPager;

    @BindView(R.id.go_cart)
    TextView mGoCart;

    @BindView(R.id.indicator)
    TextView mIndicator;

    @BindView(R.id.price)
    TextView mPrice;

    @BindView(R.id.name)
    TextView mName;

    @BindView(R.id.select_value)
    TextView mSelectValue;

    @BindView(R.id.address_value)
    TextView mAddressValue;

    @BindView(R.id.freight_value)
    TextView mFreightValue;

    Goods mGoods;

    Badge mBdage;

    @Override
    public int getViewId() {
        return R.layout.fragment_goods_detail;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        assert getArguments() != null;
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        mGoods = FastjsonUtils.toObject(text1, Goods.class);
        List<GoodsImage> data = FastjsonUtils.toList(mGoods.goodsImages, GoodsImage.class);
        mBannerViewPager.setAdapter(new BaseBannerPagerAdapter<GoodsImage>(data) {

            @Override
            public void convert(ImageView image, GoodsImage item, int position) {
                image.setScaleType(ImageView.ScaleType.MATRIX);
                AppUtils.loadImage(item.url, image);
            }

        });
        mIndicator.setText(String.format("%d/%d", mBannerViewPager.getCurrentItem() + 1, mBannerViewPager.getCount()));
        mBannerViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                mIndicator.setText(String.format("%d/%d", i + 1, mBannerViewPager.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }

        });
    }

    @Override
    public void initData() {
        setSupportEventBus();
        StatusBarUtils.setTranslucentStatus(getActivity()); //???????????????
        StatusBarUtils.setStatusBarDarkTheme(getActivity(), true); //?????????????????????
        mPrice.setText(AppUtils.toRMBFormat(mGoods.price));
        mName.setText(mGoods.name);
        mSelectValue.setText(String.format("???????????????%s", AppUtils.getSelectSpecItem(mGoods.specificationItems)));
        mAddressValue.setText("???????????????");
        mFreightValue.setText("?????????");
        mBdage = new QBadgeView(getContext()).bindTarget(mGoCart);
        getPresenter().getBadgeCount();
    }

    @Override
    public GoodsDetailPresenter createPresenter() {
        return new GoodsDetailPresenter(new GoodsDetailModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.back, R.id.select_value, R.id.address_value, R.id.freight_value, R.id.contact, R.id.go_cart, R.id.add_cart, R.id.buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select_value:
                Bundle args = new Bundle();
                args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(mGoods));
                goIntent(SelectSpecActivity.class, args);
                break;
            case R.id.address_value:

                break;
            case R.id.freight_value:

                break;
            case R.id.contact:

                break;
            case R.id.go_cart:
                args = new Bundle();
                args.putBoolean(Constants.INTENT_KEY1, true);
                BaseFragmentActivity.createFragmentNewTask(requireContext(), CartFragment.class, args);
                break;
            case R.id.add_cart:
                if (mGoods.hasSpecification()) { //?????????
                    args = new Bundle();
                    args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(mGoods));
                    goIntent(SelectSpecActivity.class, args);
                } else { //????????????

                }
                break;
            case R.id.buy:
                if (mGoods.hasSpecification()) { //?????????
                    args = new Bundle();
                    args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(mGoods));
                    goIntent(SelectSpecActivity.class, args);
                } else { //????????????
                    args = new Bundle();
                    args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(mGoods));
                    goIntent(SubmitOrderFragment.class, args);
                    finish();
                }
                break;
        }
    }

    @Override
    public void setBadgeCount(int count) {
        mBdage.setBadgeNumber(count);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.CartEvent event) {
        getPresenter().getBadgeCount();
    }
}
