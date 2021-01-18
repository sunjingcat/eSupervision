package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.company.AddCompanyActivity;
import com.zz.supervision.business.inspenction.adapter.DetailAdapter;
import com.zz.supervision.business.inspenction.adapter.SignInfoAdapter;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.business.risk.RiskSuperviseActivity;
import com.zz.supervision.business.risk.RiskSuperviseInfoActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.FileUtils;
import com.zz.supervision.utils.GlideUtils;
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

/**
 * 测评结果-监督签字页面
 */
public class SuperviseSignActivity extends MyBaseActivity {


    SuperviseBean.ResposeBean resposeBean;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_sumCount)
    TextView tvSumCount;
    @BindView(R.id.tv_lawEnforcer)
    TextView tvLawEnforcer;
    @BindView(R.id.tv_yearCount)
    TextView tvYearCount;

    @BindView(R.id.tv_violation)
    TextView tvViolation;
    @BindView(R.id.tv_lawEnforcer_sign)
    ImageView tvLawEnforcerSign;
    @BindView(R.id.bt_ok)
    Button bt_ok;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.tv_legalRepresentative_sign)
    ImageView tvLegalRepresentativeSign;
    String lawEnforcer_sign;
    String legalRepresentative_sign;
    String reviewerSign_sign;
    String url="";
    String id="";
    String reformTime="" ;
    @BindView(R.id.ll_lawEnforcer_sign)
    LinearLayout llLawEnforcerSign;
    @BindView(R.id.ll_legalRepresentative_sign)
    LinearLayout llLegalRepresentativeSign;
    @BindView(R.id.ll_violation)
    LinearLayout ll_violation;
    @BindView(R.id.ll_reformTime)
    LinearLayout ll_reformTime;
    @BindView(R.id.ll_sumCount)
    LinearLayout llSumCount;
    @BindView(R.id.tv_sign_1)
    TextView tvSign1;
    @BindView(R.id.et_reformTime)
    TextView et_reformTime;
    @BindView(R.id.tv_sign_2)
    TextView tvSign2;
    @BindView(R.id.tv_reviewerSign_sign)
    ImageView tvReviewerSignSign;
    @BindView(R.id.ll_reviewerSign_sign)
    LinearLayout llReviewerSignSign;
    int type;
    @BindView(R.id.ll_yearCount)
    LinearLayout llYearCount;
    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_reason)
    TextView tv_reason;
   @BindView(R.id.rv_info)
   RecyclerView rv_info;
    SignInfoAdapter adapter;
    ArrayList<DetailBean> mlist = new ArrayList<>();
    List<BusinessType> reformTimeList = new ArrayList<>();
    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_sign;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv_info.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SignInfoAdapter(R.layout.item_sign_info,mlist);
        rv_info.setAdapter(adapter);

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
            llReviewerSignSign.setVisibility(View.GONE);
        } else if (type == 2) {
            url = "cyfwInspectionRecord";
            llReviewerSignSign.setVisibility(View.GONE);
        }  else if (type == 3) {
            url = "spxsRiskRecord";
            llReviewerSignSign.setVisibility(View.VISIBLE);
            tvSign1.setText("填表人签字");
            tvSign2.setText("企业法定代表人签字");
        } else if (type == 4) {
            url = "cyfwRiskRecord";
            llReviewerSignSign.setVisibility(View.VISIBLE);
            tvSign1.setText("填表人签字");
            tvSign2.setText("企业法定代表人签字");
        }else if (type == 5) {
            url = "llglInspectionRecord";
            llReviewerSignSign.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        }else if (type == 6||type == 7) {
            url = "ypInspectionRecord";
            llReviewerSignSign.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        }else if (type == 8||type == 9||type == 10) {
            url = "ylqxInspectionRecord";
            llReviewerSignSign.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        }
        resposeBean = (SuperviseBean.ResposeBean) getIntent().getSerializableExtra("resposeBean");
        if (resposeBean != null) {
            showIntent(resposeBean);
            id = resposeBean.getId();

        } else {
            id = getIntent().getStringExtra("id");
            getData();

        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void showIntent(SuperviseBean.ResposeBean resposeBean) {
        tvCompany.setText(resposeBean.getCompanyInfo().getOperatorName() + "");
        tvSumCount.setText(resposeBean.getSumCount() + "");
        llSumCount.setVisibility(TextUtils.isEmpty(resposeBean.getSumCount()) ? View.GONE : View.VISIBLE);
        tvLawEnforcer.setText(resposeBean.getLawEnforcer1Name() + "," + resposeBean.getLawEnforcer2Name());
        tvYearCount.setText(resposeBean.getYearCount() + "");
        llYearCount.setVisibility(TextUtils.isEmpty(resposeBean.getYearCount()) ? View.GONE : View.VISIBLE);
        tvTime.setText(resposeBean.getInspectionTime() + "");
        tv_reason.setText(resposeBean.getReason() + "");
        if (type == 1 || type == 2) {
            mlist.add(new DetailBean("重点项目", resposeBean.getImportantCount() + "",true));
            mlist.add(new DetailBean("重点项问题数", resposeBean.getImportantProblemCount() + ""));
            mlist.add(new DetailBean("一般项数", resposeBean.getGeneralCount() + "",true));
            mlist.add(new DetailBean("一般项问题数", resposeBean.getGeneralProblemCount() + ""));
            mlist.add(new DetailBean("检查结果", resposeBean.getInspectionResultText() + "",true));
            mlist.add(new DetailBean("处理结果", resposeBean.getResultReductionText() + "",true));
            tvViolation.setText(resposeBean.getViolation()+"");
            ll_violation.setVisibility(TextUtils.isEmpty(resposeBean.getViolation()) ? View.GONE : View.VISIBLE);
        } else if (type == 5) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "",true));
            mlist.add(new DetailBean("问题数", resposeBean.getProblemCount() + ""));
            mlist.add(new DetailBean("检查结果", resposeBean.getInspectionResultText() + "",true));
        }  else if (type == 6||type == 7||type == 8||type == 9||type == 10) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "",true));
            mlist.add(new DetailBean("问题数", resposeBean.getProblemCount() + ""));
            if (resposeBean.getProblemCount()>0){
                ll_reformTime.setVisibility(View.VISIBLE);
                getDicts();
            }
        } else {
            mlist.add(new DetailBean("静态评分项分数", resposeBean.getStaticScore() + "",true));
            mlist.add(new DetailBean("动态评分项分数", resposeBean.getDynamicScore() + ""));
            mlist.add(new DetailBean("总分数", resposeBean.getTotalScore() + ""));
            mlist.add(new DetailBean("风险等级", resposeBean.getLevel() + ""));
        }
        adapter.notifyDataSetChanged();
        bt_ok.setText(resposeBean.getStatus() == 3 ? "打印" : "确定");
        if (resposeBean.getStatus() == 3) {
            llLawEnforcerSign.setEnabled(false);
            llLegalRepresentativeSign.setEnabled(false);
            et_reformTime.setEnabled(false);
            et_reformTime.setText(resposeBean.getReformTime());
        }
        bt_delete.setVisibility(View.VISIBLE);
        tvType.setText(resposeBean.getTypeText() + "");

        if (type == 1 || type == 2 || type == 5|| type == 6|| type == 7||type == 8||type == 9||type == 10) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), tvLawEnforcerSign);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), tvLegalRepresentativeSign);

        } else {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getFillerSign(), tvLawEnforcerSign);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOwnerSign(), tvLegalRepresentativeSign);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getReviewerSign(), tvReviewerSignSign);

        }
    }

    private CustomDialog customDialog;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.ll_lawEnforcer_sign, R.id.ll_legalRepresentative_sign, R.id.et_reformTime,R.id.bt_ok, R.id.bt_delete, R.id.ll_reviewerSign_sign, R.id.toolbar_subtitle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_lawEnforcer_sign:
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(SuperviseSignActivity.this, SignActivity.class), 1001);
                    }

                    @Override
                    public void onDenied() {

                    }
                });

                break;
            case R.id.ll_legalRepresentative_sign:
                if (resposeBean.getStatus() != 3) {
                    PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                        @Override
                        public void onGranted() {
                            startActivityForResult(new Intent(SuperviseSignActivity.this, SignActivity.class), 1002);
                        }

                        @Override
                        public void onDenied() {

                        }
                    });
                }

                break;
            case R.id.ll_reviewerSign_sign:
                if (resposeBean.getStatus() != 3) {
                    PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                        @Override
                        public void onGranted() {
                            startActivityForResult(new Intent(SuperviseSignActivity.this, SignActivity.class), 1003);
                        }

                        @Override
                        public void onDenied() {

                        }
                    });
                }

                break;
            case R.id.bt_ok:
                if (resposeBean != null && resposeBean.getStatus() == 3) {

                    if (TextUtils.isEmpty(id)) return;
                    startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id).putExtra("tinspectSheetType", 1).putExtra("tinspectType", type));
//
                } else {
                    postData();
                }

                break;
            case R.id.toolbar_subtitle:
                if (resposeBean == null) return;
                if (type == 1 || type == 2 || type == 5|| type == 6 || type == 7||type == 8||type == 9||type == 10) {
                    startActivity(new Intent(this, SuperviseInfoActivity.class)
                            .putExtra("company", resposeBean.getCompanyInfo().getOperatorName())
                            .putExtra("id", resposeBean.getId() + "")
                            .putExtra("type", type)
                            .putExtra("status", resposeBean.getStatus())
                            .putExtra("typeText", resposeBean.getTypeText() + "")
                            .putExtra("lawEnforcer", resposeBean.getLawEnforcer1Name() + "," + resposeBean.getLawEnforcer2Name())
                            .putExtra("inspectionTime", resposeBean.getInspectionTime()));
                } else {
                    startActivity(new Intent(this, RiskSuperviseInfoActivity.class)
                            .putExtra("company", resposeBean.getCompanyInfo().getOperatorName())
                            .putExtra("id", resposeBean.getId() + "")
                            .putExtra("type", type)
                            .putExtra("status", resposeBean.getStatus())
                            .putExtra("typeText", resposeBean.getTypeText() + "")
                            .putExtra("lawEnforcer", resposeBean.getLawEnforcer1Name() + "," + resposeBean.getLawEnforcer2Name())
                            .putExtra("inspectionTime", resposeBean.getInspectionTime()));
                }

                break;
            case R.id.bt_delete:
                CustomDialog.Builder builder = new CustomDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(id);
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
                case R.id.et_reformTime:
                    showSelectPopWindow1();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("sign");
            if (requestCode == 1001) {
                lawEnforcer_sign = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, tvLawEnforcerSign);
            } else if (requestCode == 1002) {
                legalRepresentative_sign = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, tvLegalRepresentativeSign);

            } else if (requestCode == 1003) {
                reviewerSign_sign = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, tvReviewerSignSign);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    void postData() {
        if (TextUtils.isEmpty(lawEnforcer_sign)) {
            if (type == 1 || type == 2||type == 5||type == 6||type == 7||type == 8||type == 9||type == 10) {
                if (type == 5||type == 6||type == 7||type == 8||type == 9||type == 10) {
                    showToast("法人签字");
                }else {
                    showToast("执法人签字");
                }
            } else {
                showToast("填表人签字");
            }
            return;
        }
        if (TextUtils.isEmpty(legalRepresentative_sign)) {
            if (type == 1 || type == 2 || type == 5|| type == 6 || type == 7||type == 8||type == 9||type == 10) {
                if (type == 5||type == 6||type == 7||type == 8||type == 9||type == 10) {
                    showToast("执法人签字");
                }else {
                    showToast("企业负责人签字");
                }

            } else {
                showToast("企业法定代表人签字");
            }
            return;
        }
        if (type == 3 || type == 4) {
            if (TextUtils.isEmpty(reviewerSign_sign)) {
                showToast("审批人签字");

                return;
            }
        }

        String companySign = BASE64.imageToBase64(lawEnforcer_sign);
        String officerSign = BASE64.imageToBase64(legalRepresentative_sign);
        String reviewerSign = BASE64.imageToBase64(reviewerSign_sign);

        if (type == 1 || type == 2 || type == 5||type == 6||type == 7||type == 8||type == 9||type == 10) {
            RxNetUtils.request(getApi(ApiService.class).submitSign(url, id, companySign, officerSign,reformTime), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    if (jsonT.isSuccess()) {
                        startActivity(new Intent(SuperviseSignActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", resposeBean).putExtra("type", type));
                        finish();
                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    showToast(userInfoJsonT.getMessage());
                }
            }, LoadingUtils.build(this));
        } else {
            RxNetUtils.request(getApi(ApiService.class).submitSignRisk(url, id, companySign, officerSign, reviewerSign), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    if (jsonT.isSuccess()) {
                        startActivity(new Intent(SuperviseSignActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", resposeBean).putExtra("type", type));
                        finish();
                    }
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    showToast(userInfoJsonT.getMessage());
                }
            }, LoadingUtils.build(this));
        }


    }

    void getData() {

        RxNetUtils.request(getApi(ApiService.class).getSuperviseDetail(url, id), new RequestObserver<JsonT<SuperviseBean.ResposeBean>>(this) {
            @Override
            protected void onSuccess(JsonT<SuperviseBean.ResposeBean> jsonT) {
                if (jsonT.isSuccess()) {
                    resposeBean = jsonT.getData();
                    showIntent(jsonT.getData());
                }
            }

            @Override
            protected void onFail2(JsonT<SuperviseBean.ResposeBean> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removeSuperviseInfo(url, id), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                finish();
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }
    public void getDicts() {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", "reformTime");
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                reformTimeList.clear();
                reformTimeList.addAll(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        },  LoadingUtils.build(this));
    }

    SelectPopupWindows selectPopupWindows;
    void showSelectPopWindow1() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < reformTimeList.size(); i++) {
            list.add(reformTimeList.get(i).getDictLabel());
            list1.add(reformTimeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                et_reformTime.setText(msg);
                reformTime = values[index];

            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }
}
