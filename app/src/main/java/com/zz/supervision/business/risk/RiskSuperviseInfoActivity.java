package com.zz.supervision.business.risk;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.business.risk.adapter.RiskStaticAdapter;
import com.zz.supervision.business.risk.adapter.RiskSuperviseAdapter;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 风险执法
 */
public class RiskSuperviseInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_inspector)
    TextView tvInspector;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.rv_dynamicRisks)
    RecyclerView rv_dynamicRisks;

    @BindView(R.id.rv_staticRisks)
    RecyclerView rv_staticRisks;
    RiskSuperviseAdapter adapter;
    RiskStaticAdapter staticSuperviseAdapter;

    String id;
    String url = "spxsRiskRecord";
    int type = 0;
    int status = 0;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbaSubtitle;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_risks;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv_dynamicRisks.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RiskSuperviseAdapter(new RiskSuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (type == 10) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof RiskSuperviseBean.RiskItem && ((RiskSuperviseBean.RiskItem) children).getId().equals(((RiskSuperviseBean.RiskItem) node).getId())) {
                            adapter.expandOrCollapse(i);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, 1);
        rv_dynamicRisks.setAdapter(adapter);
        toolbaSubtitle.setVisibility(View.GONE);

        rv_staticRisks.setLayoutManager(new LinearLayoutManager(this));

        staticSuperviseAdapter = new RiskStaticAdapter(new RiskSuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (type==10) {
                    for (int i = 0; i < staticSuperviseAdapter.getData().size(); i++) {
                        BaseNode children = staticSuperviseAdapter.getData().get(i);
                        if (children instanceof RiskSuperviseBean.RiskItem && ((RiskSuperviseBean.RiskItem) children).getId().equals(((RiskSuperviseBean.RiskItem) node).getId())) {

                            if (((RiskSuperviseBean.RiskItem) children).isExpanded()) {
                                staticSuperviseAdapter.collapseAndChild(i);
                                ((RiskSuperviseBean.RiskItem) children).setExpanded(false);
                            } else {
                                staticSuperviseAdapter.expandAndChild(i);
                                ((RiskSuperviseBean.RiskItem) children).setExpanded(true);
                            }

                            break;
                        }
                    }
                }
                staticSuperviseAdapter.notifyDataSetChanged();
            }
        }, 1);
        rv_staticRisks.setAdapter(staticSuperviseAdapter);
        type = getIntent().getIntExtra("type", 0);
        status = getIntent().getIntExtra("status", 0);
        if (type == 3) {
            url = "spxsRiskRecord";
        } else {
            url = "cyfwRiskRecord";

        }
        btOk.setText("打印");
        btOk.setVisibility(status == 3 ? View.VISIBLE : View.GONE);

        toolbarTitle.setText("评分详情");
        initData();
        getData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void initData() {
        String company = getIntent().getStringExtra("company");
        String lawEnforcerText = getIntent().getStringExtra("lawEnforcer");
        String type = getIntent().getStringExtra("typeText");
        id = getIntent().getStringExtra("id");
        tvCompany.setText(company);
        tvInspector.setText("检查员：" + lawEnforcerText);
        tvType.setText("检查类型：" + type + "");
        String time = getIntent().getStringExtra("inspectionTime");
        String reason = getIntent().getStringExtra("reason");
        tvTime.setText("检查时间：" + time + "");
        if (TextUtils.isEmpty(reason)){
            tvReason.setVisibility(View.GONE);
        }
        tvReason.setText("检查事由：" +  reason+"");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    public void showSuperviseList(RiskSuperviseBean data) {
        adapter.setList(data.getDynamicRisks());
        adapter.notifyDataSetChanged();
        staticSuperviseAdapter.setList(data.getStaticRisks());
        staticSuperviseAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    void getData() {
        RxNetUtils.request(getApi(ApiService.class).getRiskSuperviseInfo(url, id), new RequestObserver<JsonT<RiskSuperviseBean>>() {
            @Override
            protected void onSuccess(JsonT<RiskSuperviseBean> jsonT) {
                showSuperviseList(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<RiskSuperviseBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        if (TextUtils.isEmpty(id)) return;
        startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type));
    }
}
