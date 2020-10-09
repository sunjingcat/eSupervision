package com.zz.supervision.business.risk;

import android.content.Intent;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lib.commonlib.utils.ToolBarUtils;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        });
        rv_dynamicRisks.setAdapter(adapter);


        rv_staticRisks.setLayoutManager(new LinearLayoutManager(this));
        rv_staticRisks.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

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
                } else if (node instanceof RiskSuperviseBean.ChildRisk ) {
                    List<BaseNode> superviseAdapterData = staticSuperviseAdapter.getData();
                    for (BaseNode baseNode : superviseAdapterData) {
                        if (baseNode instanceof RiskSuperviseBean.RiskItem) {
                            if (((RiskSuperviseBean.ChildRisk) node).
                                    getPid().equals(((RiskSuperviseBean.RiskItem) baseNode).getId())) {
                                for (RiskSuperviseBean.ChildRisk child : ((RiskSuperviseBean.RiskItem) baseNode).getChildRisks()) {
                                    if (((RiskSuperviseBean.ChildRisk) node).getId().equals(child.getId())) {
                                        ((RiskSuperviseBean.ChildRisk) node).setCheck(!((RiskSuperviseBean.ChildRisk) node).isCheck());
                                    } else {
                                        child.setCheck(false);
                                    }
                                }
                                break;
                            } else {
                                for (RiskSuperviseBean.ChildRisk child : ((RiskSuperviseBean.RiskItem) baseNode).getChildRisks()) {
                                    if (child.getId().equals(((RiskSuperviseBean.ChildRisk) node).getPid())) {
                                        for (RiskSuperviseBean.ChildRisk childRisk : (child).getChildRisks()) {
                                            if (((RiskSuperviseBean.ChildRisk) node).getId().equals(childRisk.getId())) {
                                                ((RiskSuperviseBean.ChildRisk) node).setCheck(!((RiskSuperviseBean.ChildRisk) node).isCheck());
                                            } else {
                                                childRisk.setCheck(false);
                                            }
                                        }
                                        break;
                                    }else {
                                        if (child.getChildRisks()!=null) {
                                            for (RiskSuperviseBean.ChildRisk grandson : child.getChildRisks()) {
                                                if (grandson.getId().equals(((RiskSuperviseBean.ChildRisk) node).getPid())) {
                                                    for (RiskSuperviseBean.ChildRisk childRisk : (grandson).getChildRisks()) {
                                                        if (((RiskSuperviseBean.ChildRisk) node).getId().equals(childRisk.getId())) {
                                                            ((RiskSuperviseBean.ChildRisk) node).setCheck(!((RiskSuperviseBean.ChildRisk) node).isCheck());
                                                        } else {
                                                            childRisk.setCheck(false);
                                                        }
                                                    }
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                staticSuperviseAdapter.notifyDataSetChanged();
            }
        });
        rv_staticRisks.setAdapter(staticSuperviseAdapter);
        type = getIntent().getIntExtra("type", 0);
        if (type == 3) {
            url = "spxsRiskRecord";
        } else {
            url = "cyfwRiskRecord";

        }
        mPresenter.getData(url);
        initData();
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
                        .putExtra("type", type)
                , 1001);
    }

    @Override
    public void showResult(Integer resposeBean) {
        startActivity(new Intent(this, SuperviseSignActivity.class).putExtra("id", resposeBean + "").putExtra("type", type));
        finish();
    }

    RiskSuperviseBean.PostBean postBeans = new RiskSuperviseBean.PostBean();

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        List<BaseNode> mlist = adapter.getData();
        List<BaseNode> superviseAdapterData = staticSuperviseAdapter.getData();
        Map<String, Object> dynamicRiskMap = new HashMap<>();
        for (BaseNode node : mlist) {
            if (node instanceof RiskSuperviseBean.RiskItem) {
                for (BaseNode child : ((RiskSuperviseBean.RiskItem) node).getChildRisks()) {
                    if (((RiskSuperviseBean.ChildRisk) child).isCheck()) {
                        dynamicRiskMap.put(((RiskSuperviseBean.ChildRisk) child).getId(), ((RiskSuperviseBean.ChildRisk) child).isCheck() ? 1 : 0);
                    }
                }
            }
        }
        List<String> staticRiskIds = new ArrayList<>();
        for (BaseNode node : superviseAdapterData) {
            if (node instanceof RiskSuperviseBean.RiskItem) {
                for (BaseNode child : ((RiskSuperviseBean.RiskItem) node).getChildRisks()) {
                    if (((RiskSuperviseBean.ChildRisk) child).getChildType() == 0) {
                        if (((RiskSuperviseBean.ChildRisk) child).isCheck()) {
                            staticRiskIds.add(((RiskSuperviseBean.ChildRisk) child).getId());
                        }
                    } else {
                        for (RiskSuperviseBean.ChildRisk child1 : ((RiskSuperviseBean.ChildRisk) child).getChildRisks()) {
                            if (child1.getChildType()==0) {
                                if ((child1).isCheck()) {
                                    staticRiskIds.add(child1.getId());
                                }
                            }else {
                                for (RiskSuperviseBean.ChildRisk child2 : child1.getChildRisks()) {
                                    if ((child2).isCheck()) {
                                        staticRiskIds.add(child2.getId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        postBeans = new RiskSuperviseBean.PostBean(staticRiskIds, dynamicRiskMap);
        mPresenter.submitReData(url, id, postBeans);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                mPresenter.submitData(url, id, postBeans);
            }
        }
    }
}
