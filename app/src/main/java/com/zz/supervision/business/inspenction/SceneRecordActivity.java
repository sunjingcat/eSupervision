package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.business.company.CompanyListActivity;
import com.zz.supervision.business.company.PeopleActivity;
import com.zz.supervision.business.company.ProductListActivity;
import com.zz.supervision.business.equipment.SelectEquipmentActivity;
import com.zz.supervision.business.risk.RiskSuperviseActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 现场笔录
 */
public class SceneRecordActivity extends MyBaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_company)
    TextView etCompany;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;
    @BindView(R.id.et_people)
    TextView etPeople;
    @BindView(R.id.et_startTime)
    TextView etStartTime;
    @BindView(R.id.tv_startTime)
    TextView tv_startTime;
    @BindView(R.id.ll_endTime)
    LinearLayout ll_endTime;
    @BindView(R.id.et_endTime)
    TextView et_endTime;
    @BindView(R.id.v_endTime)
    View v_endTime;
    @BindView(R.id.ed_cause)
    EditText edCause;
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
    @BindView(R.id.ll_type)
    LinearLayout ll_type;
    CompanyBean companyBean;
    String names = "";
    ArrayList<LawEnforcerBean> lawEnforcerBeanArrayList = new ArrayList<>();
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
        ll_type.setVisibility(View.GONE);
        toolbar_title.setText("现场笔录");
        tv_startTime.setText("开始时间");
        ll_endTime.setVisibility(View.VISIBLE);
        v_endTime.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @OnClick({R.id.ll_company, R.id.ll_company_info, R.id.et_type, R.id.et_people, R.id.et_startTime, R.id.et_endTime, R.id.bt_ok,})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
//                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.ll_company_info:
//                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.et_people:
                startActivityForResult(new Intent(this, PeopleActivity.class), 2001);
                break;
            case R.id.et_startTime:
                if (companyBean == null) {
                    showToast("请先选择企业");
                    return;
                }
                selectTime(etStartTime);
                break;
            case R.id.bt_ok:
                postDate();

                break;
            case R.id.et_endTime:
                if (companyBean == null) {
                    showToast("请先选择企业");
                    return;
                }
                selectTime(et_endTime);
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
            }  else if (requestCode == 2001) {
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

        tv_startTime.setText("执法时间");

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


        String inspectionTimeStart = getText(etStartTime);
        if (TextUtils.isEmpty(inspectionTimeStart)) {
            showToast("请选择执法时间");
            return;
        }

        String inspectionTimeEnd = getText(et_endTime);
        if (TextUtils.isEmpty(inspectionTimeEnd)) {
            showToast("请选择执法时间");
            return;
        }
        String reason = edCause.getText().toString();
        if (!TextUtils.isEmpty(reason)) {
            map.put("reason", reason);
        }

        map.put("companyId", companyBean.getId());
        map.put("type", companyBean.getCompanyType());
        map.put("inspectionTimeStart", inspectionTimeStart);
        map.put("inspectionTimeEnd", inspectionTimeEnd);
        map.put("lawEnforcer1", lawEnforcerBeanArrayList.get(0).getId());
        map.put("lawEnforcer2", lawEnforcerBeanArrayList.get(1).getId());

        RxNetUtils.request(getApi(ApiService.class).createSceneRecord(map), new RequestObserver<JsonT<Integer>>() {
            @Override
            protected void onSuccess(JsonT<Integer> jsonT) {
                String reason = edCause.getText().toString();
                    startActivity(new Intent(SceneRecordActivity.this, SceneRecordDetailActivity.class)
                            .putExtra("company", companyBean.getOperatorName())
                            .putExtra("id", jsonT.getData() + "")
                            .putExtra("lawEnforcer", names)
                            .putExtra("reason", reason + "")
                            .putExtra("inspectionTimeStart", inspectionTimeStart)
                            .putExtra("inspectionTimeEnd", inspectionTimeEnd)
                    );
                    finish();
                }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                showToast(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    private void selectTime(TextView textView) {
        DateType dateType = DateType.TYPE_YMDHM;
        String format = "yyyy-MM-dd HH:mm";
        DatePickDialog dialog = new DatePickDialog(SceneRecordActivity.this);
        //设置上下年分限制
        //设置上下年分限制
        dialog.setYearLimt(20);
        //设置标题
        dialog.setTitle("选择时间");
        //设置类型
        dialog.setType(dateType);
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat(format);
        //设置选择回调
        dialog.setOnChangeLisener(new OnChangeLisener() {
            @Override
            public void onChanged(Date date) {
                Log.v("+++", date.toString());
            }
        });
        //设置点击确定按钮回调
        String finalFormat = format;
        dialog.setOnSureLisener(new OnSureLisener() {
            @Override
            public void onSure(Date date) {
                String time = TimeUtils.getTime(date.getTime(), new SimpleDateFormat(finalFormat));
                textView.setText(time);
            }
        });
        dialog.show();
    }
}
