package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.codbking.widget.utils.UIAdjuster;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.SyxhRectificationOrder;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.widget.ItemGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.business.BusinessUtils.getRecordTypeByType;
import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 *
 */
public class CreateDecisionStatusActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_illegalActivity)
    EditText igIllegalActivity;
    @BindView(R.id.et_lawContent)
    EditText igLawContent;
    @BindView(R.id.et_accordContent)
    EditText igAccordContent;
    @BindView(R.id.iv_sign1)
    ImageView iv_sign1;
    @BindView(R.id.iv_sign2)
    ImageView iv_sign2;
    @BindView(R.id.iv_sign3)
    ImageView iv_sign3;

    @BindView(R.id.bg)
    LinearLayout bg;
    String recordId;
    @BindView(R.id.bt_ok)
    Button btOk;
    int type;

    @Override
    protected int getContentView() {
        return R.layout.activity_decision_status;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        recordId = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);
        getData();

    }
    int code = 1001;
    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_print,R.id.ll_sign1, R.id.ll_sign2,  R.id.ll_sign3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                finish();
                break;
            case R.id.bt_ok:
//                completeData();
                break;
            case R.id.bt_print:
                postData();
                break;
            case R.id.ll_sign1:
                code = 1001;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(CreateDecisionStatusActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;
            case R.id.ll_sign2:
                code = 1002;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(CreateDecisionStatusActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;
            case R.id.ll_sign3:
                code = 1003;
                PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                    @Override
                    public void onGranted() {
                        startActivityForResult(new Intent(CreateDecisionStatusActivity.this, SignActivity.class), code);
                    }

                    @Override
                    public void onDenied() {

                    }
                });
                break;

        }
    }
    String sign1;
    String sign2;
    String sign3;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("sign");
            if (requestCode == 1001) {
                sign1 = BASE64.imageToBase64(path);

                GlideUtils.loadImage(CreateDecisionStatusActivity.this, path, iv_sign1);
            } else if (requestCode == 1002) {
                sign2 = BASE64.imageToBase64(path);
                GlideUtils.loadImage(CreateDecisionStatusActivity.this, path, iv_sign2);

            } else if (requestCode == 1003) {
                sign3 = BASE64.imageToBase64(path);
                GlideUtils.loadImage(CreateDecisionStatusActivity.this, path, iv_sign3);

            }
        }
    }
    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }
    void postData() {
        Map<String,Object> map = new HashMap<>();
        map.put("illegalActivity",getText(igIllegalActivity));
        map.put("lawContent",getText(igLawContent));
        map.put("accordContent",getText(igAccordContent));
        if (TextUtils.isEmpty(sign1)){
            showToast("请当事人签名");
            return;
        }if (TextUtils.isEmpty(sign2)){
            showToast("请执法人员(一)签名");
            return;
        }if (TextUtils.isEmpty(sign3)){
            showToast("执法人员(二)签字");
            return;
        }
        map.put("companySign", sign1);
        map.put("officerSign1", sign2);
        map.put("officerSign2", sign3);
        RxNetUtils.request(getApi(ApiService.class).syxhPenaltyDecision(getRecordTypeByType(type),recordId,map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                startActivity(new Intent(CreateDecisionStatusActivity.this, ShowDocActivity.class).putExtra("id", recordId).putExtra("type", type).putExtra("tinspectSheetType", 4).putExtra("tinspectType", type).putExtra("read", 1));

            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
    void getData() {
        RxNetUtils.request(getApi(ApiService.class).getSyxhPenaltyDecision(getRecordTypeByType(type),recordId), new RequestObserver<JsonT<SyxhRectificationOrder>>(this) {
            @Override
            protected void onSuccess(JsonT<SyxhRectificationOrder> jsonT) {
                SyxhRectificationOrder data = jsonT.getData();
                igIllegalActivity.setText(data.getIllegalActivity()+"");
                igLawContent.setText(data.getLawContent()+"");
                igAccordContent.setText(data.getAccordContent()+"");
                GlideUtils.loadImage(CreateDecisionStatusActivity.this, data.getCompanySign(), iv_sign1);
                GlideUtils.loadImage(CreateDecisionStatusActivity.this, data.getOfficerSign1(), iv_sign2);
                GlideUtils.loadImage(CreateDecisionStatusActivity.this, data.getOfficerSign2(), iv_sign3);
                sign1=data.getCompanySign();
                sign2=data.getOfficerSign1();
                sign3=data.getOfficerSign2();
            }

            @Override
            protected void onFail2(JsonT<SyxhRectificationOrder> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
//                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }



}