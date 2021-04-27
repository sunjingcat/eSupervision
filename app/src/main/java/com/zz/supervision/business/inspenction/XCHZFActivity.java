package com.zz.supervision.business.inspenction;

import android.annotation.SuppressLint;
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
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.LawEnforcerBean;
import com.zz.supervision.business.company.CompanyListActivity;
import com.zz.supervision.business.company.PeopleActivity;
import com.zz.supervision.business.equipment.EquipmentListActivity;
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
    @BindView(R.id.et_device)
    TextView et_device;
    @BindView(R.id.et_endTime)
    TextView et_endTime;
    @BindView(R.id.ll_device)
    LinearLayout ll_device;
    @BindView(R.id.ll_endTime)
    LinearLayout ll_endTime;
    @BindView(R.id.ed_cause)
    EditText edCause;
    @BindView(R.id.bt_ok)
    TextView btOk;
    @BindView(R.id.ll_company)
    LinearLayout llCompany;
    @BindView(R.id.v_endTime)
    View v_endTime;
    @BindView(R.id.view_device)
    View view_device;
    @BindView(R.id.view_InspectionCheckType)
    View view_InspectionCheckType;
    @BindView(R.id.et_InspectionCheckType)
    TextView et_InspectionCheckType;
    @BindView(R.id.ll_InspectionCheckType)
    LinearLayout ll_InspectionCheckType;
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
    List<EquipmentBean> equipment = new ArrayList<>();
    int type = 0;
    int inspectionType = 0;
    String names = "";
    String ids = "";
    List<BusinessType> businessTypeList = new ArrayList<>();
    List<BusinessType> inspectionCheckTypeList = new ArrayList<>();
    ArrayList<LawEnforcerBean> lawEnforcerBeanArrayList = new ArrayList<>();
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
            getLawEnforcerType();
            etType.setText("");
            type = 0;

        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @OnClick({R.id.ll_company, R.id.ll_company_info, R.id.et_type, R.id.et_people, R.id.et_startTime, R.id.bt_ok, R.id.et_device, R.id.et_endTime, R.id.ll_InspectionCheckType})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_company:
                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.ll_company_info:
                startActivityForResult(new Intent(this, CompanyListActivity.class).putExtra("select", "xczf"), 1001);
                break;
            case R.id.et_device:
                if (companyBean == null) {
                    showToast("请先选择企业");
                    return;
                }
                if (type == 0) {
                    showToast("请先选择执法类型");
                    return;
                }
                startActivityForResult(new Intent(this, SelectEquipmentActivity.class).putExtra("type",type ).putExtra("id", companyBean.getId()).putExtra("companyType", companyBean.getCompanyType() + ""), 1002);
                break;
            case R.id.et_type:
                if (businessTypeList.size() == 0) {
                    showToast("请先选择企业");
                    return;
                }
                showSelect("type", businessTypeList);
                break;
            case R.id.ll_InspectionCheckType:
                if (inspectionCheckTypeList.size() == 0) {
                    showToast("请先选择企业");
                    return;
                }
                showSelect("tzsb_inspection_category", inspectionCheckTypeList);
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
            case R.id.et_endTime:
                if (companyBean == null) {
                    showToast("请先选择企业");
                    return;
                }
                selectTime(et_endTime);
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
                    getLawEnforcerType();
                    etType.setText("");
                    type = 0;

                }
            } else if (requestCode == 1002) {
                equipment = data.getParcelableArrayListExtra("equipment");
                if (equipment != null) {
                 String   names = "";
                 ids = "";

                    for (int i = 0; i < equipment.size(); i++) {
                        if (i == equipment.size() - 1) {
                            names = names + equipment.get(i).getDeviceName();
                            ids = ids + equipment.get(i).getId();
                        } else {
                            names = names + equipment.get(i).getDeviceName() + ",";
                            ids = ids + equipment.get(i).getId() + ",";
                        }
                    }
                    et_device.setText(names + "");

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
        ll_endTime.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        ll_device.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        v_endTime.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        view_device.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        ll_InspectionCheckType.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        view_InspectionCheckType.setVisibility(company.getCompanyType() == 6 ? View.VISIBLE : View.GONE);
        if (company.getCompanyType() == 6) {
            getInspectionCheckType();
        }
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
        String inspectionTime = getText(etStartTime);
        if (TextUtils.isEmpty(inspectionTime)) {
            showToast("请选择执法时间");
            return;
        }
        String reason = edCause.getText().toString();
        if (!TextUtils.isEmpty(reason)) {
            map.put("reason", reason);
        }
        if (companyBean.getCompanyType() == 6) {
            String endTime = getText(et_endTime);
            if (TextUtils.isEmpty(endTime)) {
                showToast("请选择执法结束时间");
                return;
            }
            map.put("inspectionTimeEnd", endTime);
            map.put("inspectionTimeStart", inspectionTime);
            if (equipment .size()==0) {
                showToast("请选择执法设备");
                return;
            }
            map.put("deviceIds", ids);
            if (inspectionType==0) {
                showToast("请选择检查类型");
                return;
            }
            map.put("inspectionCheckType", inspectionType);
        }

        map.put("companyId", companyBean.getId());
        map.put("inspectionTime", inspectionTime);
        map.put("lawEnforcer1", lawEnforcerBeanArrayList.get(0).getId());
        map.put("lawEnforcer2", lawEnforcerBeanArrayList.get(1).getId());
        map.put("type", type);

        RxNetUtils.request(getApi(ApiService.class).createRecord(map), new RequestObserver<JsonT<Integer>>() {
            @Override
            protected void onSuccess(JsonT<Integer> jsonT) {
                String reason = edCause.getText().toString();
                if (type == 1 || type == 2
                        || type == 5
                        || type == 6 || type == 7
                        || type == 8 || type == 9 || type == 10
                        || type == 11|| type == 12 || type == 13 || type == 14 || type == 15 || type == 16 || type == 17|| type == 18) {
                    startActivity(new Intent(XCHZFActivity.this, SuperviseActivity.class)
                            .putExtra("company", companyBean.getOperatorName())
                            .putExtra("id", jsonT.getData() + "")
                            .putExtra("type", type)
                            .putExtra("typeText", etType.getText().toString())
                            .putExtra("lawEnforcer", names)
                            .putExtra("reason", reason + "")
                            .putExtra("inspectionTime", inspectionTime));
                    finish();
                } else if (type == 3 || type == 4) {
                    startActivity(new Intent(XCHZFActivity.this, RiskSuperviseActivity.class)
                            .putExtra("company", companyBean.getOperatorName())
                            .putExtra("id", jsonT.getData() + "")
                            .putExtra("type", type)
                            .putExtra("typeText", etType.getText().toString())
                            .putExtra("lawEnforcer", names)
                            .putExtra("reason", reason + "")
                            .putExtra("inspectionTime", inspectionTime));
                    finish();
                }
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                showToast(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    private void selectTime(TextView textView) {
        DateType dateType = DateType.TYPE_YMD;
        String format = "yyyy-MM-dd";
        switch (companyBean.getCompanyType()) {
            case 3:
            case 4:
            case 6:
                dateType = DateType.TYPE_YMDHM;
                format = "yyyy-MM-dd HH:mm";
                break;

        }
        DatePickDialog dialog = new DatePickDialog(XCHZFActivity.this);
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

    private void showSelect(String selectType, List<BusinessType> businessTypeList) {
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
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (selectType.equals("tzsb_inspection_category")) {
                    et_InspectionCheckType.setText(msg);
                    inspectionType = Integer.parseInt(values[index]);
                } else {
                    etType.setText(msg);
                    type = Integer.parseInt(values[index]);
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    public void getLawEnforcerType() {
        Map<String, Object> map = new HashMap<>();
        map.put("companyType", companyBean.getCompanyType() + "");
        RxNetUtils.request(getApi(ApiService.class).getInspectionTypeByCompanyType(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                businessTypeList.clear();
                businessTypeList.addAll(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
                businessTypeList.clear();
            }
        }, LoadingUtils.build(this));
    }

    public void getInspectionCheckType() {
        Map<String, Object> map = new HashMap<>();
        map.put("dictType", "tzsb_inspection_category");
        RxNetUtils.request(getApi(ApiService.class).getDicts(map), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                inspectionCheckTypeList.clear();
                inspectionCheckTypeList.addAll(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
                inspectionCheckTypeList.clear();
            }
        }, LoadingUtils.build(this));
    }
}
