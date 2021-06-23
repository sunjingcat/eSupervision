package com.zz.supervision.business.equipment;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.PipePartBean;
import com.zz.supervision.business.company.adapter.ComInfoAdapter;
import com.zz.supervision.business.record.CheckListActivity;
import com.zz.supervision.business.record.TzsbCheckListActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.ImageLoader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 管道单元详情
 */
public class PipePartInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    TextView etOperatorName;

    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;

    ArrayList<DetailBean> mlist = new ArrayList<>();

    ComInfoAdapter infoAdapter;

    @BindView(R.id.rv)
    RecyclerView rv;
    String id;
    PipePartBean pipePartBean;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.bg)
    LinearLayout bg;
    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_pipe_part_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        rv.setLayoutManager(new LinearLayoutManager(this));

        infoAdapter = new ComInfoAdapter(R.layout.item_com_info, mlist);

        rv.setAdapter(infoAdapter);
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }


    public void showCompanyInfo(PipePartBean data) {
        if (data == null) return;
        pipePartBean = data;
        mlist.clear();
        etOperatorName.setText(data.getPipeName() + "");
        mlist.add(new DetailBean("制造单位", data.getManufacturerName() + ""));
        mlist.add(new DetailBean("管道编号", data.getPipeNumber() + ""));
        mlist.add(new DetailBean("制造日期", data.getManufacturerDate() + ""));
        mlist.add(new DetailBean("安装单位", data.getInstallationCompany() + ""));
        mlist.add(new DetailBean("竣工日期", data.getCompletionDate() + ""));
        mlist.add(new DetailBean("管道长度", data.getTotalLength() + ""));
        mlist.add(new DetailBean("管道级别", data.getPipeRank() + ""));
        mlist.add(new DetailBean("管道规格", data.getPipeStandard() + ""));
        mlist.add(new DetailBean("管道材质", data.getPipeTexture() + ""));
        mlist.add(new DetailBean("工作介质", data.getWorkingMedium() + ""));
        infoAdapter.notifyDataSetChanged();
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getPressurepipePartInfo(id), new RequestObserver<JsonT<PipePartBean>>(this) {
            @Override
            protected void onSuccess(JsonT<PipePartBean> jsonT) {
                pipePartBean = jsonT.getData();
                showCompanyInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<PipePartBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_delete, R.id.et_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddPipePartActivity.class).putExtra("id", pipePartBean.getId())
                                .putExtra("deviceId", pipePartBean.getDeviceId())
                        , 1001);
                break;
            case R.id.bt_ok:
                startActivity(new Intent(this, CheckActivity.class).putExtra("deviceType", "3").putExtra("deviceId", pipePartBean.getId()));
                break;
            case R.id.et_record:
                if (pipePartBean == null) return;
                startActivity(new Intent(this, TzsbCheckListActivity.class).putExtra("id", pipePartBean.getId()).putExtra("url","tzsbInspectionRecord"));
                break;


            case R.id.bt_delete:
                if (pipePartBean == null) return;
                CustomDialog.Builder builder = new CustomDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除该压力管道单元？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(pipePartBean.getId());
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

    void deleteDate(String id) {
        RxNetUtils.request(getApi(ApiService.class).removePressurepipePartInfo(id), new RequestObserver<JsonT>() {
            @Override
            protected void onSuccess(JsonT jsonT) {
                showToast("删除成功");
                finish();
            }

            @Override
            protected void onFail2(JsonT stringJsonT) {
                super.onFail2(stringJsonT);
                ToastUtils.showShort(stringJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (!TextUtils.isEmpty(id)) {
                getData(id);
            }
        }
    }
}
