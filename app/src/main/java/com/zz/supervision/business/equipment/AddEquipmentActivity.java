package com.zz.supervision.business.equipment;


import android.view.View;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.widget.ItemGroup;

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

    @BindView(R.id.ig_deviceCode)
    ItemGroup ig_deviceCode;
    @BindView(R.id.ig_deviceModel)
    ItemGroup ig_deviceModel;
    @BindView(R.id.ig_deviceType1)
    ItemGroup ig_deviceType1;
    @BindView(R.id.ig_deviceType2)
    ItemGroup ig_deviceType2;
    @BindView(R.id.ig_deviceType3)
    ItemGroup ig_deviceType3;
    @BindView(R.id.ig_deviceName)
    ItemGroup ig_deviceName;
    @BindView(R.id.ig_registStatus)
    ItemGroup ig_registStatus;
    @BindView(R.id.ig_registOrganizationId)
    ItemGroup ig_registOrganizationId;
    @BindView(R.id.ig_registTime)
    ItemGroup ig_registTime;
    @BindView(R.id.ig_registRecorder)
    ItemGroup ig_registRecorder;
    @BindView(R.id.ig_registNumber)
    ItemGroup ig_registNumber;
    @BindView(R.id.ig_registCode)
    ItemGroup ig_registCode;
    @BindView(R.id.ig_usageStatus)
    ItemGroup ig_usageStatus;
    @BindView(R.id.ig_usageUpdateDate)
    ItemGroup ig_usageUpdateDate;
    @BindView(R.id.ig_manufacturerName)
    ItemGroup ig_manufacturerName;
    @BindView(R.id.ig_projectNumber)
    ItemGroup ig_projectNumber;
    @BindView(R.id.ig_manufacturerDate)
    ItemGroup ig_manufacturerDate;
    @BindView(R.id.ig_installationCompany)
    ItemGroup ig_installationCompany;
    @BindView(R.id.ig_completionDate)
    ItemGroup ig_completionDate;

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
        ig_completionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }
}