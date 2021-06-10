package com.vlip.app.fragment.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.vlip.app.R;
import com.vlip.app.bean.Event;
import com.vlip.app.fragment.login.LoginFragment;
import com.vlip.app.fragment.web.WebFragment;
import com.vlip.app.kit.AppUtils;
import com.vlip.app.network.GlideCacheUtil;
import com.vlip.kit.DPUtils;
import com.vlip.ui.activity.ToolbarFragmentActivity;
import com.vlip.ui.dialog.CommonDialog;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.mvp.base.BasePresenter;
import com.vlip.ui.row.RowSettingText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

import static com.vlip.app.Constants.INTENT_KEY1;
import static com.vlip.app.Constants.TEST_BASE_URL;
import static com.vlip.app.Constants.WEB_ABOUT_PATH;
import static com.vlip.app.Constants.WEB_HELP_PATH;
import static com.vlip.app.Constants.WEB_SERVICE_PATH;

public class SettingFragment extends BaseFragment {

    @BindView(R.id.exit_btn)
    Button mExitBtn;

    @BindView(R.id.clear_cache)
    RowSettingText mClearCache;

    @Override
    public int getViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mClearCache.setStatus(GlideCacheUtil.getInstance().getCacheSize(getContext()));
        mClearCache.setRightImage(false);
        int space = DPUtils.dp2px(getResources(), 16);
        mClearCache.setStatusPadding(0, 0, space, 0);
    }

    @Override
    public void initData() {
        setSupportEventBus();
        getToolbar().setTitle("设置");
        if (AppUtils.isLogin()) {
            mExitBtn.setVisibility(View.VISIBLE);
        } else {
            mExitBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @OnClick({R.id.clear_cache, R.id.help, R.id.service, R.id.about, R.id.exit_btn})
    public void onClick(View view) {
        String url = "";
        switch (view.getId()) {
            case R.id.clear_cache:
                CommonDialog dialog = new CommonDialog(getContext());
                dialog.setTitle("清理缓存");
                dialog.setContent("你确认要清理应用缓存？");
                dialog.setOnClickCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                dialog.setOnClickSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        GlideCacheUtil.getInstance().clearCache(getContext());
                    }
                });
                dialog.show();
                break;
            case R.id.help:
                url = TEST_BASE_URL + WEB_HELP_PATH;
                Bundle help = new Bundle();
                help.putString(INTENT_KEY1, url);
                ToolbarFragmentActivity.createFragment(getContext(), WebFragment.class, help);
                break;
            case R.id.service:
                url = TEST_BASE_URL + WEB_SERVICE_PATH;
                Bundle service = new Bundle();
                service.putString(INTENT_KEY1, url);
                ToolbarFragmentActivity.createFragment(getContext(), WebFragment.class, service);
                break;
            case R.id.about:
                url = TEST_BASE_URL + WEB_ABOUT_PATH;
                Bundle about = new Bundle();
                about.putString(INTENT_KEY1, url);
                ToolbarFragmentActivity.createFragment(getContext(), WebFragment.class, about);
                break;
            case R.id.exit_btn:
                AppUtils.exitLogin();
                ToolbarFragmentActivity.createFragment(getContext(), LoginFragment.class);
                finish();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSubscribeEvent(Event.ClearCacheEvent event) {
        mClearCache.setStatus(GlideCacheUtil.getInstance().getCacheSize(getContext()));
    }

}
