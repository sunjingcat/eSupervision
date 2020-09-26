package com.zz.supervision.business.inspenction;

import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
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

public class SelectInspectionActivity extends MyBaseActivity<Contract.IsetSupervisePresenter> implements Contract.IGetSuperviseView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    SuperviseAdapter adapter;
    ArrayList<ScoreBean> list = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new SuperviseAdapter();
        adapter.setList(list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public SupervisePresenter initPresenter() {
        return new SupervisePresenter(this);
    }

    @Override
    public void showFoodSuperviseList(List<SuperviseBean> data) {

    }

    @Override
    public void showResult() {

    }

}
