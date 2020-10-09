package com.zz.supervision.business.company;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

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
import com.zz.supervision.utils.TabUtils;
import com.zz.supervision.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
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
    NoScrollViewPager viewpager;
    public FmPagerAdapter pagerAdapter;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    String select;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        getDate();
        select = getIntent().getStringExtra("select");
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

    void initFragment(List<CompanyType> list) {
        String[] tabs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            fragments.add(new CompanyFragment(list.get(i).getCompanyType()));
            tabs[i] = list.get(i).getCompanyTypeText();
        }


        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);

        for (int i = 0; i < list.size(); i++) {
            tablayout.getTabAt(i).setText(list.get(i).getCompanyTypeText()+"("+list.get(i).getCount()+")");
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
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


    @OnClick({R.id.bt_search, R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_search:
                startActivityForResult(new Intent(this, SearchCompanyActivity.class).putExtra("select", select), 1001);
                break;
            case R.id.toolbar_subtitle:
                startActivity(new Intent(this, AddCompanyActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }
}
