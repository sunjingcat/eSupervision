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
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.InfoActivity;
import com.zz.supervision.business.inspenction.SuperviseSignActivity;
import com.zz.supervision.business.risk.adapter.RiskStaticAdapter;
import com.zz.supervision.business.risk.adapter.RiskSuperviseAdapter;
import com.zz.supervision.business.risk.presenter.RiskSupervisePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 风险执法:食品销售日常
 */
public class RiskSuperviseActivity extends MyBaseActivity<Contract.IsetRiskSupervisePresenter> implements Contract.IGetRiskSuperviseView {
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

    @Override
    protected int getContentView() {
        return R.layout.activity_select_risks;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv_dynamicRisks.setLayoutManager(new LinearLayoutManager(this));
        rv_dynamicRisks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new RiskSuperviseAdapter(new RiskSuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (node instanceof RiskSuperviseBean.RiskItem) {
                    for (RiskSuperviseBean.ChildRisk children : ((RiskSuperviseBean.RiskItem) node).getChildRisks()) {
                        children.setCheck(!((RiskSuperviseBean.RiskItem) node).isCheck());
                    }
                } else if (node instanceof RiskSuperviseBean.RootFooterNode) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof RiskSuperviseBean.RiskItem&&((RiskSuperviseBean.RiskItem) children).getId() == ((RiskSuperviseBean.RootFooterNode) node).getId()) {
//                           ((RiskSuperviseBean.RiskItem)children).setExpanded(!((RiskSuperviseBean.RiskItem) children).isExpanded());
                            ((RiskSuperviseBean.RootFooterNode) node).setExpanded(!((RiskSuperviseBean.RiskItem) children).isExpanded());
                            adapter.expandOrCollapse(i);
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        rv_dynamicRisks.setAdapter(adapter);


        rv_staticRisks.setLayoutManager(new LinearLayoutManager(this));
        rv_staticRisks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        staticSuperviseAdapter = new RiskStaticAdapter(R.layout.item_risk_second);
        rv_staticRisks.setAdapter(staticSuperviseAdapter);


        mPresenter.getData("spxsInspectionRecord/getItems");
        initData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    void initData() {
        CompanyBean company = (CompanyBean) getIntent().getSerializableExtra("company");
        String lawEnforcerText = getIntent().getStringExtra("lawEnforcerText");
        String type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        tvCompany.setText(company.getOperatorName());
        tvInspector.setText("检查员：" + lawEnforcerText);
        tvType.setText("检查类型：" + type + "");
    }

    @Override
    public RiskSupervisePresenter initPresenter() {
        return new RiskSupervisePresenter(this);
    }


    @Override
    public void showSuperviseList(RiskSuperviseBean data) {
        adapter.setList(data.getDynamicRisks());
        adapter.notifyDataSetChanged();

        staticSuperviseAdapter.setList(data.getStaticRisks());
        staticSuperviseAdapter.notifyDataSetChanged();

    }

    @Override
    public void showReResult(SuperviseBean.ResposeConfirmBean bean) {
        startActivityForResult(new Intent(this, InfoActivity.class)
                        .putExtra("supervise_respose", bean)
                , 1001);
    }

    @Override
    public void showResult(SuperviseBean.ResposeBean resposeBean) {
        startActivity(new Intent(this, SuperviseSignActivity.class).putExtra("resposeBean", resposeBean));

    }

    RiskSuperviseBean.PostBean postBeans =new RiskSuperviseBean.PostBean();

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
       List<BaseNode> mlist = adapter.getData();
       List<RiskSuperviseBean.RiskItem> superviseAdapterData = staticSuperviseAdapter.getData();
        Map<String, Object> dynamicRiskMap = new HashMap<>();
        for (BaseNode node : mlist) {
            if (node instanceof RiskSuperviseBean.RiskItem) {
                for (BaseNode child : ((RiskSuperviseBean.RiskItem) node).getChildRisks()) {
                    if (((RiskSuperviseBean.ChildRisk) child).isCheck()) {
                        dynamicRiskMap.put(((RiskSuperviseBean.ChildRisk) child).getId(),((RiskSuperviseBean.ChildRisk) child).isCheck()?1:0);
                    }
                }
            }
        }
        List<String> staticRiskIds = new ArrayList<>();
        for (BaseNode node : superviseAdapterData) {
            if (node instanceof RiskSuperviseBean.RiskItem) {
                for (BaseNode child : ((RiskSuperviseBean.RiskItem) node).getChildRisks()) {
                    if (((RiskSuperviseBean.ChildRisk) child).isCheck()) {
                        staticRiskIds.add(((RiskSuperviseBean.ChildRisk) child).getId());
                    }
                }
            }
        }
        mPresenter.submitReData(id, new RiskSuperviseBean.PostBean(staticRiskIds,dynamicRiskMap));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                mPresenter.submitData(id, postBeans);
            }
        }
    }
}
