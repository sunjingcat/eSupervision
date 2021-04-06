package com.zz.supervision.business.company;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.previewlibrary.ZoomMediaLoader;
import com.troila.customealert.CustomDialog;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.ImageBack;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.net.RxNetUtils.getApi;

public class CompanyInfoActivity extends MyBaseActivity {
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
    String fieldTime;
    String validDate;
    String businessType = "";
    String id;
    String companyType;
    CompanyBean companyBean;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bt_delete)
    Button bt_delete;
    @BindView(R.id.bg)
    LinearLayout bg;
    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_info;
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
        companyType = getIntent().getStringExtra("companyType");
        if (!TextUtils.isEmpty(id)) {
            getData(id);
        }
        if (companyType.equals("2")){
            toolbar_subtitle.setVisibility(View.GONE);
            bt_delete.setVisibility(View.GONE);
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


    public void showCompanyInfo(CompanyBean data) {
        if (data == null) return;
        companyBean = data;
        mlist.clear();
        etOperatorName.setText(data.getOperatorName() + "");
        if (companyType.equals("2")) {
            mlist.add(new DetailBean("经营者全称", data.getOperatorFullName() + ""));
            mlist.add(new DetailBean("社会信用代码", data.getSocialCreditCode() + ""));
            mlist.add(new DetailBean("许可证编号", data.getLicenseNumber() + ""));
            mlist.add(new DetailBean("法定代表人", data.getLegalRepresentative() + ""));
            mlist.add(new DetailBean("住所", data.getAddress() + ""));
            mlist.add(new DetailBean("联系人", data.getContact() + ""));
            mlist.add(new DetailBean("联系方式", data.getContactInformation() + ""));
            ll_user.setVisibility(View.VISIBLE);
            mlist.add(new DetailBean("登录账号", data.getLoginAccount() + ""));
            mlist.add(new DetailBean("冷库类型", data.getColdstorageType1Text() + ""));
            mlist.add(new DetailBean("是否含第三方冷库", data.getColdstorageType2Text() + ""));
        } else if (companyType.equals("3")||companyType.equals("4")) {
            mlist.add(new DetailBean("经营者名称", data.getOperatorName() + ""));
            mlist.add(new DetailBean("社会信用代码", data.getSocialCreditCode() + ""));
            mlist.add(new DetailBean("许可证编号", data.getLicenseNumber() + ""));
            mlist.add(new DetailBean("法定代表人", data.getLegalRepresentative() + ""));
            mlist.add(new DetailBean("住所", data.getAddress() + ""));
            if (companyType.equals("3")){
                mlist.add(new DetailBean("企业负责人", data.getContact() + ""));
                mlist.add(new DetailBean("质量负责人", data.getQualityContact() + ""));
                mlist.add(new DetailBean("仓库地址", data.getWarehouseAddress() + ""));
                mlist.add(new DetailBean("经营范围", data.getBusinessScopeText() + ""));
            }else {
                mlist.add(new DetailBean("联系人", data.getContact() + ""));
            }

            mlist.add(new DetailBean("联系方式", data.getContactInformation() + ""));
            ll_user.setVisibility(View.GONE);
            mlist.add(new DetailBean("经营场所", data.getBusinessPlace() + ""));
            mlist.add(new DetailBean("企业类型", data.getCompanyTypeText() + ""));
            mlist.add(new DetailBean("具体类型", data.getSpecificTypeText() + ""));
            mlist.add(new DetailBean("有效期至", data.getValidDate() + ""));
            mlist.add(new DetailBean("签发时间", data.getFieldTime() + ""));
        }else if (companyType.equals("6")) {
            mlist.add(new DetailBean("单位名称", data.getOperatorFullName() + ""));
            mlist.add(new DetailBean("单位地址", data.getSocialCreditCode() + ""));
            mlist.add(new DetailBean("社会信用代码", data.getLicenseNumber() + ""));
            mlist.add(new DetailBean("法定代表人", data.getLegalRepresentative() + ""));
            mlist.add(new DetailBean("安全管理联系人", data.getAddress() + ""));
            mlist.add(new DetailBean("安全管理联系电话", data.getContact() + ""));
            ll_user.setVisibility(View.GONE);

        } else {
            mlist.add(new DetailBean("社会信用代码", data.getSocialCreditCode() + ""));
            mlist.add(new DetailBean("许可证编号", data.getLicenseNumber() + ""));
            mlist.add(new DetailBean("法定代表人", data.getLegalRepresentative() + ""));
            mlist.add(new DetailBean("住所", data.getAddress() + ""));
            mlist.add(new DetailBean("联系人", data.getContact() + ""));
            mlist.add(new DetailBean("联系方式", data.getContactInformation() + ""));
            ll_user.setVisibility(View.GONE);
            mlist.add(new DetailBean("经营场所", data.getBusinessPlace() + ""));
            mlist.add(new DetailBean("企业类型", data.getCompanyTypeText() + ""));
            mlist.add(new DetailBean("主体业态", data.getBusinessTypeText() + ""));
            mlist.add(new DetailBean("经营项目", data.getBusinessProjectText() + ""));
            mlist.add(new DetailBean("有效期至", data.getValidDate() + ""));
            mlist.add(new DetailBean("签发时间", data.getFieldTime() + ""));
        }


        businessType = data.getBusinessType();
        validDate = data.getValidDate();
        fieldTime = data.getFieldTime();
        getImage(data.getId());

        infoAdapter.notifyDataSetChanged();
    }

    void getData(String id) {
            String url = "companyInfo";
            if (companyType.equals("2")) {
                url = "coldstorage";
            }else if (companyType.equals("3")) {
                url = "ypCompanyInfo";
            }else if (companyType.equals("4")) {
                url = "ylqxCompanyInfo";
            }
            RxNetUtils.request(getApi(ApiService.class).getCompanyInfo(url,id), new RequestObserver<JsonT<CompanyBean>>(this) {
                @Override
                protected void onSuccess(JsonT<CompanyBean> jsonT) {
                    companyBean = jsonT.getData();
                    showCompanyInfo(jsonT.getData());
                }

                @Override
                protected void onFail2(JsonT<CompanyBean> stringJsonT) {
                    super.onFail2(stringJsonT);
                }
            }, LoadingUtils.build(this));
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok,  R.id.bt_password, R.id.et_location, R.id.bt_delete, R.id.et_nav, R.id.et_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddCompanyActivity.class).putExtra("id", companyBean.getId())
                                .putExtra("companyType", companyBean.getCompanyType()+"")
                                .putExtra("companyTypeText", companyBean.getCompanyTypeText())
                        , 1001);
                break;
            case R.id.bt_password:
                startActivityForResult(new Intent(this, PasswordActivity.class).putExtra("id", companyBean.getId()).putExtra("page", "company"), 1001);
                break;
            case R.id.bt_ok:
                if (companyBean == null) return;
                startActivity(new Intent(this, XCHZFActivity.class).putExtra("company", companyBean));
                break;
            case R.id.et_record:
                if (companyBean == null) return;
                if (companyType.equals("2")) {
                    startActivity(new Intent(this, ColdCheckListActivity.class).putExtra("id", companyBean.getId()));
                } else if (companyType.equals("3")||companyType.equals("4")) {
                    startActivity(new Intent(this, YaoCheckListActivity.class).putExtra("id", companyBean.getId()));
                } else {
                    startActivity(new Intent(this, CheckListActivity.class).putExtra("id", companyBean.getId()));
                }

                break;
            case R.id.et_location:
                if (companyBean == null) return;
                if (companyBean.getLongitude() == 0.0) return;
                startActivity(new Intent(this, ShowLocationActivity.class).putExtra("location_lat", companyBean.getLatitude()).putExtra("location_lng", companyBean.getLongitude()));
                break;
            case R.id.et_nav:
                if (companyBean == null) return;
                if (companyBean.getLongitude() == 0.0) return;
                if (!NavUtils.isInstalled()) {
                    com.zz.lib.core.http.utils.ToastUtils.showToast("未安装百度地图");
                    return;
                } else {
                    if (companyBean.getLatitude() > 0.0 && companyBean.getLongitude() > 0.0) {
                        NavUtils.invokeNavi(this, null, "中智.智慧监督", companyBean.getLatitude() + "," + companyBean.getLongitude());
                    } else {
                        com.zz.lib.core.http.utils.ToastUtils.showToast("坐标错误");
                    }
                }
                break;
            case R.id.bt_delete:
                if (companyBean == null) return;
                CustomDialog.Builder builder = new com.troila.customealert.CustomDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定删除该企业及相关执法记录？")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteDate(companyBean.getId());
                            }
                        });
                customDialog = builder.create();
                customDialog.show();
                break;
        }
    }

    public void getImage(String id) {
        String url = "company";
        if (companyType.equals("2")) {
            url = "coldstorage";
        }else if (companyType.equals("3")) {
            url = "ypCompany";
        }else if (companyType.equals("4")) {
            url = "ylqxCompany";
        }
        RxNetUtils.request(getApi(ApiService.class).getImageBase64(url, id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
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
