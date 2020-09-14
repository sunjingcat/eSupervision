package com.zz.supervision.business.map;

import android.os.Bundle;

import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.lib.core.ui.mvp.BasePresenter;

public class NavActivity extends MyBaseActivity {
//    https://blog.csdn.net/qq_38605488/article/details/87119796
    @Override
    protected int getContentView() {
        return R.layout.activity_nav;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
//        c.getBaiduNaviManager().init(context, mSDCardPath, APP_FOLDER_NAME,
//                new IBaiduNaviManager.INaviInitListener());

    }

    @Override
    protected void initToolBar() {

    }
}
