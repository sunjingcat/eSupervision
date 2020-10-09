package com.zz.supervision.business.risk;

import android.content.Intent;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.InfoActivity;
import com.zz.supervision.business.inspenction.SuperviseSignActivity;
import com.zz.supervision.business.risk.adapter.RiskStaticAdapter;
import com.zz.supervision.business.risk.adapter.RiskSuperviseAdapter;
import com.zz.supervision.business.risk.presenter.RiskSupervisePresenter;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.tv_num)
    TextView tvNum;

    @BindView(R.id.rv_dynamicRisks)
    RecyclerView rv_dynamicRisks;

    @BindView(R.id.rv_staticRisks)
    RecyclerView rv_staticRisks;
    RiskSuperviseAdapter adapter;
    RiskStaticAdapter staticSuperviseAdapter;

    String id;
    String url = "spxsRiskRecord";
    int type = 0;

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
                if (node instanceof RiskSuperviseBean.RootFooterNode) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof RiskSuperviseBean.RiskItem && ((RiskSuperviseBean.RiskItem) children).getId().equals(((RiskSuperviseBean.RootFooterNode) node).getId())) {
//                           ((RiskSuperviseBean.RiskItem)children).setExpanded(!((RiskSuperviseBean.RiskItem) children).isExpanded());
                            ((RiskSuperviseBean.RootFooterNode) node).setExpanded(!((RiskSuperviseBean.RiskItem) children).isExpanded());
                            adapter.expandOrCollapse(i);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        },1);
        rv_dynamicRisks.setAdapter(adapter);


        rv_staticRisks.setLayoutManager(new LinearLayoutManager(this));

        staticSuperviseAdapter = new RiskStaticAdapter(new RiskSuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (node instanceof RiskSuperviseBean.RootFooterNode) {
                    for (int i = 0; i < staticSuperviseAdapter.getData().size(); i++) {
                        BaseNode children = staticSuperviseAdapter.getData().get(i);
                        if (children instanceof RiskSuperviseBean.RiskItem && ((RiskSuperviseBean.RiskItem) children).getId().equals(((RiskSuperviseBean.RootFooterNode) node).getId())) {

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
        },1);
        rv_staticRisks.setAdapter(staticSuperviseAdapter);
        type = getIntent().getIntExtra("type", 0);
        if (type == 3) {
            url = "spxsRiskRecord";
        } else {
            url = "cyfwRiskRecord";

        }
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

}
