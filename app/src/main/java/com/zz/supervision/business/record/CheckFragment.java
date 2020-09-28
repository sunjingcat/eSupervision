package com.zz.supervision.business.record;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.company.CompanyInfoActivity;
import com.zz.supervision.business.company.adapter.CompanyListAdapter;
import com.zz.supervision.business.record.adapter.CheckListAdapter;
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
public class CheckFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private RecyclerView recyclerView;
    private CheckListAdapter adapter;
    List<RecordBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;
    private String searchStr = "";
    private String type = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);

        unbinder = ButterKnife.bind(this, view);
        init(view);
        return view;
    }

    @SuppressLint("ValidFragment")
    public CheckFragment(String type) {
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
        adapter = new CheckListAdapter(R.layout.item_check_list, mlist);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);
        String select = getActivity().getIntent().getStringExtra("select");

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if (TextUtils.isEmpty(select)) {
                    startActivity(new Intent(getActivity(), CompanyInfoActivity.class).putExtra("id", mlist.get(position).getId()));
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("company", mlist.get(position));
                    getActivity().setResult(getActivity().RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        pagenum = 1;
        getDate();
        refreshlayout.finishRefresh();
    }

    public void showResult(List<RecordBean> data) {
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
        map.put("inspectionType", type);
        if (!TextUtils.isEmpty(searchStr)) {
            map.put("searchValue", searchStr);
        }
        RxNetUtils.request(getApi(ApiService.class).getRecordList(map), new RequestObserver<JsonT<List<RecordBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<RecordBean>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<RecordBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(getActivity()));
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
    }


}