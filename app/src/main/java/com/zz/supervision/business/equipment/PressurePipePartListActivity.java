package com.zz.supervision.business.equipment;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.bean.PressurePipePart;
import com.zz.supervision.business.company.SearchCompanyActivity;
import com.zz.supervision.business.equipment.adapter.OrganizationAdapter;
import com.zz.supervision.business.equipment.adapter.PipeAdapter;
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

/**
 * 压力管道单元
 */
public class PressurePipePartListActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    List<PressurePipePart> mlist = new ArrayList<>();
    PipeAdapter adapter;
    String deviceId;

    @Override
    protected int getContentView() {
        return R.layout.activity_pipe_list;

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
        adapter = new PipeAdapter(R.layout.item_law_enforcer, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableLoadMore(false);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {


            }
        });
        deviceId = getIntent().getStringExtra("deviceId");
        getDate();
    }

    @OnClick({R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivity(new Intent(PressurePipePartListActivity.this, AddPipePartActivity.class).putExtra("deviceId",deviceId)
                );

                break;
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void getDate() {

        RxNetUtils.request(getApi(ApiService.class).tzsbPressurepipePartList(deviceId), new RequestObserver<JsonT<List<PressurePipePart>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<PressurePipePart>> jsonT) {

                mlist.clear();

                mlist.addAll(jsonT.getData());
                adapter.notifyDataSetChanged();
                if (mlist.size() == 0) {
                    llNull.setVisibility(View.VISIBLE);
                } else {
                    llNull.setVisibility(View.GONE);
                }
            }

            @Override
            protected void onFail2(JsonT<List<PressurePipePart>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

}