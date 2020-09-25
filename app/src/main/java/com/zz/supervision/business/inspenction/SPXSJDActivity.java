package com.zz.supervision.business.inspenction;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.business.inspenction.adapter.ScoreAdapter;
import com.zz.supervision.business.inspenction.presenter.ScorePresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 食品销售日常监督检查
 */
public class SPXSJDActivity extends MyBaseActivity<Contract.IsetScorePresenter> implements Contract.IGetScoreView {
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
    ScoreAdapter adapter;
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
        adapter = new ScoreAdapter();
        adapter.setList(list);
        rv.setAdapter(adapter);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public ScorePresenter initPresenter() {
        return new ScorePresenter(this);
    }

    @Override
    public void showScoreInfo(CompanyBean data) {

    }

    @Override
    public void showResult() {

    }
}
