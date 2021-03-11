package com.vlip.app.fragment.car;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.app.bean.Car;
import com.vlip.app.network.GlideApp;
import com.vlip.kit.FastjsonUtils;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.mvp.IPresenter;

import butterknife.BindView;

public class CarFragment extends BaseFragment {
    @BindView(R.id.car)
    ImageView mImage;
    @BindView(R.id.load)
    TextView mLoad;
    @BindView(R.id.lwh)
    TextView mLwh;
    @BindView(R.id.volume)
    TextView mVolume;

    private Car mCar;

    public static CarFragment newInstance(Car car) {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        args.putString(Constants.INTENT_KEY1, FastjsonUtils.toString(car));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getViewId() {
        return R.layout.fragment_car;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String text1 = getArguments().getString(Constants.INTENT_KEY1);
        mCar = FastjsonUtils.toObject(text1, Car.class);
        GlideApp.with(this).load(Uri.parse(mCar.image)).into(mImage);
        mLoad.setText("载重："+ mCar.load);
        mLwh.setText("长宽高："+ mCar.lwh);
        mVolume.setText("载货体积："+ mCar.volume);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }
}
