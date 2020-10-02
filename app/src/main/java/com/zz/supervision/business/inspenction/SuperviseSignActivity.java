package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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
    @BindView(R.id.tv_importantCount)
    TextView tvImportantCount;
    @BindView(R.id.tv_importantDetail)
    TextView tvImportantDetail;
    @BindView(R.id.tv_importantProblemCount)
    TextView tvImportantProblemCount;
    @BindView(R.id.tv_importantProblemDetail)
    TextView tvImportantProblemDetail;
    @BindView(R.id.tv_generalCount)
    TextView tvGeneralCount;
    @BindView(R.id.tv_generalDetail)
    TextView tvGeneralDetail;
    @BindView(R.id.tv_generalProblemCount)
    TextView tvGeneralProblemCount;
    @BindView(R.id.tv_generalProblemDetail)
    TextView tvGeneralProblemDetail;
    @BindView(R.id.tv_inspectionResult)
    TextView tvInspectionResult;
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
    String url;
    String id;
    @BindView(R.id.ll_lawEnforcer_sign)
    LinearLayout llLawEnforcerSign;
    @BindView(R.id.ll_legalRepresentative_sign)
    LinearLayout llLegalRepresentativeSign;

    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_sign;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


        int type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
        } else if (type == 2) {
            url = "cyfwInspectionRecord";
        } else if (type == 3) {
            url = "spxsRiskRecord";
        } else {
            url = "cyfwRiskRecord";

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
        tvLawEnforcer.setText(resposeBean.getLawEnforcer1Name() + "/" + resposeBean.getLawEnforcer2Name());
        tvYearCount.setText(resposeBean.getYearCount() + "");
        tvImportantCount.setText(resposeBean.getImportantCount() + "");
        tvImportantDetail.setText(resposeBean.getImportantDetail() + "");
        tvImportantProblemCount.setText(resposeBean.getImportantProblemCount() + "");
        tvImportantProblemDetail.setText(resposeBean.getImportantProblemDetail() + "");
        tvGeneralCount.setText(resposeBean.getGeneralCount() + "");
        tvGeneralDetail.setText(resposeBean.getGeneralDetail() + "");
        tvGeneralProblemCount.setText(resposeBean.getGeneralProblemCount() + "");
        tvGeneralProblemDetail.setText(resposeBean.getGeneralProblemDetail() + "");
        tvInspectionResult.setText(resposeBean.getInspectionResult() + "");
        tvViolation.setText(resposeBean.getViolation() + "");
        bt_ok.setText(resposeBean.getStatus() == 3 ? "打印" : "确定");
        if (resposeBean.getStatus() == 3) {
            llLawEnforcerSign.setEnabled(false);
            llLegalRepresentativeSign.setEnabled(false);
            bt_delete.setVisibility(View.VISIBLE);
        }else {
            bt_delete.setVisibility(View.GONE);
        }
//        tvType.setText(lightDevice.);

        GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), tvLawEnforcerSign);

        GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), tvLegalRepresentativeSign);

    }
    private CustomDialog customDialog;
    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.ll_lawEnforcer_sign, R.id.ll_legalRepresentative_sign, R.id.bt_ok, R.id.bt_delete})
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
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(SuperviseSignActivity.this, SignActivity.class), 1002);
                    }

                    @Override
                    public void onDenied() {

                    }
                });

                break;
            case R.id.bt_ok:
                if (resposeBean != null && resposeBean.getStatus() == 3) {
                    showToast("打印");
                } else {
                    postData();
                }

                break;
            case R.id.bt_delete:
                CustomDialog.Builder builder = new com.troila.customealert.CustomDialog.Builder(this)
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
            showToast("执法人签字");
            return;
        }
        if (TextUtils.isEmpty(legalRepresentative_sign)) {
            showToast("企业负责人签字");
            return;
        }
        String companySign = BASE64.imageToBase64(lawEnforcer_sign);
        String officerSign = BASE64.imageToBase64(legalRepresentative_sign);

        RxNetUtils.request(getApi(ApiService.class).submitSign(url, id, companySign, officerSign), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                if (jsonT.isSuccess()) {
                    startActivity(new Intent(SuperviseSignActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", resposeBean));
                }
            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
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
        RxNetUtils.request(getApi(ApiService.class).removeCompanyInfo(id), new RequestObserver<JsonT>() {
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

}
