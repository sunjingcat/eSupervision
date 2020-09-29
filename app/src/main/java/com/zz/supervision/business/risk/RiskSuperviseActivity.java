package com.zz.supervision.business.risk;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.InfoActivity;
import com.zz.supervision.business.inspenction.SuperviseSignActivity;
import com.zz.supervision.business.risk.adapter.RiskSuperviseAdapter;
import com.zz.supervision.business.risk.presenter.RiskSupervisePresenter;

import java.util.ArrayList;
import java.util.List;

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
    RiskSuperviseAdapter adapter;
    List<BaseNode> mlist = new ArrayList<>();
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
                if (node instanceof SuperviseBean) {
                    for (SuperviseBean.Children children : ((SuperviseBean) node).getChildrenList()) {
                        children.setCheck(((SuperviseBean) node).isCheck());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setList(mlist);
        rv_dynamicRisks.setAdapter(adapter);
        mPresenter.getData("spxsInspectionRecord/getItems");
        initData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    void initData() {
        CompanyBean company = getIntent().getParcelableExtra("company");
        String lawEnforcerText = getIntent().getStringExtra("lawEnforcerText");
        String type = getIntent().getStringExtra("type");
//        id = getIntent().getStringExtra("id");
//        tvCompany.setText(company.getOperatorName());
//        tvInspector.setText("检查员：" + lawEnforcerText);
//        tvType.setText("检查类型：" + type + "");
    }

    @Override
    public RiskSupervisePresenter initPresenter() {
        return new RiskSupervisePresenter(this);
    }


    @Override
    public void showSuperviseList(RiskSuperviseBean data) {

        adapter.setList(data.getDynamicRisks());
        adapter.notifyDataSetChanged();

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

    ArrayList<SuperviseBean.PostBean> postBeans = new ArrayList<>();

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        mlist = adapter.getData();
        postBeans = new ArrayList<>();
        for (BaseNode node : mlist) {
            if (node instanceof SuperviseBean.Children) {
                if (((SuperviseBean.Children) node).isCheck()) {
                    postBeans.add(new SuperviseBean.PostBean(((SuperviseBean.Children) node).getId(), ((SuperviseBean.Children) node).getIsSatisfy()));
                }
            }
        }
        mPresenter.submitReData(id, postBeans);
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
