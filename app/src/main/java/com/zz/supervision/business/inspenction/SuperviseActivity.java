package com.zz.supervision.business.inspenction;

import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.SuperviseAdapter;
import com.zz.supervision.business.inspenction.presenter.SupervisePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 监督检查:食品销售日常
 */
public class SuperviseActivity extends MyBaseActivity<Contract.IsetSupervisePresenter> implements Contract.IGetSuperviseView {
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
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    SuperviseAdapter adapter;
    ArrayList<SuperviseBean> mlist = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new SuperviseAdapter(new SuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (node instanceof SuperviseBean){
                    ((SuperviseBean) node).setCheck(!((SuperviseBean) node).isCheck());
                }else if (node instanceof SuperviseBean){
                    if (type==1) {
                        ((SuperviseBean.Children) node).setCheck(true);
                    }else {
                        ((SuperviseBean.Children) node).setCheck(false);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setList(mlist);
        rv.setAdapter(adapter);
        mPresenter.getData("spxsInspectionRecord/getItems");
        initData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    void initData() {
//       CompanyBean company = getIntent().getParcelableExtra("company");
//       String lawEnforcerText = getIntent().getStringExtra("lawEnforcerText");
//       tvCompany.setText(company.getOperatorName());
//       tvInspector.setText(lawEnforcerText);
    }

    @Override
    public SupervisePresenter initPresenter() {
        return new SupervisePresenter(this);
    }


    @Override
    public void showFoodSuperviseList(List<SuperviseBean> data) {
        adapter.setList(data);
        adapter.notifyDataSetChanged();
        if (data.size() == 0) {
            llNull.setVisibility(View.VISIBLE);
        } else {
            llNull.setVisibility(View.GONE);
        }
    }

    @Override
    public void showResult() {

    }
}
