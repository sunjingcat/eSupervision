package com.zz.supervision.business.company;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessProjectBean;
import com.zz.supervision.business.company.adapter.BusinessProjectAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessProjectActivity extends MyBaseActivity {

    BusinessProjectAdapter adapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    List<BusinessProjectBean> mlist = new ArrayList<>();
    @BindView(R.id.tv_left)
    TextView tvLeft;

    @Override
    protected int getContentView() {
        return R.layout.activity_business_project;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BusinessProjectAdapter(R.layout.item_bn_pj_title, R.layout.item_bn_pj_content, mlist);
        rv.setAdapter(adapter);
        String type = getIntent().getStringExtra("type" );
        mlist.clear();
        if (type .equals("1")) {
            initXS();
            tvLeft.setText("食品销售");
        } else if (type .equals("2") || type .equals("3")) {
            initFW();
            tvLeft.setText("餐饮服务");
        }
        adapter.notifyDataSetChanged();
        adapter.setOnBnpjClickListener(new BusinessProjectAdapter.OnBnpjClickListener() {
            @Override
            public void onHeaderClick(View v, int p) {
                for (int i =0;i<mlist.size();i++){
                    if (mlist.get(i).getFatherValue()==mlist.get(p).getValue()){
                        mlist.get(i).setSelect(!mlist.get(p).isSelect());
                    }
                }
                mlist.get(p).setSelect(!mlist.get(p).isSelect());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onContentClick(View v, int p) {
                mlist.get(p).setSelect(!mlist.get(p).isSelect());
                adapter.notifyDataSetChanged();
            }

        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    void initXS() {
        mlist.add(new BusinessProjectBean(true, "预包装食品销售", "1",""));
        mlist.add(new BusinessProjectBean(false, "含冷藏冷冻食品", "1.1", "1"));
        mlist.add(new BusinessProjectBean(false, "不含冷藏冷冻食品", "1.2", "1"));
        mlist.add(new BusinessProjectBean(true, "散装食品销售", "2", "0"));
        mlist.add(new BusinessProjectBean(false, "含冷藏冷冻食品", "2.1", "2"));
        mlist.add(new BusinessProjectBean(false, "不含冷藏冷冻食品", "2.2", "2"));
        mlist.add(new BusinessProjectBean(false, "含熟食", "2.3", "2"));
        mlist.add(new BusinessProjectBean(false, "不含熟食", "2.4", "2"));
        mlist.add(new BusinessProjectBean(true, "特殊食品销售", "3", ""));
        mlist.add(new BusinessProjectBean(false, "保健食品", "3.1", "3"));
        mlist.add(new BusinessProjectBean(false, "特殊医学用途配方食品", "3.2", "3"));
        mlist.add(new BusinessProjectBean(false, "婴幼儿配方乳粉", "3.3", "3"));
        mlist.add(new BusinessProjectBean(false, "其他婴幼儿配方食品", "3.4", "3"));
        mlist.add(new BusinessProjectBean(true, "其他类食品销售", "4", ""));
    }

    void initFW() {
        mlist.add(new BusinessProjectBean(true, "热食类", "1", ""));
        mlist.add(new BusinessProjectBean(true, "冷食类", "2", ""));
        mlist.add(new BusinessProjectBean(true, "生食类", "3", ""));
        mlist.add(new BusinessProjectBean(true, "糕点类", "4", ""));
        mlist.add(new BusinessProjectBean(false, "不含裱花搞点", "4.1", "4"));
        mlist.add(new BusinessProjectBean(false, "含裱花糕点", "4.2", "4"));
        mlist.add(new BusinessProjectBean(true, "自制饮品类", "5", ""));
        mlist.add(new BusinessProjectBean(false, "含鲜榨果蔬汁", "5.1", "5"));
        mlist.add(new BusinessProjectBean(false, "不含鲜榨果蔬汁", "5.2", "5"));
        mlist.add(new BusinessProjectBean(false, "含自酿酒", "5.3", "5"));
        mlist.add(new BusinessProjectBean(false, "不含自酿酒", "5.4", "5"));
        mlist.add(new BusinessProjectBean(true, "中央厨房", "6", ""));
        mlist.add(new BusinessProjectBean(true, "集体用餐配送单位", "7", ""));
        mlist.add(new BusinessProjectBean(true, "单位食堂", "8", ""));

    }


    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        ArrayList<BusinessProjectBean> selectList = new ArrayList<>();
        selectList.clear();
        for (BusinessProjectBean businessProjectBean:mlist){
            if (businessProjectBean.isSelect()){
                selectList.add(businessProjectBean);
            }
        }

        Intent intent = new Intent();
        intent.putExtra("bnpj", selectList);
        setResult(RESULT_OK,intent);
        finish();
    }
}
