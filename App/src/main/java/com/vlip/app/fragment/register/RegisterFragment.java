package com.vlip.app.fragment.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.vlip.app.R;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowInputEdit;

import butterknife.BindView;
import butterknife.OnClick;


public class RegisterFragment extends BaseFragment<RegisterPresenter> implements RegisterView {

    @BindView(R.id.mobile)
    RowInputEdit mMobile;

    @BindView(R.id.password)
    RowInputEdit mPassword;

    @BindView(R.id.re_password)
    RowInputEdit mRePassword;

    @BindView(R.id.submit)
    Button mSubmit;


    @BindView(R.id.progressBar)
    ProgressBar mProgressbar;

    @Override
    public int getViewId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        hideLoading();
    }

    @Override
    public void initData() {
        getToolbar().setTitle("注册");
    }

    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter(new RegisterModel(), this);
    }


    @Override
    public void showLoading() {
        mProgressbar.setVisibility(View.VISIBLE);
        mSubmit.setEnabled(false);
    }

    @Override
    public void hideLoading() {
        mProgressbar.setVisibility(View.GONE);
        mSubmit.setEnabled(true);
    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                getPresenter().register(mMobile.getText(), mPassword.getText());
                break;
        }
    }


    @Override
    public void backToLogin() {
        finish();
    }
}
