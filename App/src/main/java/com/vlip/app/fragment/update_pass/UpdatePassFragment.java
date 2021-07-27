package com.vlip.app.fragment.update_pass;

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

public class UpdatePassFragment extends BaseFragment<UpdatePassPresenter> implements UpdatePassView {


    @BindView(R.id.old_pass)
    RowInputEdit oldPassView;

    @BindView(R.id.new_pass)
    RowInputEdit newPassView;

    @BindView(R.id.re_pass)
    RowInputEdit rePassView;

    @BindView(R.id.progressBar)
    ProgressBar mProgressbar;

    @BindView(R.id.submit)
    Button mSubmit;

    @Override
    public int getViewId() {
        return R.layout.fragment_update_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        hideLoading();
    }

    @Override
    public void initData() {
        getToolbar().setTitle("修改密码");

    }

    @OnClick({R.id.submit})
    public void click(View view) {
        String oldPass = oldPassView.getText();
        String newPass = newPassView.getText();
        String rePass = rePassView.getText();

        if (oldPass.isEmpty() || newPass.isEmpty()) {
            ToastUtils.showToast("请完善信息");
            return;
        }
        if (!newPass.equals(rePass)) {
            ToastUtils.showToast("两次密码不一致");
            return;
        }
        if (oldPass.equals(newPass)) {
            ToastUtils.showToast("新密码不能和原密码相同");
            return;
        }
        Pattern p = Pattern.compile("[0-9a-zA-Z_]{6,18}");
        Matcher m = p.matcher(newPass);
        if (m.matches()) {
            ToastUtils.showToast("密码必须为6-18位并包含数字和字母");
            return;
        }
        getPresenter().updatePass(oldPass, newPass);
    }

    @Override
    public UpdatePassPresenter createPresenter() {
        return new UpdatePassPresenter(new UpdatePassModel(), this);
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

    @Override
    public void goBack() {
        finish();
    }
}
