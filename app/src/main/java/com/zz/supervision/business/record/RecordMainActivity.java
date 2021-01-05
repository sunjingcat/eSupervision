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
 * [{
 * 		"companyType": "0",
 * 		"companyTypeText": "全部",
 * 		"count": "51"
 *        }, {
 * 		"companyType": "1",
 * 		"companyTypeText": "食品",   监督 type=1,2  风险 3,4
 * 		"count": "51"
 *    }, {
 * 		"companyType": "2",
 * 		"companyTypeText": "冷链", type =5
 * 		"count": "4"
 *    }, {
 * 		"companyType": "3",
 * 		"companyTypeText": "药品",type = 6,7
 * 		"count": "3"
 *    }, {
 * 		"companyType": "4",
 * 		"companyTypeText": "医疗器械",type = 8,9,10
 * 		"count": "2"
 *    }, {
 * 		"companyType": "5",
 * 		"companyTypeText": "化妆品",
 * 		"count": "0"
 *    }, {
 * 		"companyType": "6",
 * 		"companyTypeText": "特种设备",
 * 		"count": "0"
 *    }, {
 * 		"companyType": "7",
 * 		"companyTypeText": "重点工业品",
 * 		"count": "0"
 *    }]
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
                startActivity(new Intent(RecordMainActivity.this, YaoCheckListActivity.class));
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