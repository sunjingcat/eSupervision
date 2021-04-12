package com.zz.supervision.business.equipment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.business.company.adapter.BusinessScopeAdapter;
import com.zz.supervision.business.equipment.adapter.OrganizationAdapter;
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
import butterknife.Unbinder;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 *机构
 */
public class OrganizationListActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    private int pagenum = 1;
    private int pagesize = 20;
    List<OrganizationBean> mlist = new ArrayList<>();
    ArrayList<OrganizationBean> select = new ArrayList<>();
    OrganizationAdapter adapter;
    String type;
    @Override
    protected int getContentView() {
        return R.layout.activity_organization;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new OrganizationAdapter(R.layout.item_law_enforcer, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                Intent intent = new Intent();
                intent.putExtra("select",mlist.get(position));
                setResult(RESULT_OK,intent);
                finish();

            }
        });
        type =getIntent().getStringExtra("url");
        getDate();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }
    public void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        RxNetUtils.request(getApi(ApiService.class).tzsbRegistOrganizationList(type,map), new RequestObserver<JsonT<List<OrganizationBean>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<OrganizationBean>> jsonT) {
                if (pagenum == 1) {
                    mlist.clear();
                }
                mlist.addAll(jsonT.getData());
                adapter.notifyDataSetChanged();
                if (mlist.size() == 0) {
                    llNull.setVisibility(View.VISIBLE);
                } else {
                    llNull.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onFail2(JsonT<List<OrganizationBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 1;
        getDate();
        refreshlayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }
}