package com.zz.supervision.business.inspenction;

import android.os.Bundle;

import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;

public class XCHZF2Activity extends MyBaseActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_xchzf;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xchzf2);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initToolBar() {

    }
}
