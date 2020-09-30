package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.inspenction.adapter.SuperviseAdapter;
import com.zz.supervision.business.inspenction.presenter.SupervisePresenter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 监督检查:食品销售日常
 */
public class SuperviseActivity extends MyBaseActivity<Contract.IsetSupervisePresenter> implements Contract.IGetSuperviseView {
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
    @BindView(R.id.ll_null)
    LinearLayout llNull;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    SuperviseAdapter adapter;
    List<BaseNode> mlist = new ArrayList<>();
    String id;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new SuperviseAdapter(new SuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (node instanceof SuperviseBean) {
                    for (SuperviseBean.Children children : ((SuperviseBean) node).getChildrenList()) {
                        children.setCheck(((SuperviseBean) node).isCheck());
                    }
                }
                if (node instanceof SuperviseBean.RootFooterNode){
                    for (int i =0;i<adapter.getData().size();i++) {
                        BaseNode children  = adapter.getData().get(i);
                        if (((SuperviseBean)children).getId()==((SuperviseBean.RootFooterNode) node).getId()){
                            adapter.expandOrCollapse(i);
                            ((SuperviseBean.RootFooterNode) node).setExpanded(((SuperviseBean) children).isExpanded());
                            break;
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        rv.setAdapter(adapter);
        mPresenter.getData("spxsInspectionRecord/getItems");
        initData();
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    void initData() {
       CompanyBean company = getIntent().getParcelableExtra("company");
       String lawEnforcerText = getIntent().getStringExtra("lawEnforcerText");
       String type = getIntent().getStringExtra("type");
//        id = getIntent().getStringExtra("id");
//       tvCompany.setText(company.getOperatorName());
//       tvInspector.setText("检查员："+lawEnforcerText);
//       tvType.setText("检查类型："+type+"");
    }

    @Override
    public SupervisePresenter initPresenter() {
        return new SupervisePresenter(this);
    }


    @Override
    public void showFoodSuperviseList(List<SuperviseBean> data) {
        adapter.setList(data);
        adapter.notifyDataSetChanged();
        if (data.size() == 0) {
            llNull.setVisibility(View.VISIBLE);
        } else {
            llNull.setVisibility(View.GONE);
        }
    }

    @Override
    public void showReResult(SuperviseBean.ResposeConfirmBean bean) {
        startActivityForResult(new Intent(this,InfoActivity.class)
                        .putExtra("supervise_respose",bean)
                ,1001);
    }

    @Override
    public void showResult(SuperviseBean.ResposeBean resposeBean) {
        startActivity(new Intent(this,SuperviseSignActivity.class).putExtra("resposeBean",resposeBean));

    }
    ArrayList<SuperviseBean.PostBean> postBeans = new ArrayList<>();
    @OnClick(R.id.bt_ok)
    public void onViewClicked() {
        mlist = adapter.getData();
        postBeans = new ArrayList<>();
        for (BaseNode node : mlist) {
            if (node instanceof SuperviseBean.Children) {
                if (((SuperviseBean.Children) node).isCheck()) {
                    postBeans.add(new SuperviseBean.PostBean(((SuperviseBean.Children) node).getId(), ((SuperviseBean.Children) node).getIsSatisfy()));
                }
            }
        }
        mPresenter.submitReData(id, postBeans);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1001){
            if (resultCode==RESULT_OK){
                mPresenter.submitData(id, postBeans);
            }
        }
    }
}
