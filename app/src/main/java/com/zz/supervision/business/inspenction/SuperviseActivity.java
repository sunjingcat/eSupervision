package com.zz.supervision.business.inspenction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
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
    @BindView(R.id.toolbar_subtitle)
    TextView toolbaSubtitle;
    @BindView(R.id.rv)
    RecyclerView rv;

    SuperviseAdapter adapter;
    List<BaseNode> mlist = new ArrayList<>();
    String id;
    String url = "spxsInspectionRecord";
    int type = 0;
    @BindView(R.id.bt_ok)
    Button btOk;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new SuperviseAdapter(new SuperviseAdapter.OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (node instanceof SuperviseBean) {
                    if (type == 10) {
                        for (int i = 0; i < adapter.getData().size(); i++) {
                            BaseNode children = adapter.getData().get(i);
                            if (children instanceof SuperviseBean && ((SuperviseBean) children).getId().equals(((SuperviseBean) node).getId())) {
                                adapter.expandOrCollapse(i);
                                break;
                            }
                        }
                    } else {
                        for (SuperviseBean.Children children : ((SuperviseBean) node).getChildrenList()) {
                            if (((SuperviseBean) node).isCheck()) {
                                children.setIsSatisfy(1);
                            } else {
                                children.setIsSatisfy(0);
                            }
                        }
                    }
                } else if (node instanceof SuperviseBean.Children) {
                    if (type == ((SuperviseBean.Children) node).getIsSatisfy()) {
                        ((SuperviseBean.Children) node).setIsSatisfy(0);
                    } else {
                        ((SuperviseBean.Children) node).setIsSatisfy(type);
                    }
                    if (type == 1) {
                        for (BaseNode superviseBean : adapter.getData()) {
                            if (superviseBean instanceof SuperviseBean && ((SuperviseBean) superviseBean).getId().equals(((SuperviseBean.Children) node).getItemPid())) {
                                boolean isAllYes = true;
                                for (SuperviseBean.Children children : ((SuperviseBean) superviseBean).getChildrenList()) {
                                    if (children.getIsSatisfy()!=1){
                                        isAllYes = false;
                                    }
                                }
                                ((SuperviseBean) superviseBean).setCheck(isAllYes);
                                break;
                            }
                        }
                    }else if (type == 2){
                        for (BaseNode superviseBean : adapter.getData()) {
                            if (superviseBean instanceof SuperviseBean) {
                                ((SuperviseBean) superviseBean).setCheck(false);
                            }
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }, 0);

        rv.setAdapter(adapter);
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
        } else {
            url = "cyfwInspectionRecord";
        }

        toolbaSubtitle.setVisibility(View.VISIBLE);
        initData();
        mPresenter.getData(id, url);
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
    }

    @Override
    public SupervisePresenter initPresenter() {
        return new SupervisePresenter(this);
    }


    @Override
    public void showFoodSuperviseList(List<SuperviseBean> data) {
        adapter.setList(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showReResult(SuperviseBean.ResposeConfirmBean bean) {
        startActivityForResult(new Intent(this, InfoActivity.class)
                        .putExtra("supervise_respose", bean)
                        .putExtra("type", type)
                , 1001);
    }

    @Override
    public void showResult(SuperviseBean.ResposeBean resposeBean) {
        startActivity(new Intent(this, SuperviseSignActivity.class).putExtra("resposeBean", resposeBean).putExtra("type", type));
        finish();
    }

    @Override
    public void showDelete() {
        finish();
    }

    ArrayList<SuperviseBean.PostBean> postBeans = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                mPresenter.submitData(url, id, postBeans);
            }
        }
    }

    private CustomDialog customDialog;

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                if (TextUtils.isEmpty(url)) return;
                if (TextUtils.isEmpty(id)) return;

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
                                mPresenter.delete(url, id);
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
            case R.id.bt_ok:
                mlist = adapter.getData();
                postBeans = new ArrayList<>();
                for (BaseNode node : mlist) {
                    if (node instanceof SuperviseBean) {
                        for (SuperviseBean.Children children : ((SuperviseBean) node).getChildrenList()) {
                            if (children.getIsSatisfy() != 0) {
                                postBeans.add(new SuperviseBean.PostBean(children.getId(), children.getIsSatisfy() == 1 ? 1 : 0));
                            }
                        }
                    }
                }
                mPresenter.submitReData(url, id, postBeans);
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

}
