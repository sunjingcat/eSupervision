package com.zz.supervision.business.record;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.business.company.adapter.FmPagerAdapter;
import com.zz.supervision.business.inspenction.SuperviseActivity;
import com.zz.supervision.business.inspenction.SuperviseSignActivity;
import com.zz.supervision.business.record.adapter.CheckListAdapter;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class ComCheckListActivity extends MyBaseActivity implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_subtitle)
    ImageView toolbarSubtitle;
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_lawEnforcer)
    EditText etLawEnforcer;
    @BindView(R.id.et_inspectionResult)
    TextView etInspectionResult;
    @BindView(R.id.et_status)
    TextView etStatus;
    @BindView(R.id.et_level)
    TextView etLevel;
    public FmPagerAdapter pagerAdapter;
    @BindView(R.id.et_beginTime)
    TextView etBeginTime;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.ll_inspectionResult)
    LinearLayout llInspectionResult;
    @BindView(R.id.ll_level)
    LinearLayout llLevel;
    String inspectionResult = "";
    String beginTime = "";
    String endTime = "";
    String level = "";
    int status = 0;
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private CheckListAdapter adapter;
    List<RecordBean> mlist = new ArrayList<>();
    private int pagenum = 1;
    private int pagesize = 20;

    @Override
    protected int getContentView() {
        return R.layout.activity_cold_check_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
        llLevel.setVisibility(View.GONE);
        llInspectionResult.setVisibility(View.VISIBLE);
        String select = getIntent().getStringExtra("select");
        if (TextUtils.isEmpty(select)) {
            toolbarSubtitle.setVisibility(View.VISIBLE);
        } else {
            toolbarSubtitle.setVisibility(View.GONE);
        }
        String companyId = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(companyId)) {
            ll_company.setVisibility(View.GONE);
        } else {
            ll_company.setVisibility(View.VISIBLE);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckListAdapter(R.layout.item_check_list, mlist);
        recyclerView.setAdapter(adapter);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setOnLoadMoreListener(this);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                RecordBean recordBean = mlist.get(position);
                if (recordBean.getStatus() == 1) {

                    startActivity(new Intent(ComCheckListActivity.this, SuperviseActivity.class)
                            .putExtra("company", recordBean.getOperatorName())
                            .putExtra("id", recordBean.getId() + "")
                            .putExtra("type", recordBean.getType())
                            .putExtra("typeText", "特种设备")
                            .putExtra("reason", recordBean.getReason() + "")
                            .putExtra("lawEnforcer", recordBean.getLawEnforcer1Name() + "," + recordBean.getLawEnforcer2Name())
                            .putExtra("inspectionTime", recordBean.getInspectionTime()));

                } else {
                    startActivity(new Intent(ComCheckListActivity.this, SuperviseSignActivity.class)
                            .putExtra("id", mlist.get(position).getId() + "")
                            .putExtra("type", recordBean.getType()));

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        pagenum = 1;
        getDate();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick(R.id.toolbar_subtitle)
    public void onViewClicked() {
        drawer.openDrawer(GravityCompat.END);
    }

    SelectPopupWindows selectPopupWindows;
    SelectPopupWindows selectPopupWindows1;
    SelectPopupWindows selectPopupWindows2;

    @OnClick({R.id.drawer_bg, R.id.et_inspectionResult, R.id.et_status, R.id.et_level, R.id.et_beginTime, R.id.et_endTime, R.id.bt_cancel, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.drawer_bg:

                break;
            case R.id.et_inspectionResult:
                UIAdjuster.closeKeyBoard(this);
                String[] PLANETS = new String[]{"合格", "不合格"};
                selectPopupWindows = new SelectPopupWindows(this, PLANETS);
                selectPopupWindows.showAtLocation(findViewById(R.id.drawer),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etInspectionResult.setText(msg);
                        inspectionResult =(index+1)+"";
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows.dismiss();
                    }
                });
                break;
            case R.id.et_level:
                UIAdjuster.closeKeyBoard(this);
                String[] PLANETS3 = new String[]{"A", "B", "C", "D"};
                selectPopupWindows2 = new SelectPopupWindows(this, PLANETS3);
                selectPopupWindows2.showAtLocation(findViewById(R.id.drawer),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etLevel.setText(msg);
                        level = msg;
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows2.dismiss();
                    }
                });
                break;
            case R.id.et_status:
                UIAdjuster.closeKeyBoard(this);
                String[] PLANETS2 = new String[]{"待评分", "待签名", "已完成"};
                selectPopupWindows1 = new SelectPopupWindows(this, PLANETS2);
                selectPopupWindows1.showAtLocation(findViewById(R.id.drawer),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows1.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etStatus.setText(msg);
                        status = index + 1;
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows1.dismiss();
                    }
                });
                break;
            case R.id.et_beginTime:
                DatePickDialog dialog = new DatePickDialog(ComCheckListActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog.setYearLimt(20);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        beginTime = time;
                        etBeginTime.setText(time);
                    }
                });
                dialog.show();
                break;
            case R.id.et_endTime:
                DatePickDialog dialog2 = new DatePickDialog(ComCheckListActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog2.setYearLimt(20);
                //设置标题
                dialog2.setTitle("选择时间");
                //设置类型
                dialog2.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog2.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog2.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog2.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        endTime = time;
                        etEndTime.setText(time);
                    }
                });
                dialog2.show();
                break;
            case R.id.bt_cancel:
                etCompany.setText("");
                etLawEnforcer.setText("");
                inspectionResult = "";
                etInspectionResult.setText("");
                status = 0;
                etStatus.setText("");
                level = "";
                etLevel.setText("");
                etBeginTime.setText("");
                beginTime = "";
                etEndTime.setText("");
                endTime = "";
                getDate();
                drawer.closeDrawers();
                break;
            case R.id.bt_ok:
                UIAdjuster.closeKeyBoard(this);
                drawer.closeDrawers();
                String strCompany = etCompany.getText().toString();
                String strLawEnforcer = etLawEnforcer.getText().toString();
                getDate();
                break;
        }
    }

    List<BusinessType> businessTypeList = new ArrayList<>();

    void getType() {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", "inspection_result");
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                if (jsonT.getData() != null) {
                    businessTypeList.clear();
                    businessTypeList.addAll(jsonT.getData());
                }
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pagenum = 1;
        getDate();
        refreshLayout.finishRefresh();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pagenum++;
        getDate();
        refreshLayout.finishLoadMore();
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
        String companyId = getIntent().getStringExtra("id");
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", pagenum);
        map.put("pageSize", pagesize);
        if (!TextUtils.isEmpty(companyId)) {
            map.put("companyId", companyId);
        }
        String strCompany = etCompany.getText().toString();
        String strLawEnforcer = etLawEnforcer.getText().toString();
        if (!TextUtils.isEmpty(strCompany)) {
            map.put("company", strCompany);
        }
        if (!TextUtils.isEmpty(companyId)) {
            map.put("companyId", companyId);
        }
        if (!TextUtils.isEmpty(strLawEnforcer)) {
            map.put("lawEnforcer", strLawEnforcer);
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
        RxNetUtils.request(getApi(ApiService.class).getTzsbInspectionRecordList(map), new RequestObserver<JsonT<List<RecordBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<RecordBean>> jsonT) {
                showResult(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<RecordBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
}
