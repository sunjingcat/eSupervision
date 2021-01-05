package com.zz.supervision.business.inspenction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zz.supervision.R;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.DetailAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 详情
 */
public class InfoActivity extends Activity {

    @BindView(R.id.item_title)
    TextView itemTitle;
    @BindView(R.id.info_rv)
    RecyclerView infoRv;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_ok)
    Button btnControl;
    List<DetailBean> mlist = new ArrayList<>();
    DetailAdapter adapter;
    SuperviseBean.ResposeConfirmBean deviceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        infoRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DetailAdapter(R.layout.item_detail, mlist);
        infoRv.setAdapter(adapter);
        int type = getIntent().getIntExtra("type", 0);
        deviceInfo = (SuperviseBean.ResposeConfirmBean) getIntent().getSerializableExtra("supervise_respose");
        if (deviceInfo != null) {
            showIntent(deviceInfo, type);
        }

    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                Intent intent = new Intent();
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btn_ok:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    public void showIntent(SuperviseBean.ResposeConfirmBean lightDevice, int type) {
        if (lightDevice == null) return;
        mlist.clear();
        List<DetailBean> list = new ArrayList<>();
        itemTitle.setText("执法详情预览");
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
        } else if (type == 6||type == 7||type == 8||type == 9||type == 10) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("问题数", lightDevice.getProblemCount() + ""));
        } else {
            list.add(new DetailBean("静态评分项分数", lightDevice.getStaticScore() + ""));
            list.add(new DetailBean("动态评分项分数", lightDevice.getDynamicScore() + ""));
            list.add(new DetailBean("总分数", lightDevice.getTotalScore() + ""));
            list.add(new DetailBean("风险等级", lightDevice.getLevel() + ""));

        }

        mlist.addAll(list);
        adapter.notifyDataSetChanged();

    }


}
