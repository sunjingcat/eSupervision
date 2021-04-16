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
import com.zz.supervision.business.inspenction.adapter.OnProviderOnClick;
import com.zz.supervision.business.inspenction.adapter.SuperviseAdapter;
import com.zz.supervision.business.inspenction.presenter.SupervisePresenter;
import com.zz.supervision.utils.LogUtils;

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
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_reason)
    TextView tvReason;
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
    private int num;

    @Override
    protected int getContentView() {
        return R.layout.activity_select_inspections;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            url = "spxsInspectionRecord";
        } else if (type == 2) {
            url = "cyfwInspectionRecord";
        } else if (type == 5) {
            url = "llglInspectionRecord";
        } else if (type == 6 || type == 7) {
            url = "ypInspectionRecord";
        } else if (type == 8 || type == 9 || type == 10) {
            url = "ylqxInspectionRecord";
        } else if (type == 11 || type == 12 || type == 13 || type == 14 || type == 15 || type == 16 || type == 17 || type == 18) {
            url = "tzsbInspectionRecord";
        }
        final int[] num = {0};
        adapter = new SuperviseAdapter(new OnProviderOnClick() {
            @Override
            public void onItemOnclick(BaseNode node, int type) {
                if (type == 10) { //展开
                    for (int i = 0; i < adapter.getData().size(); i++) {
                        BaseNode children = adapter.getData().get(i);
                        if (children instanceof SuperviseBean && ((SuperviseBean) children).getId()==(((SuperviseBean) node).getId())) {
                            adapter.expandOrCollapse(i);
                            break;
                        }
                    }
                } else {
                    if (((SuperviseBean) node).getChildType() == 0) {
                        if (type == ((SuperviseBean) node).getIsSatisfy()) {
                            ((SuperviseBean) node).setIsSatisfy(0);
                        } else {
                            ((SuperviseBean) node).setIsSatisfy(type);
                        }
                    } else {
                        setSelect((SuperviseBean) node, ((SuperviseBean) node).isCheck());
                    }
                    reverseCheck((SuperviseBean) node,type);
                }
                adapter.notifyDataSetChanged();
            }
        }, 0,url.equals("tzsbInspectionRecord") ?1:0);

        rv.setAdapter(adapter);



        toolbaSubtitle.setVisibility(View.VISIBLE);
        initData();
        mPresenter.getData(id, url);
    }

    private void setSelect(SuperviseBean children, boolean check) {
        if (children.getChildType() == 1) {
            children.setCheck(check);
            children.setIsSatisfy(check ? 1 : 0);
            for (SuperviseBean grand : children.getChildrenList()) {
                setSelect(grand, check);
            }
        } else {
            children.setIsSatisfy(check ? 1 : 0);
        }
    }


    private void reverseCheck(SuperviseBean node, int type) {

        if (node.getChildType() == 0) {
            for (BaseNode superviseBean : adapter.getData()) {
                if (((SuperviseBean) superviseBean).getId()==(((SuperviseBean) node).getItemPid())) {
                    if (type == 1) {
                        boolean isAllYes = true;
                        for (SuperviseBean children : ((SuperviseBean) superviseBean).getChildrenList()) {
                            if (children.getIsSatisfy() != 1) {
                                isAllYes = false;
                            }
                        }
                        ((SuperviseBean) superviseBean).setCheck(isAllYes);
                    } else {
                        ((SuperviseBean) superviseBean).setCheck(false);
                    }
                    reverseCheck((SuperviseBean) superviseBean,type);
                    break;
                }

            }
        }


    }


    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void initData() {
        String company = getIntent().getStringExtra("company");
        String lawEnforcerText = getIntent().getStringExtra("lawEnforcer");
        String type = getIntent().getStringExtra("typeText");
        String time = getIntent().getStringExtra("inspectionTime");
        String reason = getIntent().getStringExtra("reason");
        id = getIntent().getStringExtra("id");
        tvCompany.setText(company + "");
        tvInspector.setText("检查员：" + lawEnforcerText);
        tvType.setText("检查类型：" + type + "");
        tvTime.setText("检查时间：" + time + "");
        tvReason.setText("检查事由：" + reason + "");
        if (TextUtils.isEmpty(reason)) {
            tvReason.setVisibility(View.GONE);
        }
    }

    @Override
    public SupervisePresenter initPresenter() {
        return new SupervisePresenter(this);
    }


    @Override
    public void showFoodSuperviseList(List<SuperviseBean> data) {
        mlist.clear();
        mlist.addAll(data);
        adapter.setList(mlist);
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
//                mlist = mlist;
                postBeans = new ArrayList<>();
                for (BaseNode node : mlist) {
                    getPostBeans((SuperviseBean) node);
                }
                mPresenter.submitReData(url, id, postBeans);
                break;
        }
    }

    public void getPostBeans(SuperviseBean children) {
        LogUtils.v("---------",children.getItemDegree()+"-----"+children.getItemDegree()+"-----"+children.getId());
        if (children.getChildType() == 0) {
            if (children.getIsSatisfy() != 0) {
                postBeans.add(new SuperviseBean.PostBean(children.getId()+"", getSatisfyValue(children.getIsSatisfy())));
            }
        } else {
            for (SuperviseBean grand : children.getChildrenList()) {
                getPostBeans(grand);
            }
        }
    }

    int getSatisfyValue(int num) {
        switch (num) {
            case 1:
                return 1;
            case 2:
                return 0;
            case 3:
                return 3;
        }
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }

}
