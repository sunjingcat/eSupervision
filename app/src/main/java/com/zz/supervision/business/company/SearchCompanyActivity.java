package com.zz.supervision.business.company;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.ClearEditText;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.business.company.adapter.CompanyListAdapter;
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
import butterknife.Unbinder;

import static com.zz.supervision.net.RxNetUtils.getApi;


/**
 * fragment
 */
@SuppressLint("ValidFragment")
public class SearchCompanyActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.et_search)
    ClearEditText et_search;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private CompanyListAdapter adapter;
    List<CompanyBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";
    private String type = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_search_company_list;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 1;
        getDate();
        refreshlayout.finishRefresh();
    }

    public void showResult(List<CompanyBean> data) {
        if (pagenum == 1) {
            mlist.clear();
        }
        mlist.addAll(data);
        adapter.notifyDataSetChanged();
        if (mlist.size() == 0) {
            llNull.setVisibility(View.VISIBLE);
        } else {
            llNull.setVisibility(View.GONE);
        }
    }

    void getDate() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        map.put("companyType", 0);
        searchStr = et_search.getText().toString();
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("searchValue", searchStr);
        }
        RxNetUtils.request(getApi(ApiService.class).getCompanyInfoList(map), new RequestObserver<JsonT<List<CompanyBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<CompanyBean>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<CompanyBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompanyListAdapter(R.layout.item_company, mlist);
        rv.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        String select = getIntent().getStringExtra("select");
        getDate();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (TextUtils.isEmpty(select)) {
                    startActivity(new Intent(SearchCompanyActivity.this, CompanyInfoActivity.class).putExtra("id", mlist.get(position).getId()));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("company", mlist.get(position));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                getDate();//搜索方法
                //隐藏软键盘
                @SuppressLint("WrongConstant") InputMethodManager imm = (InputMethodManager) context.getSystemService("input_method");
                imm.toggleSoftInput(0, 2);
                return true;
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

}