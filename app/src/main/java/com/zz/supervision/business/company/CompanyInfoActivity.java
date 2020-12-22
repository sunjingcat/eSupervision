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
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.business.company.adapter.ImageItemAdapter;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.business.mine.PasswordActivity;
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

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
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
    @BindView(R.id.et_socialCreditCode)
    TextView etSocialCreditCode;
    @BindView(R.id.et_licenseNumber)
    TextView etLicenseNumber;
    @BindView(R.id.et_legalRepresentative)
    TextView etLegalRepresentative;
    @BindView(R.id.et_address)
    TextView etAddress;
    @BindView(R.id.et_businessPlace)
    TextView etBusinessPlace;
    @BindView(R.id.et_businessType)
    TextView etBusinessType;
    @BindView(R.id.et_businessProject)
    TextView etBusinessProject;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.et_contact)
    TextView etContact;
    @BindView(R.id.et_contactInformation)
    TextView etContactInformation;
    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.et_fieldTime)
    TextView etFieldTime;
    @BindView(R.id.et_loginAccount)
    TextView etLoginAccount;
    @BindView(R.id.ll_loginAccount)
    LinearLayout ll_loginAccount;
    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    ArrayList<String> images = new ArrayList<>();
    ImageItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String fieldTime;
    String validDate;
    String businessType = "";
    String id;
    CompanyBean companyBean;
    @BindView(R.id.bt_ok)
    Button btOk;
    @BindView(R.id.bg)
    LinearLayout bg;
    @BindView(R.id.et_companyType)
    TextView etCompanyType;
    private CustomDialog customDialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_company_info;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageItemAdapter(R.layout.item_image, images);
        itemRvImages.setAdapter(adapter);
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


    public void showCompanyInfo(CompanyBean data) {
        etOperatorName.setText(data.getOperatorName() + "");
        etSocialCreditCode.setText(data.getSocialCreditCode() + "");
        etLicenseNumber.setText(data.getLicenseNumber() + "");
        etLegalRepresentative.setText(data.getLegalRepresentative() + "");
        etAddress.setText(data.getAddress() + "");
        etBusinessPlace.setText(data.getBusinessPlace() + "");
        etBusinessType.setText(data.getBusinessTypeText() + "");
        etBusinessProject.setText(data.getBusinessProjectText() + "");
        businessType = data.getBusinessType();
        validDate = data.getValidDate();
        etEndTime.setText(data.getValidDate() + "");
        etContact.setText(data.getContact() + "");
        etContactInformation.setText(data.getContactInformation() + "");
        etFieldTime.setText(data.getFieldTime() + "");
        fieldTime = data.getFieldTime();
//        etLocation.setText(data.getLatitude() + "," + data.getLongitude());
        etCompanyType.setText(data.getCompanyTypeText() + "");
        getImage(data.getId());

        if (data.getCompanyType()==2){
            ll_loginAccount.setVisibility(View.VISIBLE);
            ll_user.setVisibility(View.VISIBLE);
            etLoginAccount.setText(data.getLoginAccount()+"");
        }else {
            ll_loginAccount.setVisibility(View.GONE);
            ll_user.setVisibility(View.GONE);
        }
    }

    void getData(String id) {
        RxNetUtils.request(getApi(ApiService.class).getCompanyInfo(id), new RequestObserver<JsonT<CompanyBean>>(this) {
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

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_user, R.id.bt_password, R.id.et_location, R.id.bt_delete, R.id.et_nav, R.id.et_record})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                startActivityForResult(new Intent(this, AddCompanyActivity.class).putExtra("id", companyBean.getId()), 1001);
                break;
            case R.id.bt_user:
                startActivityForResult(new Intent(this, AddCompanyActivity.class).putExtra("id", companyBean.getId()), 1001);
                break;
            case R.id.bt_password:
                startActivityForResult(new Intent(this, PasswordActivity.class).putExtra("id", companyBean.getId()).putExtra("page", "company"), 1001);
                break;
            case R.id.bt_ok:
                if (companyBean == null) return;
                startActivity(new Intent(this, XCHZFActivity.class).putExtra("company", companyBean));
                break;
            case R.id.et_record:

                startActivity(new Intent(this, CheckListActivity.class).putExtra("id", companyBean.getId()));
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
                        NavUtils.invokeNavi(this, null, "中智.智慧路灯", companyBean.getLatitude() + "," + companyBean.getLongitude());
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
        RxNetUtils.request(getApi(ApiService.class).getImageBase64("company", id), new RequestObserver<JsonT<List<ImageBack>>>(this) {
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
