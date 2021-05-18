package com.vlip.app.fragment.login;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.vlip.app.R;
import com.vlip.app.activity.home.HomeActivity;
import com.vlip.app.fragment.register.RegisterFragment;
import com.vlip.ui.activity.ToolbarFragmentActivity;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowInputEdit;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView {

    @BindView(R.id.mobile)
    RowInputEdit mMobile;

    @BindView(R.id.password)
    RowInputEdit mPassword;

    @BindView(R.id.photo)
    ImageView photo;

    @BindView(R.id.member_check)
    RadioGroup mRadioGroup;

    @Override
    public int getViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        getToolbar().setTitle("登录");
        mMobile.setText("13972608888");
        mPassword.setText("nklRoW2c^6o~");
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(new LoginModel(), this);
    }


    @Override
    public void goHome() {
        goIntent(HomeActivity.class);
        finish();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @OnClick({R.id.login, R.id.wx_login, R.id.forget_pwd, R.id.register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                int id = mRadioGroup.getCheckedRadioButtonId();
                id = (id == R.id.sender ? 0 : 1);
                getPresenter().login(mMobile.getText(), mPassword.getText(), id);
                break;
            case R.id.wx_login:
                getPresenter().wx_login();
                break;
            case R.id.forget_pwd:
                break;
            case R.id.register:
                ToolbarFragmentActivity.createFragment(requireContext(), RegisterFragment.class, null);
                break;
        }
    }

}
