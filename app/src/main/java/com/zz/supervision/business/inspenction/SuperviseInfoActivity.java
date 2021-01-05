package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.bean.SuperviseInfoBean;
import com.zz.supervision.business.inspenction.adapter.SuperviseAdapter;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.FileUtils;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 监督检查:食品销售日常
 */
public class SuperviseInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_inspector)
    TextView tvInspector;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.rv)
    RecyclerView rv;
    SuperviseAdapter adapter;
    String id;
    String url = "spxsInspectionRecord";
    int type = 0;
    int status = 0;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbaSubtitle;


    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SuperviseAdapter(new SuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (type == 10) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof SuperviseBean && ((SuperviseBean) children).getId().equals(((SuperviseBean) node).getId())) {
                            adapter.expandOrCollapse(i);
//                            ((SuperviseBean) node).setExpanded(((SuperviseBean) children).isExpanded());
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, 1);
        rv.setAdapter(adapter);
        type = getIntent().getIntExtra("type", 0);
        status = getIntent().getIntExtra("status", 0);
        btOk.setText("打印");
        btOk.setVisibility(status == 3 ? View.VISIBLE : View.GONE);
        if (type == 1) {
            url = "spxsInspectionRecord";
        } else if (type == 2) {
            url = "cyfwInspectionRecord";
        } else if (type == 5) {
            url = "llglInspectionRecord";
            btOk.setVisibility(View.GONE);
        }else if (type == 6||type == 7) {
            url = "ypInspectionRecord";
        }else if (type == 8||type == 9||type == 10) {
            url = "ylqxInspectionRecord";
        }
        initData();


        toolbarTitle.setText("评分详情");
        toolbaSubtitle.setVisibility(View.GONE);
        tvReason.setVisibility(View.GONE);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void initData() {
        String company = getIntent().getStringExtra("company");
        String lawEnforcerText = getIntent().getStringExtra("lawEnforcer");
        String type = getIntent().getStringExtra("typeText");
        id = getIntent().getStringExtra("id");
        tvCompany.setText(company + "");
        tvInspector.setText("检查员：" + lawEnforcerText);
        tvType.setText("检查类型：" + type + "");
        String time = getIntent().getStringExtra("inspectionTime");
        String reason = getIntent().getStringExtra("reason");
        tvTime.setText("检查时间：" + time + "");
        tvReason.setText("检查事由：" + reason + "");
        if (TextUtils.isEmpty(reason)) {
            tvReason.setVisibility(View.GONE);
        }
        getData();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    public void showSuperviseList(List<SuperviseBean> data) {
        adapter.setList(data);
        adapter.notifyDataSetChanged();

    }

    void getData() {
        RxNetUtils.request(getApi(ApiService.class).getSuperviseInfo(url, id), new RequestObserver<JsonT<SuperviseInfoBean>>() {
            @Override
            protected void onSuccess(JsonT<SuperviseInfoBean> jsonT) {
                if (jsonT == null) return;
                showSuperviseList(jsonT.getData().getData());
                tvRemark.setVisibility(View.VISIBLE);
                tvRemark.setText(jsonT.getData().getRemark() + "");
            }

            @Override
            protected void onFail2(JsonT<SuperviseInfoBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        if (TextUtils.isEmpty(id)) return;
        startActivity(new Intent(this, ShowDocActivity.class).putExtra("id", id).putExtra("tinspectSheetType", 2).putExtra("tinspectType", type));
    }
}
