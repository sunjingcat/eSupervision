package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonitorActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ig_illegalActivity)
    ItemGroup igIllegalActivity;
    @BindView(R.id.ig_lawContent)
    ItemGroup igLawContent;
    @BindView(R.id.ig_accordContent)
    ItemGroup igAccordContent;
    @BindView(R.id.ig_reformMeasure)
    ItemGroup igReformMeasure;
    @BindView(R.id.bg)
    LinearLayout bg;
String id;
    @Override
    protected int getContentView() {
        return R.layout.activity_monitor;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id).putExtra("tinspectSheetType", 2).putExtra("tinspectType", 8));

    }
}
