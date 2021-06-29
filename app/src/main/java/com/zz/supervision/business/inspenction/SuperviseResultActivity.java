package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.MainActivity;
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
    int resultReduction;
    @BindView(R.id.bt_orderStatus)
    Button bt_orderStatus;
    @BindView(R.id.bt_decisionStatus)
    Button bt_decisionStatus;
    @BindView(R.id.bt_replyStatus)
    Button bt_replyStatus;
    @BindView(R.id.ly_status)
    LinearLayout ly_status;
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
        if (type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
            btn_pingfen.setVisibility(View.GONE);
        }

        if (type >= 11 && type <= 18) {
            btn_pingfen.setVisibility(View.GONE);
            resultReduction = deviceInfo.getResultReduction();
            if (resultReduction!=0&&resultReduction!=4) {
                btn_pingfen.setVisibility(View.VISIBLE);
                btn_pingfen.setText("打印指令书");
            }
        }

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuperviseResultActivity.this, MainActivity.class));
                finish();
            }
        });
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK ) {
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
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
        } else if (type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("问题数", lightDevice.getProblemCount() + ""));
        } else if (type >= 11 && type <= 18) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("不符合规范项数", lightDevice.getProblemCount() + ""));
        }else if (type == 19) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("问题数", lightDevice.getProblemCount() + ""));
        }else if (type == 20) {
            list.add(new DetailBean("严重缺陷数", lightDevice.getSeriousCount() + ""));
            list.add(new DetailBean("主要缺陷数", lightDevice.getProblemCount() + ""));
            list.add(new DetailBean("一般缺陷数", lightDevice.getGeneralCount() + ""));
            list.add(new DetailBean("合理缺陷数", lightDevice.getReasonableCount() + ""));
            list.add(new DetailBean("评定级别", lightDevice.getLevel() + ""));
            list.add(new DetailBean("评定类别", lightDevice.getGradeText() + ""));
        }else if (type == 21) {
            list.add(new DetailBean("检查项数目", lightDevice.getSumCount() + ""));
            list.add(new DetailBean("不符合规范项数", lightDevice.getProblemCount() + ""));
        } else {
            list.add(new DetailBean("静态评分项分数", lightDevice.getStaticScore() + ""));
            list.add(new DetailBean("动态评分项分数", lightDevice.getDynamicScore() + ""));
            list.add(new DetailBean("总分数", lightDevice.getTotalScore() + ""));
            list.add(new DetailBean("风险等级", lightDevice.getLevel() + ""));
        }

        mlist.addAll(list);
        adapter.notifyDataSetChanged();
        bt_orderStatus.setVisibility(lightDevice.getOrderStatus()==0?View.GONE:View.VISIBLE);
        bt_decisionStatus.setVisibility(lightDevice.getDecisionStatus()==0?View.GONE:View.VISIBLE);
        bt_replyStatus.setVisibility(lightDevice.getReplyStatus()==0?View.GONE:View.VISIBLE);

        bt_orderStatus.setText(lightDevice.getOrderStatus()==1?"生成责令改正通知书":"打印责令改正通知书");
        bt_decisionStatus.setText(lightDevice.getDecisionStatus()==1?"生成行政处罚决定书":"打印行政处罚决定书");
        bt_replyStatus.setText(lightDevice.getReplyStatus()==1?"生成送达回证":"打印送达回证");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.btn_jilu, R.id.btn_pingfen, R.id.bt_orderStatus, R.id.bt_decisionStatus, R.id.bt_replyStatus})
    public void onViewClicked(View view) {
        if (deviceInfo == null) {
            return;
        }
        switch (view.getId()) {

            case R.id.btn_jilu:
                startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", deviceInfo.getId()).putExtra("tinspectSheetType", 1).putExtra("tinspectType", type));

                break;
            case R.id.btn_pingfen:
//                if (resultReduction!=0&&resultReduction!=4) {
//                    startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", deviceInfo.getId()).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type));
//                } else {
                    startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", deviceInfo.getId()).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type));

//                }
                finish();
                break;
            case R.id.bt_orderStatus:
                if (deviceInfo != null && deviceInfo.getStatus() == 3) {
                    if (deviceInfo.getOrderStatus()==1) {
                        startActivity(new Intent(SuperviseResultActivity.this, CreateOrderStatusActivity.class ).putExtra("id", deviceInfo.getId()).putExtra("type", type));
                    }else {
                        startActivity(new Intent(SuperviseResultActivity.this,  ShowDocActivity.class).putExtra("tinspectSheetType", 3).putExtra("tinspectType", type).putExtra("read", 1));
                    }
                }
                finish();
                break;
            case R.id.bt_decisionStatus:
                if (deviceInfo != null && deviceInfo.getStatus() == 3) {
                    if (deviceInfo.getDecisionStatus() == 1) {
                        startActivity(new Intent(SuperviseResultActivity.this, CreateDecisionStatusActivity.class).putExtra("id",  deviceInfo.getId()).putExtra("type", type));
                    } else {
                        startActivity(new Intent(SuperviseResultActivity.this, ShowDocActivity.class).putExtra("tinspectSheetType", 4).putExtra("tinspectType", type).putExtra("id",  deviceInfo.getId()));

                    }
                }
                finish();
                break;
            case R.id.bt_replyStatus:
                if (deviceInfo != null && deviceInfo.getStatus() == 3) {
                    if (deviceInfo.getReplyStatus()==1) {
                        startActivity(new Intent(SuperviseResultActivity.this, CreateReplyStatusActivity.class ).putExtra("id",  deviceInfo.getId()).putExtra("type", type));
                    }else {
                        startActivity(new Intent(SuperviseResultActivity.this,  ShowDocActivity.class).putExtra("tinspectSheetType", 5).putExtra("tinspectType", type).putExtra("id",  deviceInfo.getId()));
                    }
                }
                finish();
                break;
        }
    }
}
