package com.zz.supervision.business.equipment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PipePartBean;
import com.zz.supervision.business.company.ShowLocationActivity;
import com.zz.supervision.business.company.adapter.ComInfoAdapter;
import com.zz.supervision.business.company.adapter.ImageItemAdapter;
import com.zz.supervision.business.record.CheckListActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.ImageLoader;
import com.zz.supervision.utils.NavUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 * 管道单元详情
 */
public class PipeInfoPartActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    TextView etOperatorName;


    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;

    ArrayList<String> images = new ArrayList<>();
    ArrayList<DetailBean> mlist = new ArrayList<>();
    ImageItemAdapter adapter;
    ComInfoAdapter infoAdapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
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
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        infoAdapter = new ComInfoAdapter(R.layout.item_com_info, mlist);
        itemRvImages.setAdapter(adapter);
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

        getImage(data.getId());

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

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_delete, R.id.et_nav, R.id.et_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddPipePartActivity.class).putExtra("id", pipePartBean.getId())
                                .putExtra("deviceId", pipePartBean.getDeviceId())
                        , 1001);
                break;
            case R.id.bt_ok:
                startActivity(new Intent(this, InspectActivity.class).putExtra("id", pipePartBean.getId()).putExtra("deviceId", pipePartBean.getDeviceId()));
                break;
            case R.id.et_record:
                if (pipePartBean == null) return;
                startActivity(new Intent(this, CheckListActivity.class).putExtra("id", pipePartBean.getId()));
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

    public void getImage(String id) {
        RxNetUtils.request(getApi(ApiService.class).getImageBase64("tzsbDeviceInfo", id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<ImageBack>> data) {
                if (data.isSuccess()) {
                    showImage(data.getData());
                } else {

                }
            }

            @Override
            protected void onFail2(JsonT<List<ImageBack>> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    List<ImageBack> imageBacks = new ArrayList<>();

    public void showImage(List<ImageBack> list) {
        if (list == null) return;

        List<String> showList = new ArrayList<>();
        for (ImageBack imageBack : list) {
            String bitmapName = "company_" + imageBack.getId() + ".png";
            String path = getCacheDir() + "/zhongzhi/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                showList.add(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                showList.add(s);
            }

        }
        images.clear();
        images.addAll(showList);

        adapter.notifyDataSetChanged();
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
