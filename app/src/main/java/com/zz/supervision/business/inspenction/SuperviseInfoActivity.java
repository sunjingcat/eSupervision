package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
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

import java.util.List;

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
    @BindView(R.id.tv_num)
    TextView tvNum;

    @BindView(R.id.rv)
    RecyclerView rv;
    SuperviseAdapter adapter;
    String id;
    String url = "spxsInspectionRecord";
    int type = 0;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

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
                if (node instanceof SuperviseBean.RootFooterNode) {
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof SuperviseBean && ((SuperviseBean) children).getId().equals(((SuperviseBean.RootFooterNode) node).getId())) {
                            adapter.expandOrCollapse(i);
                            ((SuperviseBean.RootFooterNode) node).setExpanded(((SuperviseBean) children).isExpanded());
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, 1);
        rv.setAdapter(adapter);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
        } else {
            url = "cyfwInspectionRecord";
        }
        initData();
        btOk.setText("打印");
        toolbarTitle.setText("评分详情");
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
        RxNetUtils.request(getApi(ApiService.class).getSuperviseInfo(url, id), new RequestObserver<JsonT<List<SuperviseInfoBean>>>() {
            @Override
            protected void onSuccess(JsonT<List<SuperviseInfoBean>> jsonT) {
                showSuperviseList(jsonT.getData().get(0).getData());
            }

            @Override
            protected void onFail2(JsonT<List<SuperviseInfoBean>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        if (TextUtils.isEmpty(id))return;
        startActivity(new Intent(this, ShowDocActivity.class).putExtra("id",id).putExtra("tinspectSheetType",2).putExtra("tinspectType",type));
    }
}
