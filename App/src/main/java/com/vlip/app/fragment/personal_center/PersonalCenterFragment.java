package com.vlip.app.fragment.personal_center;

import android.os.Bundle;

import com.vlip.kit.DPUtils;
import com.vlip.app.R;
import com.vlip.app.bean.Member;
import com.vlip.app.kit.AppUtils;
import com.vlip.ui.fragment.BaseFragment;
import com.vlip.ui.mvp.IPresenter;
import com.vlip.ui.row.RowSettingText;

import butterknife.BindView;

public class PersonalCenterFragment extends BaseFragment {


    @BindView(R.id.user_id)
    RowSettingText mUserId;

    @BindView(R.id.phone)
    RowSettingText mPhone;

    @Override
    public int getViewId() {
        return R.layout.fragment_personal_center;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData() {
        getToolbar().setTitle("个人中心");
        Member member = AppUtils.getMember();

        mUserId.setStatus(member.sn);
        mUserId.setRightImage(false);
        int space = DPUtils.dp2px(getResources(), 16);
        mUserId.setStatusPadding(0, 0, space, 0);
        mPhone.setStatus(AppUtils.formatPhone(member.mobile));
        mPhone.setRightImage(false);
        mPhone.setStatusPadding(0, 0, space, 0);
    }

    @Override
    public IPresenter createPresenter() {
        return null;
    }

}
