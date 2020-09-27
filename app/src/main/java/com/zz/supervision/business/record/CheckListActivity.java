package com.zz.supervision.business.record;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.AddCompanyActivity;
import com.zz.supervision.business.company.adapter.FmPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckListActivity extends MyBaseActivity {
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
    String[] tabs = {"日常监督检查", "风险等级评定"};

    @Override
    protected int getContentView() {
        return R.layout.activity_check_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initFragment();
        String select = getIntent().getStringExtra("select");
        if (TextUtils.isEmpty(select)) {
            toolbarSubtitle.setVisibility(View.VISIBLE);
        } else {
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

    void initFragment() {
        for (int i = 0; i < tabs.length; i++) {
            fragments.add(new CheckFragment(tabs[i]));
            tablayout.addTab(tablayout.newTab());
        }

        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for (int i = 0; i < tabs.length; i++) {
            tablayout.getTabAt(i).setText(tabs[i]);
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


}
