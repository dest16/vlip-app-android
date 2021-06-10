package com.vlip.app.fragment.web;

import android.os.Bundle;
import android.webkit.WebView;

import com.vlip.app.Constants;
import com.vlip.app.R;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.mvp.IPresenter;

import butterknife.BindView;

/**
 * webview
 *
 * @author zm
 */
public class WebFragment extends BaseFragment {


    @BindView(R.id.webView)
    WebView webView;

    @Override
    public int getViewId() {
        return R.layout.fragment_web;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        assert getArguments() != null;
        String url = getArguments().getString(Constants.INTENT_KEY1);
        webView.loadUrl(url);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

}
