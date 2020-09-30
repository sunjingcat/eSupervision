package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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

import java.util.HashMap;
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
    @BindView(R.id.tv_legalRepresentative_sign)
    ImageView tvLegalRepresentativeSign;
    String lawEnforcer_sign;
    String legalRepresentative_sign;

    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_sign;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);


        resposeBean = (SuperviseBean.ResposeBean) getIntent().getSerializableExtra("resposeBean");
        if (resposeBean != null) {
            showIntent(resposeBean);
        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
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
//        tvType.setText(lightDevice.);


    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.ll_lawEnforcer_sign, R.id.ll_legalRepresentative_sign, R.id.bt_ok})
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
                postData();

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

    void postData() {
        if (TextUtils.isEmpty(lawEnforcer_sign)) {
            showToast("执法人签字");
            return;
        }
        if (TextUtils.isEmpty(legalRepresentative_sign)) {
            showToast("企业负责人签字");
            return;
        }
        Map<String, Object> params = new HashMap<>();
        String companySign = BASE64.imageToBase64(lawEnforcer_sign);
        String officerSign = BASE64.imageToBase64(legalRepresentative_sign);
        params.put("companySign", companySign);
        params.put("officerSign", officerSign);
        RxNetUtils.request(getApi(ApiService.class).submitSign(resposeBean.getId(), params), new RequestObserver<JsonT>(this) {
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
}
