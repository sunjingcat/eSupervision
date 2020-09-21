package com.zz.supervision.business.company;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
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
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.zz.supervision.net.RxNetUtils.getApi;


/**
 * 签证验收fragment
 */
@SuppressLint("ValidFragment")
public class CompanyFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.certi_search_et)
    EditText certiSearchEt;
//    @BindView(R.id.certi_search)
//    TextView certiSearch;
//    @BindView(R.id.certi_title_num)
//    TextView certiTitleNum;
//    @BindView(R.id.certi_title_name)
//    TextView certiTitleName;
//    @BindView(R.id.certi_title_type)
//    TextView certiTitleType;
//    @BindView(R.id.certi_title_time)
//    TextView certiTitleTime;
//    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private RecyclerView recyclerView;
    private CompanyListAdapter adapter;
    List<CompanyBean> mlist = new ArrayList<>();
    private int pagenum = 0;
    private int pagesize = 20;
    private String searchStr="";
    private int type=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_company_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    @SuppressLint("ValidFragment")
    public CompanyFragment(int type) {
        this.type = type;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDate();
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        adapter = new CompanyListAdapter(R.layout.item_company, mlist);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        certiSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)){
                    searchStr ="";
                }else {
                    searchStr = s.toString();
                }

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.certi_search)
    public void onViewClicked() {
        pagenum = 0;
        getDate();
    }
    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 0;
        getDate();
        refreshlayout.finishRefresh();
    }

    public void showResult(List<CompanyBean> data) {
        if (pagenum == 0) {
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
        map.put("pagenum", pagenum);
        map.put("pagesize", pagesize);
        map.put("visaAcceptanceType", type);
        if (!TextUtils.isEmpty(searchStr)){
            map.put("title",searchStr);
        }
//        RxNetUtils.request(getApi(ApiService.class).getUserDetail(map), new RequestObserver<JsonT<List<CompanyBean>>>() {
//            @Override
//            protected void onSuccess(JsonT<List<CompanyBean>> jsonT) {
//                showResult(jsonT.getData());
//            }
//
//            @Override
//            protected void onFail2(JsonT<List<CompanyBean>> stringJsonT) {
//                super.onFail2(stringJsonT);
//            }
//        }, null);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum ++;
        getDate();
        refreshLayout.finishLoadMore();
    }
}