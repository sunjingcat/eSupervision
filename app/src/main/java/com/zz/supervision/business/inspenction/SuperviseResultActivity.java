package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.DetailAdapter;
import com.zz.supervision.business.record.ShowDocActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 监督检查结果
 */
public class SuperviseResultActivity extends MyBaseActivity {

    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    List<DetailBean> mlist = new ArrayList<>();
    DetailAdapter adapter;
    SuperviseBean.ResposeBean deviceInfo;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.btn_pingfen)
    Button btn_pingfen;
    int type;

    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_result;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(R.layout.item_detail, mlist);
        infoRv.setAdapter(adapter);

         type = getIntent().getIntExtra("type", 0);
        deviceInfo = (SuperviseBean.ResposeBean) getIntent().getSerializableExtra("resposeBean");
        if (deviceInfo != null) {
            showIntent(deviceInfo, type);
        }
        if (type==5){
            btn_pingfen.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void showIntent(SuperviseBean.ResposeBean lightDevice, int type) {
        if (lightDevice == null) return;
        itemTitle.setText(lightDevice.getCompanyInfo().getOperatorName() + "");
        mlist.clear();
        List<DetailBean> list = new ArrayList<>();
        if (type == 1 || type == 2) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("重点项目", lightDevice.getImportantCount() + ""));
            list.add(new DetailBean("重点项问题数", lightDevice.getImportantProblemCount() + ""));
            list.add(new DetailBean("一般项数", lightDevice.getGeneralCount() + ""));
            list.add(new DetailBean("一般项问题数", lightDevice.getGeneralProblemCount() + ""));
            list.add(new DetailBean("检查结果", lightDevice.getInspectionResultText() + ""));
            list.add(new DetailBean("处理结果", lightDevice.getResultReductionText() + ""));
        } else if (type == 5) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("问题数", lightDevice.getProblemCount() + ""));
            list.add(new DetailBean("检查结果", lightDevice.getInspectionResultText() + ""));
        }else {
            list.add(new DetailBean("静态评分项分数", lightDevice.getStaticScore() + ""));
            list.add(new DetailBean("动态评分项分数", lightDevice.getDynamicScore() + ""));
            list.add(new DetailBean("总分数", lightDevice.getTotalScore() + ""));
            list.add(new DetailBean("风险等级", lightDevice.getLevel() + ""));
        }

        mlist.addAll(list);
        adapter.notifyDataSetChanged();

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.btn_jilu, R.id.btn_pingfen})
    public void onViewClicked(View view) {
        if (deviceInfo == null) {
            return;
        }
        switch (view.getId()) {

            case R.id.btn_jilu:
                startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", deviceInfo.getId()).putExtra("tinspectSheetType", 1).putExtra("tinspectType", type));

                break;
            case R.id.btn_pingfen:
                startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", deviceInfo.getId()).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type));
                break;
        }
    }
}
