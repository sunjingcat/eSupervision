package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.zz.supervision.bean.SceneRecord;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.SignInfoAdapter;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.business.risk.RiskSuperviseInfoActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemArea;
import com.zz.supervision.widget.ItemGroup;

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
 * 4.记录详情
 */
public class SceneRecordInfoActivity extends MyBaseActivity {
    SceneRecord resposeBean;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_lawEnforcer)
    TextView tvLawEnforcer;
    @BindView(R.id.tv_yearCount)
    TextView tvYearCount;
    @BindView(R.id.iv_sign1)
    ImageView iv_sign1;
    @BindView(R.id.bt_print)
    Button bt_print;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.iv_sign2)
    ImageView iv_sign2;
    String url = "";
    int id = 0;
    @BindView(R.id.tv_sign_1)
    TextView tvSign1;
    @BindView(R.id.tv_sign_2)
    TextView tvSign2;
    @BindView(R.id.tv_sign_3)
    TextView tvSign3;
    @BindView(R.id.iv_sign3)
    ImageView iv_sign3;

    @BindView(R.id.ll_sign3)
    LinearLayout ll_sign3;
    int type;
    @BindView(R.id.ll_yearCount)
    LinearLayout llYearCount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tv_reason;
    @BindView(R.id.ig_party_is_see)
    ItemGroup ig_party_is_see;
    @BindView(R.id.ig_party_apply_void)
    ItemGroup ig_party_apply_void;
    @BindView(R.id.ig_party_is_told)
    ItemGroup ig_party_is_told;
    @BindView(R.id.ig_is_check_site)
    ItemGroup ig_is_check_site;
    @BindView(R.id.ia_partyDescription)
    ItemArea ia_partyDescription;
    @BindView(R.id.ia_siteCondition)
    ItemArea ia_siteCondition;
    @BindView(R.id.ia_partyAttendance)
    ItemArea ia_partyAttendance;
    @Override
    protected int getContentView() {
        return R.layout.activity_scene_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        type = getIntent().getIntExtra("type", 0);
        url = "sceneRecord";
        ll_sign3.setVisibility(View.VISIBLE);
        tvSign1.setText("当事人签字");
        tvSign2.setText("见证人签字");
        tvSign3.setText("检查人员签字");


           id = getIntent().getIntExtra("id",0);
            getData();


    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    public void showIntent(SceneRecord resposeBean) {
        tvCompany.setText(resposeBean.getOperatorName() + "");
        tvLawEnforcer.setText(resposeBean.getLawEnforcer1Name() + "," + resposeBean.getLawEnforcer2Name());
        tvYearCount.setText(resposeBean.getYearCount() + "");
        tvTime.setText(resposeBean.getInspectionTime() + "");
        tv_reason.setText(resposeBean.getReason() + "");

        ig_party_is_see.setChooseContent(resposeBean.getPartyIsSeeText());
        ig_party_apply_void.setChooseContent(resposeBean.getPartyApplyVoidText());
        ig_party_is_told.setChooseContent(resposeBean.getPartyIsToldText());
        ig_is_check_site.setChooseContent(resposeBean.getIsCheckSiteText());
        ia_partyDescription.setChooseContent(resposeBean.getPartyDescription());
        ia_siteCondition.setChooseContent(resposeBean.getSiteCondition());
        ia_partyAttendance.setChooseContent(resposeBean.getPartyAttendance());



        bt_delete.setVisibility(View.VISIBLE);
        tvType.setText("现场笔录");

        GlideUtils.loadImage(SceneRecordInfoActivity.this, resposeBean.getPartySign(), iv_sign1);
        GlideUtils.loadImage(SceneRecordInfoActivity.this, resposeBean.getObserverSign(), iv_sign2);
        GlideUtils.loadImage(SceneRecordInfoActivity.this, resposeBean.getOfficerSign(), iv_sign3);
    }

    private CustomDialog customDialog;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.bt_print, R.id.bt_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_print:

                if (id==0) return;
                startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id+"").putExtra("tinspectSheetType", 1).putExtra("tinspectType", 100));
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
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

    void getData() {

        RxNetUtils.request(getApi(ApiService.class).getSceneRecordDetail(id+""), new RequestObserver<JsonT<SceneRecord>>(this) {
            @Override
            protected void onSuccess(JsonT<SceneRecord> jsonT) {
                if (jsonT.isSuccess()) {
                    resposeBean = jsonT.getData();
                    showIntent(jsonT.getData());
                }
            }

            @Override
            protected void onFail2(JsonT<SceneRecord> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    void deleteDate(int id) {
        RxNetUtils.request(getApi(ApiService.class).removeSuperviseInfo(url, id+""), new RequestObserver<JsonT>() {
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
