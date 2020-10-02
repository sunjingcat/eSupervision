package com.zz.supervision.business.record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.business.inspenction.SuperviseActivity;
import com.zz.supervision.business.inspenction.SuperviseSignActivity;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.business.record.adapter.CheckListAdapter;
import com.zz.supervision.business.risk.RiskSuperviseActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

    String company;
    String lawEnforcer;
    String inspectionResult;
    int status;
    String beginTime;
    String endTime;
    String level;

    public void setSearchStr(String company, String lawEnforcer, String inspectionResult, String level, int status, String beginTime, String endTime) {
        this.company = company;
        this.lawEnforcer = lawEnforcer;
        this.inspectionResult = inspectionResult;
        this.beginTime = beginTime;
        this.status = status;
        this.endTime = endTime;
        this.level = level;
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
                RecordBean recordBean = mlist.get(position);
                if (recordBean.getStatus() == 1) {
                    if (recordBean.getType() == 1 ||recordBean.getType() == 2) {
                        startActivity(new Intent(getActivity(), SuperviseActivity.class)
                                .putExtra("company", recordBean.getOperatorName())
                                .putExtra("id", recordBean.getId() + "")
                                .putExtra("type", recordBean.getType())
                                .putExtra("typeText", recordBean.getType()+"")
                                .putExtra("lawEnforcer", recordBean.getLawEnforcer1Name()+","+recordBean.getLawEnforcer2Name())
                                .putExtra("inspectionTime", recordBean.getCreateTime()));
                    } else {
                        startActivity(new Intent(getActivity(), RiskSuperviseActivity.class)
                                .putExtra("company", recordBean.getOperatorName())
                                .putExtra("id", recordBean.getId() + "")
                                .putExtra("type", recordBean.getType())
                                .putExtra("typeText", recordBean.getType()+"")
                                .putExtra("lawEnforcer", recordBean.getLawEnforcer1Name()+","+recordBean.getLawEnforcer2Name())
                                .putExtra("inspectionTime", recordBean.getCreateTime())); }

                } else {
                    startActivity(new Intent(getActivity(), SuperviseSignActivity.class).putExtra("id", mlist.get(position).getId() + "").putExtra("type", mlist.get(position).getType()));

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
        if (!TextUtils.isEmpty(company)) {
            map.put("company", company);
        }
        if (!TextUtils.isEmpty(lawEnforcer)) {
            map.put("lawEnforcer", lawEnforcer);
        }
        if (!TextUtils.isEmpty(beginTime)) {
            map.put("beginTime", beginTime);
        }
        if (!TextUtils.isEmpty(level)) {
            map.put("level", level);
        }
        if (!TextUtils.isEmpty(endTime)) {
            map.put("endTime", endTime);
        }
        if (!TextUtils.isEmpty(inspectionResult)) {
            map.put("inspectionResult", inspectionResult);
        }
        if (status != 0) {
            map.put("status", status);
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