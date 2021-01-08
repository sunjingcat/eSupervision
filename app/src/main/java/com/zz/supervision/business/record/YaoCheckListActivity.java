package com.zz.supervision.business.record;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.google.android.material.tabs.TabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.business.company.adapter.FmPagerAdapter;
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

public class YaoCheckListActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_subtitle)
    ImageView toolbarSubtitle;
    @BindView(R.id.ll_company)
    LinearLayout ll_company;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
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
    private ArrayList<Fragment> fragments = new ArrayList<>();
    String[] tabs = {"药品", "医疗器械"};
    String inspectionResult = "";
    String beginTime = "";
    String endTime = "";
    String level = "";
    int status = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_check_list;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);

        ImmersionBar.with(this).statusBarDarkFont(false)
                .navigationBarColor(R.color.colorPrimary)
                .init();
        initFragment();
        String select = getIntent().getStringExtra("select");
        if (TextUtils.isEmpty(select)) {
            toolbarSubtitle.setVisibility(View.VISIBLE);
        } else {
            toolbarSubtitle.setVisibility(View.GONE);
        }
        String companyId = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(companyId)){
            ll_company.setVisibility(View.GONE);
        }else {
            ll_company.setVisibility(View.VISIBLE);
        }
        getDate();
    }

    CheckFragment checkFragment1;
    CheckFragment checkFragment2;

    void initFragment() {

        checkFragment1 = new CheckFragment("3");
        checkFragment2 = new CheckFragment("4");
        for (int i = 0; i < tabs.length; i++) {
            fragments.add(i == 0 ? checkFragment1 : checkFragment2);
        }
        tablayout.setupWithViewPager(viewpager, false);
        pagerAdapter = new FmPagerAdapter(fragments, getSupportFragmentManager());
        viewpager.setAdapter(pagerAdapter);
        tablayout.getTabAt(0);
        for (int i = 0; i < tabs.length; i++) {
            tablayout.getTabAt(i).setText(tabs[i]);
        }
        llLevel.setVisibility(View.GONE);
        llInspectionResult.setVisibility(View.GONE);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
                if (tablayout.getSelectedTabPosition() == 0) {
                    checkFragment1.setSearchStr("", "", inspectionResult, level, status, beginTime, endTime);

                } else {
                    checkFragment2.setSearchStr("", "", inspectionResult, level, status, beginTime, endTime);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

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
                if (businessTypeList.size() == 0) return;
                UIAdjuster.closeKeyBoard(this);
                List<String> list = new ArrayList<>();
                List<String> list1 = new ArrayList<>();
                for (int i = 0; i < businessTypeList.size(); i++) {
                    list.add(businessTypeList.get(i).getDictLabel());
                    list1.add(businessTypeList.get(i).getDictValue());
                }
                String[] array = (String[]) list.toArray(new String[list.size()]);
                String[] values = (String[]) list1.toArray(new String[list1.size()]);
                selectPopupWindows = new SelectPopupWindows(this, array);
                selectPopupWindows.showAtLocation(findViewById(R.id.drawer),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etInspectionResult.setText(msg);
                        inspectionResult = values[index];
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
                DatePickDialog dialog = new DatePickDialog(YaoCheckListActivity.this);
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
                DatePickDialog dialog2 = new DatePickDialog(YaoCheckListActivity.this);
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
                if (tablayout.getSelectedTabPosition() == 0) {
                    checkFragment1.setSearchStr("", "", inspectionResult, level, status, beginTime, endTime);

                } else {
                    checkFragment2.setSearchStr("", "", inspectionResult, level, status, beginTime, endTime);

                }
                drawer.closeDrawers();
                break;
            case R.id.bt_ok:
                UIAdjuster.closeKeyBoard(this);
                drawer.closeDrawers();
                String strCompany = etCompany.getText().toString();
                String strLawEnforcer = etLawEnforcer.getText().toString();
                if (tablayout.getSelectedTabPosition() == 0) {
                    checkFragment1.setSearchStr(strCompany + "", strLawEnforcer + "", inspectionResult, level, status, beginTime, endTime);
                } else {
                    checkFragment2.setSearchStr(strCompany + "", strLawEnforcer + "", inspectionResult, level, status, beginTime, endTime);
                }
                break;
        }
    }

    List<BusinessType> businessTypeList = new ArrayList<>();

    void getDate() {
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
}
