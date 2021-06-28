package com.zz.supervision.business.inspenction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.PermissionUtils;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.http.utils.ToastUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DetailBean;
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
import com.zz.supervision.widget.ItemGroup;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Field;

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
    @BindView(R.id.et_violation)
    TextView etViolation;
    @BindView(R.id.iv_sign1)
    ImageView iv_sign1;
    @BindView(R.id.bt_ok)
    Button bt_ok;
    @BindView(R.id.bt_print)
    Button bt_print;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.iv_sign2)
    ImageView iv_sign2;
    String sign1;
    String sign2;
    String sign3;
    String sign4;
    String url = "";
    String id = "";
    String reformTime = "";
    int resultReduction = 0;
    String inspectionOpinion = "";
    @BindView(R.id.ll_sign1)
    LinearLayout ll_sign1;
    @BindView(R.id.ll_sign2)
    LinearLayout ll_sign2;
    @BindView(R.id.ll_violation)
    LinearLayout ll_violation;
    @BindView(R.id.ll_reformTime)
    LinearLayout ll_reformTime;
    @BindView(R.id.ll_result_reduction)
    LinearLayout ll_result_reduction;
    @BindView(R.id.ll_inspection_opinion)
    LinearLayout ll_inspection_opinion;
    @BindView(R.id.ll_sumCount)
    LinearLayout llSumCount;
    @BindView(R.id.tv_sign_1)
    TextView tvSign1;
    @BindView(R.id.et_reformTime)
    TextView et_reformTime;
    @BindView(R.id.et_result_reduction)
    TextView et_result_reduction;
    @BindView(R.id.et_inspection_opinion)
    TextView et_inspection_opinion;
    @BindView(R.id.tv_sign_2)
    TextView tvSign2;
    @BindView(R.id.tv_sign_3)
    TextView tvSign3;
    @BindView(R.id.tv_sign_4)
    TextView tvSign4;
    @BindView(R.id.iv_sign3)
    ImageView iv_sign3;
    @BindView(R.id.iv_sign4)
    ImageView iv_sign4;
    @BindView(R.id.ll_sign3)
    LinearLayout ll_sign3;
    @BindView(R.id.ll_sign4)
    LinearLayout ll_sign4;
    int type;
    @BindView(R.id.ll_yearCount)
    LinearLayout llYearCount;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.text_violation)
    TextView text_violation;

    @BindView(R.id.layout_sign_zdgyp)
    LinearLayout layout_sign_zdgyp;
    @BindView(R.id.ig_isRandomCheck)
    ItemGroup ig_isRandomCheck;
    @BindView(R.id.ig_randomCheckType)
    ItemGroup ig_randomCheckType;
    @BindView(R.id.ig_inspectionMethod)
    ItemGroup ig_inspectionMethod;
    @BindView(R.id.ig_inspectionResult)
    ItemGroup ig_inspectionResult;
    @BindView(R.id.et_reductionOpinion)
    EditText et_reductionOpinion;

    @BindView(R.id.tv_reason)
    TextView tv_reason;
    @BindView(R.id.rv_info)
    RecyclerView rv_info;
    @BindView(R.id.bt_orderStatus)
    Button bt_orderStatus;
    @BindView(R.id.bt_decisionStatus)
    Button bt_decisionStatus;
    @BindView(R.id.bt_replyStatus)
    Button bt_replyStatus;
    @BindView(R.id.ly_status)
    LinearLayout ly_status;
    SignInfoAdapter adapter;
    ArrayList<DetailBean> mlist = new ArrayList<>();
    List<BusinessType> reformTimeList = new ArrayList<>();
    List<BusinessType> inspection_opinionList = new ArrayList<>();
    List<BusinessType> result_reductionList = new ArrayList<>();
    List<BusinessType> zdgyp_inspection_result = new ArrayList<>();
    List<BusinessType> zdgyp_inspection_method = new ArrayList<>();
    List<BusinessType> random_check_type = new ArrayList<>();
    List<BusinessType> is_random_check = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_supervise_sign;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv_info.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SignInfoAdapter(R.layout.item_sign_info, mlist);
        rv_info.setAdapter(adapter);

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
        } else if (type == 2) {
            url = "cyfwInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
        } else if (type == 3) {
            url = "spxsRiskRecord";
            ll_sign3.setVisibility(View.VISIBLE);
            tvSign1.setText("填表人签字");
            tvSign2.setText("企业法定代表人签字");
        } else if (type == 4) {
            url = "cyfwRiskRecord";
            ll_sign3.setVisibility(View.VISIBLE);
            tvSign1.setText("填表人签字");
            tvSign2.setText("企业法定代表人签字");
        } else if (type == 5) {
            url = "llglInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        } else if (type == 6 || type == 7) {
            url = "ypInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        } else if (type == 8 || type == 9 || type == 10) {
            url = "ylqxInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
        } else if (type >= 11 && type <= 18) {
            url = "tzsbInspectionRecord";
            ll_sign3.setVisibility(View.VISIBLE);
            ll_inspection_opinion.setVisibility(View.VISIBLE);

            tvSign1.setText("企业负责人签字");
            tvSign2.setText("检查人员签字");
            tvSign3.setText("记录员签字");
            text_violation.setText("检查中发现的其他问题");
        } else if (type == 19) {
            url = "hzpInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
            tvSign1.setText("企业陪同人员签字");
            tvSign2.setText("执法人签字");
        } else if (type == 20) {
            url = "ypRiskRecord";
            ll_sign3.setVisibility(View.VISIBLE);
            ll_sign4.setVisibility(View.VISIBLE);
            tvSign1.setText("法定代表人签字");
            tvSign2.setText("质量负责人签字");
            tvSign3.setText("执法人员(一)");
            tvSign4.setText("执法人员(二)");
        } else if (type == 21) {
            url = "zdgypInspectionRecord";
            ll_sign3.setVisibility(View.GONE);
            layout_sign_zdgyp.setVisibility(View.VISIBLE);
            tvSign1.setText("法人签字");
            tvSign2.setText("执法人签字");
            getDicts("is_random_check");
            getDicts("random_check_type");
            getDicts("zdgyp_inspection_method");
            getDicts("zdgyp_inspection_result");
        }
        resposeBean = (SuperviseBean.ResposeBean) getIntent().getSerializableExtra("resposeBean");
        if (resposeBean != null) {
            showIntent(resposeBean);
            id = resposeBean.getId();

        } else {
            id = getIntent().getStringExtra("id");
            getData();

        }
        ig_isRandomCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(is_random_check, ig_isRandomCheck);
            }
        });
        ig_randomCheckType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(random_check_type, ig_randomCheckType);
            }
        });
        ig_inspectionMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(zdgyp_inspection_method, ig_inspectionMethod);
            }
        });
        ig_inspectionResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(zdgyp_inspection_result, ig_inspectionResult);
            }
        });
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
            mlist.add(new DetailBean("重点项目", resposeBean.getImportantCount() + "", true));
            mlist.add(new DetailBean("重点项问题数", resposeBean.getImportantProblemCount() + ""));
            mlist.add(new DetailBean("一般项数", resposeBean.getGeneralCount() + "", true));
            mlist.add(new DetailBean("一般项问题数", resposeBean.getGeneralProblemCount() + ""));
            mlist.add(new DetailBean("检查结果", resposeBean.getInspectionResultText() + "", true));
            mlist.add(new DetailBean("处理结果", resposeBean.getResultReductionText() + "", true));
            tvViolation.setText(resposeBean.getViolation() + "");
            ll_violation.setVisibility(TextUtils.isEmpty(resposeBean.getViolation()) ? View.GONE : View.VISIBLE);
        } else if (type == 5) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "", true));
            mlist.add(new DetailBean("问题数", resposeBean.getProblemCount() + ""));
            mlist.add(new DetailBean("检查结果", resposeBean.getInspectionResultText() + "", true));
        } else if (type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "", true));
            mlist.add(new DetailBean("问题数", resposeBean.getProblemCount() + ""));
            if (resposeBean.getProblemCount() > 0) {
                ll_reformTime.setVisibility(View.VISIBLE);

                getDicts("reformTime");
            }
        } else if (type >= 11 && type <= 18) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "", true));
            mlist.add(new DetailBean("不符合规范项数", resposeBean.getProblemCount() + ""));
            getDicts("tzsb_inspection_opinion");
            if (resposeBean.getProblemCount() > 0) {
                ll_reformTime.setVisibility(View.VISIBLE);
                ll_result_reduction.setVisibility(View.VISIBLE);
                if (resposeBean.getStatus() != 3) {
                    getDicts("tzsb_result_reduction");
                    getDicts("reformTime");
                }
            }

            etViolation.setText(resposeBean.getViolation() + "");
            tvViolation.setVisibility(View.GONE);
            etViolation.setVisibility(View.VISIBLE);
            ll_violation.setVisibility(View.VISIBLE);
            if (resposeBean.getStatus() == 3) {
                inspectionOpinion = resposeBean.getInspectionOpinion();
                resultReduction = resposeBean.getResultReduction();
                et_inspection_opinion.setText(resposeBean.getInspectionOpinionText());
                et_result_reduction.setText(resposeBean.getResultReductionText());
                et_inspection_opinion.setEnabled(false);
                et_result_reduction.setEnabled(false);
                et_result_reduction.setCompoundDrawables(null, null, null, null);
                et_inspection_opinion.setCompoundDrawables(null, null, null, null);
                tvViolation.setText(resposeBean.getViolation() + "");
                tvViolation.setVisibility(View.VISIBLE);
                etViolation.setVisibility(View.GONE);
                ll_violation.setVisibility(TextUtils.isEmpty(resposeBean.getViolation()) ? View.GONE : View.VISIBLE);
            }
        } else if (type == 19) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "", true));
            mlist.add(new DetailBean("问题数", resposeBean.getProblemCount() + ""));
            if (resposeBean.getProblemCount() > 0) {
                ll_reformTime.setVisibility(View.VISIBLE);
                getDicts("reformTime");
            }
        } else if (type == 20) {
            mlist.add(new DetailBean("严重缺陷数", resposeBean.getSeriousCount() + ""));
            mlist.add(new DetailBean("主要缺陷数", resposeBean.getProblemCount() + ""));
            mlist.add(new DetailBean("一般缺陷数", resposeBean.getGeneralCount() + ""));
            mlist.add(new DetailBean("合理缺陷数", resposeBean.getReasonableCount() + ""));
            mlist.add(new DetailBean("评定级别", resposeBean.getLevel() + ""));
            mlist.add(new DetailBean("评定类别", resposeBean.getGradeText() + ""));

        } else if (type == 21) {
            mlist.add(new DetailBean("检查项数目", resposeBean.getSumCount() + "", true));
            mlist.add(new DetailBean("不符合规范项数", resposeBean.getProblemCount() + ""));

        } else {
            mlist.add(new DetailBean("静态评分项分数", resposeBean.getStaticScore() + "", true));
            mlist.add(new DetailBean("动态评分项分数", resposeBean.getDynamicScore() + ""));
            mlist.add(new DetailBean("总分数", resposeBean.getTotalScore() + ""));
            mlist.add(new DetailBean("风险等级", resposeBean.getLevel() + ""));
        }
        adapter.notifyDataSetChanged();
        bt_ok.setText(resposeBean.getStatus() == 3 ? "打印" : "确定");

        if (resposeBean.getStatus() == 3) {
            ll_sign1.setEnabled(false);
            ll_sign2.setEnabled(false);
            et_reformTime.setEnabled(false);
            et_reformTime.setText(resposeBean.getReformTimeText());
            et_reformTime.setHint("");
            et_reformTime.setCompoundDrawables(null, null, null, null);
            if (resultReduction != 0 && resultReduction != 4) {
                bt_print.setVisibility(View.VISIBLE);
                bt_ok.setText("打印记录表");
            }
        }
        bt_delete.setVisibility(View.VISIBLE);
        tvType.setText(resposeBean.getTypeText() + "");

        if (type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);

//            legalRepresentative_sign = resposeBean.getOfficerSign();
//            lawEnforcer_sign = resposeBean.getCompanySign();

        } else if (type == 1 || type == 2) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign2);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign1);
//            legalRepresentative_sign = resposeBean.getCompanySign();
//            lawEnforcer_sign = resposeBean.getOfficerSign();

        } else if (type >= 11 && type <= 18) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getRecordSign(), iv_sign3);

//            legalRepresentative_sign = resposeBean.getOfficerSign();
//            lawEnforcer_sign = resposeBean.getCompanySign();
//            reviewerSign_sign=resposeBean.getRecordSign();

        }
        if (type == 19) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);

//            legalRepresentative_sign = resposeBean.getOfficerSign();
//            lawEnforcer_sign = resposeBean.getCompanySign();

        }
        if (type == 20) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);

        }
        if (type == 21) {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getCompanySign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOfficerSign(), iv_sign2);

//            legalRepresentative_sign = resposeBean.getOfficerSign();
//            lawEnforcer_sign = resposeBean.getCompanySign();

        } else {
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getFillerSign(), iv_sign1);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getOwnerSign(), iv_sign2);
            GlideUtils.loadImage(SuperviseSignActivity.this, resposeBean.getReviewerSign(), iv_sign3);

//            legalRepresentative_sign = resposeBean.getOwnerSign();
//            lawEnforcer_sign = resposeBean.getFillerSign();
//            reviewerSign_sign=resposeBean.getReviewerSign();

        }
        ly_status.setVisibility(resposeBean.getStatus() == 3 ? View.VISIBLE : View.GONE);
        bt_orderStatus.setVisibility(resposeBean.getOrderStatus() == 0 ? View.GONE : View.VISIBLE);
        bt_decisionStatus.setVisibility(resposeBean.getDecisionStatus() == 0 ? View.GONE : View.VISIBLE);
        bt_replyStatus.setVisibility(resposeBean.getReplyStatus() == 0 ? View.GONE : View.VISIBLE);

        bt_orderStatus.setText(resposeBean.getOrderStatus() == 1 ? "生成责令改正通知书" : "打印责令改正通知书");
        bt_decisionStatus.setText(resposeBean.getDecisionStatus() == 1 ? "生成行政处罚决定书" : "打印行政处罚决定书");
        bt_replyStatus.setText(resposeBean.getReplyStatus() == 1 ? "生成送达回证" : "打印送达回证");


    }

    private CustomDialog customDialog;

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.ll_sign1, R.id.ll_sign2, R.id.et_reformTime, R.id.bt_ok, R.id.bt_print,
            R.id.bt_delete, R.id.ll_sign3, R.id.ll_sign4, R.id.toolbar_subtitle, R.id.et_inspection_opinion,
            R.id.et_result_reduction, R.id.bt_orderStatus, R.id.bt_decisionStatus, R.id.bt_replyStatus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sign1:
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
            case R.id.ll_sign2:
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
            case R.id.ll_sign3:
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
            case R.id.ll_sign4:
                if (resposeBean.getStatus() != 3) {
                    PermissionUtils.getInstance().checkPermission(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionUtils.OnPermissionChangedListener() {
                        @Override
                        public void onGranted() {
                            startActivityForResult(new Intent(SuperviseSignActivity.this, SignActivity.class), 1004);
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
            case R.id.bt_print:
                if (resposeBean != null && resposeBean.getStatus() == 3) {
                    if (TextUtils.isEmpty(id)) return;
                    startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id).putExtra("tinspectSheetType", 2).putExtra("tinspectType", 100));

//
                }
                break;
            case R.id.toolbar_subtitle:
                if (resposeBean == null) return;
                if (type == 3 || type == 4) {
                    startActivity(new Intent(this, RiskSuperviseInfoActivity.class)
                            .putExtra("company", resposeBean.getCompanyInfo().getOperatorName())
                            .putExtra("id", resposeBean.getId() + "")
                            .putExtra("type", type)
                            .putExtra("status", resposeBean.getStatus())
                            .putExtra("typeText", resposeBean.getTypeText() + "")
                            .putExtra("lawEnforcer", resposeBean.getLawEnforcer1Name() + "," + resposeBean.getLawEnforcer2Name())
                            .putExtra("inspectionTime", resposeBean.getInspectionTime()));

                } else {
                    startActivity(new Intent(this, SuperviseInfoActivity.class)
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

                if (type >= 11 && type <= 18) {
                    DatePickDialog dialog = new DatePickDialog(SuperviseSignActivity.this);
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

                            et_reformTime.setText(time);
                            reformTime = time;

                        }
                    });
                    dialog.show();

                } else {
                    showSelectPopWindow1(reformTimeList, "reformTime");
                }
                break;
            case R.id.et_inspection_opinion:

                showSelectPopWindow1(inspection_opinionList, "tzsb_inspection_opinion");
                break;
            case R.id.et_result_reduction:

                showSelectPopWindow1(result_reductionList, "tzsb_result_reduction");
                break;
            case R.id.bt_orderStatus:
                if (resposeBean != null && resposeBean.getStatus() == 3) {
                    if (resposeBean.getOrderStatus()==1) {
                        startActivity(new Intent(SuperviseSignActivity.this, CreateStatusActivity.class ).putExtra("id", ""));
                    }else {
                        startActivity(new Intent(SuperviseSignActivity.this,  ShowDocActivity.class).putExtra("tinspectSheetType", 3).putExtra("tinspectType", type).putExtra("read", 1));

                    }
                }
                break;
            case R.id.bt_decisionStatus:

                break;
            case R.id.bt_replyStatus:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            String path = data.getStringExtra("sign");
            if (requestCode == 1001) {
                sign1 = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, iv_sign1);
            } else if (requestCode == 1002) {
                sign2 = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, iv_sign2);

            } else if (requestCode == 1003) {
                sign3 = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, iv_sign3);

            } else if (requestCode == 1004) {
                sign4 = path;
                GlideUtils.loadImage(SuperviseSignActivity.this, path, iv_sign4);

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
        if (TextUtils.isEmpty(sign1)) {
            if (type == 1 || type == 2) {
                showToast("执法人签字");
            } else if (type >= 11 && type <= 18) {
                showToast("企业负责人签字");
            } else if (type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
                showToast("法人签字");
            } else if (type == 19) {
                showToast("企业陪同人员签字");
            } else if (type == 20) {
                showToast("法定代表人签字");
            } else if (type == 21) {
                showToast("法人签字");
            } else {
                showToast("填表人签字");
            }
            return;
        }
        if (TextUtils.isEmpty(sign2)) {
            if (type == 1 || type == 2) {
                showToast("企业负责人签字");
            } else if (type >= 11 && type <= 18) {
                showToast("检查人员签字");
            } else if (type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10) {
                showToast("执法人签字");
            } else if (type == 19) {
                showToast("执法人签字");
            } else if (type == 20) {
                showToast("质量负责人签字");
            } else if (type == 21) {
                showToast("执法人签字");
            } else {
                showToast("企业法定代表人签字");
            }
            return;
        }
        if (TextUtils.isEmpty(sign3)) {
            if (type == 3 || type == 4) {
                showToast("审批人签字");
                return;
            } else if (type >= 11 && type <= 18) {
                showToast("记录员签字");
                return;
            } else if (type == 20) {
                showToast("执法人员(一)签名");
                return;
            }
        }
        if (TextUtils.isEmpty(sign4)) {
            if (type == 20) {
                showToast("执法人员(二)签名");
                return;
            }
        }
        String companySign = BASE64.imageToBase64(sign1);
        String officerSign = BASE64.imageToBase64(sign2);
        String reviewerSign = BASE64.imageToBase64(sign3);
        String reviewerSign2 = BASE64.imageToBase64(sign4);
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (type >= 11 && type <= 18) {
            map.put("companySign", companySign);
            map.put("officerSign", officerSign);
            map.put("recordSign", reviewerSign);
            map.put("reformTime", reformTime);
            map.put("resultReduction", resultReduction + "");
            map.put("inspectionOpinion", inspectionOpinion);
            map.put("violation", getText(etViolation));
            RxNetUtils.request(getApi(ApiService.class).submitSign1(url, id, map), new RequestObserver<JsonT<SuperviseBean.ResposeBean>>(this) {
                @Override
                protected void onSuccess(JsonT<SuperviseBean.ResposeBean> jsonT) {
                    if (jsonT.getData().getStatus() != 3) {
                        startActivity(new Intent(SuperviseSignActivity.this, MonitorActivity.class).putExtra("recordId", id).putExtra("type", type));
                    } else {
                        startActivity(new Intent(SuperviseSignActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", jsonT.getData()).putExtra("type", type));
                        finish();
                    }
                }

                @Override
                protected void onFail2(JsonT<SuperviseBean.ResposeBean> userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    showToast(userInfoJsonT.getMessage());
                }
            }, LoadingUtils.build(this));
        } else {
            if (type == 1 || type == 2 || type == 5 || type == 6 || type == 7 || type == 8 || type == 9 || type == 10 || type == 19) {
                map.put("companySign", companySign);
                map.put("officerSign", officerSign);
                map.put("reformTime", reformTime);
            } else if (type == 3 || type == 4) {
                map.put("fillerSign", companySign);
                map.put("ownerSign", officerSign);
                map.put("reviewerSign", reviewerSign);
            } else if (type == 20) {
                map.put("companySign", companySign);
                map.put("qualitySign", officerSign);
                map.put("officerSign1", reviewerSign);
                map.put("officerSign2", reviewerSign2);
            } else if (type == 21) {
                map.put("companySign", companySign);
                map.put("officerSign", officerSign);
                map.put("isRandomCheck", ig_isRandomCheck.getSelectValue());
                map.put("randomCheckType", ig_randomCheckType.getSelectValue());
                map.put("inspectionMethod", ig_inspectionMethod.getSelectValue());
                map.put("inspectionResult", ig_inspectionResult.getSelectValue());
                map.put("reductionOpinion", getText(et_reductionOpinion));
            }
            RxNetUtils.request(getApi(ApiService.class).submitSign(url, id, map), new RequestObserver<JsonT>(this) {
                @Override
                protected void onSuccess(JsonT jsonT) {
                    showResult(jsonT);
                }

                @Override
                protected void onFail2(JsonT userInfoJsonT) {
                    super.onFail2(userInfoJsonT);
                    showToast(userInfoJsonT.getMessage());
                }
            }, LoadingUtils.build(this));
        }

    }

    private void showResult(JsonT jsonT) {
        if (jsonT.isSuccess()) {

            startActivity(new Intent(SuperviseSignActivity.this, SuperviseResultActivity.class).putExtra("resposeBean", resposeBean).putExtra("type", type));
            finish();

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

    public void getDicts(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", type);
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {

                if (type.equals("tzsb_inspection_opinion")) {
                    inspection_opinionList.clear();
                    inspection_opinionList.addAll(jsonT.getData());
                } else if (type.equals("tzsb_result_reduction")) {
                    result_reductionList.clear();
                    result_reductionList.addAll(jsonT.getData());
                } else if (type.equals("reformTime")) {
                    reformTimeList.clear();
                    reformTimeList.addAll(jsonT.getData());
                } else if (type.equals("is_random_check")) {
                    is_random_check.clear();
                    is_random_check.addAll(jsonT.getData());
                } else if (type.equals("random_check_type")) {
                    random_check_type.clear();
                    random_check_type.addAll(jsonT.getData());
                } else if (type.equals("zdgyp_inspection_method")) {
                    zdgyp_inspection_method.clear();
                    zdgyp_inspection_method.addAll(jsonT.getData());
                } else if (type.equals("zdgyp_inspection_result")) {
                    zdgyp_inspection_result.clear();
                    zdgyp_inspection_result.addAll(jsonT.getData());
                }

            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow1(List<BusinessType> businessTypes, String type) {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < businessTypes.size(); i++) {
            list.add(businessTypes.get(i).getDictLabel());
            list1.add(businessTypes.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {

                if (type.equals("tzsb_inspection_opinion")) {
                    et_inspection_opinion.setText(msg);
                    inspectionOpinion = values[index];
                } else if (type.equals("tzsb_result_reduction")) {
                    et_result_reduction.setText(msg);
                    resultReduction = Integer.parseInt(values[index]);
                } else if (type.equals("reformTime")) {
                    et_reformTime.setText(msg);
                    reformTime = values[index];
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    void showSelectPopWindow1(List<BusinessType> businessTypes, ItemGroup itemGroup) {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < businessTypes.size(); i++) {
            list.add(businessTypes.get(i).getDictLabel());
            list1.add(businessTypes.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                if (itemGroup == ig_isRandomCheck) {
                    ig_randomCheckType.setVisibility(values[index].equals("1") ? View.VISIBLE : View.GONE);
                }
                itemGroup.setChooseContent(msg, values[index]);
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }


}
