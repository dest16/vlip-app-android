package com.vlip.app.fragment.register;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.vlip.app.R;
import com.vlip.kit.ToastUtils;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowInputEdit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                String mobile = mMobile.getText();
                String pass = mPassword.getText();
                String rePass = mRePassword.getText();
                if (mobile.length() != 11) {
                    ToastUtils.showToast("请输入正确的手机号");
                    return;
                }
                if (!pass.equals(rePass)) {
                    ToastUtils.showToast("两次密码输入不一致");
                    return;
                }
                Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,18}");
                Matcher m = p.matcher(pass);
                if (!m.matches()){
                    ToastUtils.showToast("密码必须为6-18位并包含数字和字母");
                    return;
                }
                getPresenter().register(mMobile.getText(), mPassword.getText());
                break;
        }
    }


    @Override
    public void backToLogin() {
        finish();
    }
}
