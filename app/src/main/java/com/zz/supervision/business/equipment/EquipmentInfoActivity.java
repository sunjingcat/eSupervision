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
import com.zz.supervision.business.company.AddCompanyActivity;
import com.zz.supervision.business.company.ShowLocationActivity;
import com.zz.supervision.business.company.adapter.ComInfoAdapter;
import com.zz.supervision.business.company.adapter.ImageItemAdapter;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.business.mine.PasswordActivity;
import com.zz.supervision.business.record.CheckListActivity;
import com.zz.supervision.business.record.ColdCheckListActivity;
import com.zz.supervision.business.record.YaoCheckListActivity;
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
 * 设备详情
 */
public class EquipmentInfoActivity extends MyBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    TextView etOperatorName;


    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;

    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<DetailBean> mlist = new ArrayList<>();
    ImageItemAdapter adapter;
    ComInfoAdapter infoAdapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    @BindView(R.id.rv)
    RecyclerView rv;
    String id;
    EquipmentBean equipmentBean;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.bg)
    LinearLayout bg;
    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment_info;
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


    public void showCompanyInfo(EquipmentBean data) {
        if (data == null) return;
        equipmentBean = data;
        mlist.clear();
        etOperatorName.setText(data.getDeviceName() + "");
        mlist.add(new DetailBean("设备代码", data.getDeviceCode() + ""));
        mlist.add(new DetailBean("设备型号", data.getDeviceModel() + ""));
        mlist.add(new DetailBean("设备类型", data.getDeviceType1Text() + "" + data.getDeviceType2Text() + data.getDeviceType3Text()));
        mlist.add(new DetailBean("注册状态", data.getRegistStatusText() + ""));
        mlist.add(new DetailBean("注册登记机构", data.getRegistOrganizationName() + ""));
        mlist.add(new DetailBean("注册时间", data.getRegistTime() + ""));
        mlist.add(new DetailBean("注册登记人员", data.getRegistRecorder() + ""));
        mlist.add(new DetailBean("注册登记证编号", data.getRegistNumber() + ""));
        mlist.add(new DetailBean("注册代码", data.getRegistCode() + ""));
        mlist.add(new DetailBean("使用状态", data.getUsageStatusText() + ""));
        mlist.add(new DetailBean("使用状态变更日期", data.getUsageUpdateDate() + ""));
        mlist.add(new DetailBean("制造单位", data.getManufacturerName() + ""));
        mlist.add(new DetailBean("产品编号", data.getProjectNumber() + ""));
        mlist.add(new DetailBean("制造日期", data.getManufacturerDate() + ""));
        mlist.add(new DetailBean("安装单位", data.getInstallationCompany() + ""));
        mlist.add(new DetailBean("竣工日期", data.getCompletionDate() + ""));

        if (data.getDeviceType1().equals("3")) {
            mlist.add(new DetailBean("管道总长", data.getTotalLength() + ""));

        }
        if (data.getDeviceType1().equals("8")) {
            mlist.add(new DetailBean("车牌号码", data.getLicensePlate() + ""));

        }

        getImage(data.getId());

        infoAdapter.notifyDataSetChanged();
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getEquipmentInfo(id), new RequestObserver<JsonT<EquipmentBean>>(this) {
            @Override
            protected void onSuccess(JsonT<EquipmentBean> jsonT) {
                equipmentBean = jsonT.getData();
                showCompanyInfo(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<EquipmentBean> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, LoadingUtils.build(this));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.et_location, R.id.bt_delete, R.id.et_nav, R.id.et_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddEquipmentActivity.class).putExtra("id", equipmentBean.getId())
                                .putExtra("companyId", equipmentBean.getCompanyId())
                        , 1001);
                break;
            case R.id.bt_ok:
                if (equipmentBean == null) return;
                startActivity(new Intent(this, InspectActivity.class).putExtra("company", equipmentBean));
                break;
            case R.id.et_record:
                if (equipmentBean == null) return;

                startActivity(new Intent(this, CheckListActivity.class).putExtra("id", equipmentBean.getId()));


                break;
            case R.id.et_location:
                if (equipmentBean == null) return;
                if (equipmentBean.getLongitude() == 0.0) return;
                startActivity(new Intent(this, ShowLocationActivity.class).putExtra("location_lat", equipmentBean.getLatitude()).putExtra("location_lng", equipmentBean.getLongitude()));
                break;
            case R.id.et_nav:
                if (equipmentBean == null) return;
                if (equipmentBean.getLongitude() == 0.0) return;
                if (!NavUtils.isInstalled()) {
                    com.zz.lib.core.http.utils.ToastUtils.showToast("未安装百度地图");
                    return;
                } else {
                    if (equipmentBean.getLatitude() > 0.0 && equipmentBean.getLongitude() > 0.0) {
                        NavUtils.invokeNavi(this, null, "中智.智慧监督", equipmentBean.getLatitude() + "," + equipmentBean.getLongitude());
                    } else {
                        com.zz.lib.core.http.utils.ToastUtils.showToast("坐标错误");
                    }
                }
                break;
            case R.id.bt_delete:
                if (equipmentBean == null) return;
                CustomDialog.Builder builder = new CustomDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除该设备？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(equipmentBean.getId());
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
        RxNetUtils.request(getApi(ApiService.class).removeCompanyInfo(id), new RequestObserver<JsonT>() {
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
