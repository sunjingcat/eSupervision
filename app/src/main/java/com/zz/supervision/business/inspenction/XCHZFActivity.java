package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.business.company.CompanyListActivity;
import com.zz.supervision.business.company.PeopleActivity;
import com.zz.supervision.business.risk.RiskSuperviseActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 现场执法
 */
public class XCHZFActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_company)
    TextView etCompany;
    @BindView(R.id.et_type)
    TextView etType;
    @BindView(R.id.et_people)
    TextView etPeople;
    @BindView(R.id.et_startTime)
    TextView etStartTime;
    @BindView(R.id.ed_cause)
    EditText edCause;
    @BindView(R.id.bt_ok)
    TextView btOk;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.et_socialCreditCode)
    TextView etSocialCreditCode;
    @BindView(R.id.et_legalRepresentative)
    TextView etLegalRepresentative;
    @BindView(R.id.et_businessPlace)
    TextView etBusinessPlace;
    @BindView(R.id.et_contact)
    TextView etContact;
    @BindView(R.id.et_contactInformation)
    TextView etContactInformation;
    @BindView(R.id.ll_company_info)
    LinearLayout llCompanyInfo;
    CompanyBean companyBean;
    int type = 0;
    String inspectionTime = "";
    String names = "";
    ArrayList<LawEnforcerBean> lawEnforcerBeanArrayList = new ArrayList<>();
    private static final String[] PLANETS = new String[]{"食品销售日常监督检查", "餐饮服务日常监督检查", "食品销售风险因素量化分值", "餐饮服务风险因素量化分值"};
    SelectPopupWindows selectPopupWindows;

    @Override
    protected int getContentView() {
        return R.layout.activity_xchzf;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        CompanyBean company = (CompanyBean) getIntent().getSerializableExtra("company");
        if (company != null) {
            showCompany(company);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @OnClick({R.id.ll_company, R.id.ll_company_info, R.id.et_type, R.id.et_people, R.id.et_startTime, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.ll_company_info:
                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.et_type:
                UIAdjuster.closeKeyBoard(this);
                selectPopupWindows = new SelectPopupWindows(this, PLANETS);
                selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etType.setText(msg);
                        type = index + 1;
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows.dismiss();
                    }
                });
                break;
            case R.id.et_people:
                startActivityForResult(new Intent(this, PeopleActivity.class), 2001);
                break;
            case R.id.et_startTime:
                DatePickDialog dialog = new DatePickDialog(XCHZFActivity.this);
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
                        inspectionTime = time;
                        etStartTime.setText(time);
                    }
                });
                dialog.show();
                break;

            case R.id.bt_ok:
                postDate();

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1001) {
                CompanyBean company = (CompanyBean) data.getSerializableExtra("company");
                if (company != null) {
                    showCompany(company);
                }
            } else if (requestCode == 2001) {
                ArrayList<LawEnforcerBean> arrayList = data.getParcelableArrayListExtra("select");
                if (arrayList != null) {
                    lawEnforcerBeanArrayList.clear();
                    lawEnforcerBeanArrayList.addAll(arrayList);
                    names = "";

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (i == arrayList.size() - 1) {
                            names = names + arrayList.get(i).getName();
                        } else {
                            names = names + arrayList.get(i).getName() + ",";
                        }
                    }
                    etPeople.setText(names);
                }
            }
        }
    }

    void showCompany(CompanyBean company) {
        companyBean = company;
        llCompany.setVisibility(View.GONE);
        llCompanyInfo.setVisibility(View.VISIBLE);
        etCompany.setText(company.getOperatorName() + "");
        etSocialCreditCode.setText(company.getSocialCreditCode() + "");
        etLegalRepresentative.setText(company.getLegalRepresentative() + "");
        etBusinessPlace.setText(company.getBusinessPlace() + "");
        etContact.setText(company.getContact() + "");
        etContactInformation.setText(company.getContactInformation() + "");
    }

    void postDate() {
        HashMap<String, Object> map = new HashMap<>();
        if (companyBean == null) {
            showToast("请先选择企业");
            return;
        }
        if (lawEnforcerBeanArrayList.size() < 2) {
            showToast("请选择执法人员");
            return;
        }
        if (type == 0) {
            showToast("请选择执法类型");
            return;
        }
        if (TextUtils.isEmpty(inspectionTime)) {
            showToast("请选择执法时间");
            return;
        }
        String reason = edCause.getText().toString();
        if (!TextUtils.isEmpty(reason)) {
            map.put("reason", reason);
        }

        map.put("companyId", companyBean.getId());
        map.put("inspectionTime", inspectionTime);
        map.put("lawEnforcer1", lawEnforcerBeanArrayList.get(0).getId());
        map.put("lawEnforcer2", lawEnforcerBeanArrayList.get(1).getId());
        map.put("type", type);

        RxNetUtils.request(getApi(ApiService.class).createRecord(map), new RequestObserver<JsonT<Integer>>() {
            @Override
            protected void onSuccess(JsonT<Integer> jsonT) {
                if (type == 1 || type == 2) {
                    startActivity(new Intent(XCHZFActivity.this, SuperviseActivity.class)
                            .putExtra("company", companyBean.getOperatorName())
                            .putExtra("id", jsonT.getData() + "")
                            .putExtra("type", type)
                            .putExtra("typeText", etType.getText().toString())
                            .putExtra("lawEnforcer", names)
                            .putExtra("inspectionTime", inspectionTime));
                    finish();
                } else if (type == 3 || type == 4) {
                    startActivity(new Intent(XCHZFActivity.this, RiskSuperviseActivity.class)
                            .putExtra("company", companyBean.getOperatorName())
                            .putExtra("id", jsonT.getData() + "")
                            .putExtra("type", type)
                            .putExtra("typeText", etType.getText().toString())
                            .putExtra("lawEnforcer", names)
                            .putExtra("inspectionTime", inspectionTime));
                    finish();
                }
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

}
