package com.zz.supervision.business.company;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.CompanyType;
import com.zz.supervision.business.company.adapter.FmPagerAdapter;
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

public class CompanyListActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    public FmPagerAdapter pagerAdapter;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    private ArrayList<Fragment> fragments = new ArrayList<>();


    @Override
    protected int getContentView() {
        return R.layout.activity_company_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getDate();
        String select = getIntent().getStringExtra("select");
        if (TextUtils.isEmpty(select)) {
            toolbarSubtitle.setVisibility(View.VISIBLE);
        }else {
            toolbarSubtitle.setVisibility(View.GONE);
        }
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    void initFragment(List<CompanyType> list) {
        for (int i = 0; i < list.size(); i++) {
            fragments.add(new CompanyFragment(list.get(i).getCompanyType()));
            tablayout.addTab(tablayout.newTab());
        }

        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for (int i = 0; i < list.size(); i++) {
            tablayout.getTabAt(i).setText(list.get(i).getCompanyTypeText());
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        startActivity(new Intent(this, AddCompanyActivity.class));
    }

    void getDate() {
        Map<String, Object> map = new HashMap<>();
        RxNetUtils.request(getApi(ApiService.class).selectCompanyGroupCount(map), new RequestObserver<JsonT<List<CompanyType>>>() {
            @Override
            protected void onSuccess(JsonT<List<CompanyType>> jsonT) {
                initFragment(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<CompanyType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }


}
