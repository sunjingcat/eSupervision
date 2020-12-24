package com.zz.supervision.business.record;

import android.content.Intent;
import android.view.View;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.MainActivity;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.CompanyListActivity;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.business.mine.MineActivity;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 */
public class RecordMainActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_record_main;

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
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @OnClick({R.id.ll_shp, R.id.ll_ll, R.id.ll_yx, R.id.ll_tzh, R.id.ll_zhd})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.ll_shp:
                Intent intent = new Intent();
                intent.setClass(RecordMainActivity.this, CheckListActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_ll:
                Intent intent1 = new Intent();
                intent1.setClass(RecordMainActivity.this, ColdCheckListActivity.class);
                startActivity(intent1);
                break;

            case R.id.ll_yx:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.ll_tzh:
                showToast("暂未开放，敬请期待");
                break;
            case R.id.ll_zhd:
                showToast("暂未开放，敬请期待");
                break;
        }
    }
}