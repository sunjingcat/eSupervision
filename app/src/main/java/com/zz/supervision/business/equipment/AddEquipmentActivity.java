package com.zz.supervision.business.equipment;


import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *设备添加
 */
public class AddEquipmentActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment_add;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }
}