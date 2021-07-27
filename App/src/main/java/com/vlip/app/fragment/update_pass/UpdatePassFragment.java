package com.vlip.app.fragment.update_pass;

import android.os.Bundle;
import android.view.View;

import com.vlip.app.R;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.row.RowInputEdit;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePassFragment extends BaseFragment<UpdatePassPresenter> implements UpdatePassView {


    @BindView(R.id.old_pass)
    RowInputEdit oldPass;

    @BindView(R.id.new_pass)
    RowInputEdit newPass;

    @BindView(R.id.re_pass)
    RowInputEdit rePass;

    @Override
    public int getViewId() {
        return R.layout.fragment_update_pass;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        getToolbar().setTitle("修改密码");

    }

    @OnClick({R.id.submit})
    public void click(View view) {

    }

    @Override
    public UpdatePassPresenter createPresenter() {
        return new UpdatePassPresenter(new UpdatePassModel(), this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
