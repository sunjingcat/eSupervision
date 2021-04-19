package com.zz.supervision.business.company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.previewlibrary.ZoomMediaLoader;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessProjectBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PLocation;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.company.mvp.Contract;
import com.zz.supervision.business.company.mvp.presenter.CompanyAddPresenter;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.ImageLoader;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class AddCompanyActivity extends MyBaseActivity<Contract.IsetCompanyAddPresenter> implements Contract.IGetCompanyAddView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_operatorName)
    ItemGroup etOperatorName;
    @BindView(R.id.et_socialCreditCode)
    ItemGroup etSocialCreditCode;
    @BindView(R.id.et_licenseNumber)
    ItemGroup etLicenseNumber;
    @BindView(R.id.et_legalRepresentative)
    ItemGroup etLegalRepresentative;
    @BindView(R.id.et_address)
    ItemGroup etAddress;
    @BindView(R.id.et_businessPlace)
    ItemGroup etBusinessPlace;
    @BindView(R.id.et_businessType)
    TextView etBusinessType;
    @BindView(R.id.et_businessProject)
    TextView etBusinessProject;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.et_contact)
    ItemGroup etContact;
    @BindView(R.id.et_password)
    ItemGroup et_password;
    @BindView(R.id.et_passwordCon)
    ItemGroup et_passwordCon;
    @BindView(R.id.et_loginAccount)
    ItemGroup et_loginAccount;
    @BindView(R.id.et_coldstorage)
    ItemGroup et_coldstorage;
    @BindView(R.id.ll_loginAccount)
    LinearLayout ll_loginAccount;
    @BindView(R.id.ll_jtcompanyType)
    LinearLayout ll_jtcompanyType;
    @BindView(R.id.ll_yp)
    LinearLayout ll_yp;
    @BindView(R.id.ll_fieldTime)
    LinearLayout ll_fieldTime;
    @BindView(R.id.ll_jingying)
    LinearLayout ll_jingying;
    @BindView(R.id.ll_businessType)
    LinearLayout ll_businessType;
    @BindView(R.id.ll_businessProject)
    LinearLayout ll_businessProject;
    @BindView(R.id.et_contactInformation)
    ItemGroup etContactInformation;
    @BindView(R.id.et_fieldTime)
    TextView etFieldTime;
    @BindView(R.id.et_location)
    TextView etLocation;
    @BindView(R.id.et_companyType)
    TextView et_companyType;
    @BindView(R.id.et_jtcompanyType)
    TextView et_jtcompanyType;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    @BindView(R.id.ig_qualityContact)
    ItemGroup ig_qualityContact;
    @BindView(R.id.ig_warehouseAddress)
    ItemGroup ig_warehouseAddress;
    @BindView(R.id.ig_businessScope)
    ItemGroup ig_businessScope;
    String fieldTime;
    String validDate;
    String businessType = "";
    String specificType = "";
    String companyType = "";
    String businessScope = "";
    String url = "";
    String ypCompanyType = "";// ,
    String ylqxCompanyType = "";// ,
    String businessProject = "";
    String businessProjectText = "";
    List<BusinessType> businessTypeList = new ArrayList<>();
    List<BusinessType> jtCompanyTypeList = new ArrayList<>();
    List<BusinessType> business2TypeList = new ArrayList<>();


    SelectPopupWindows selectPopupWindows;
    SelectPopupWindows selectPopupWindows1;
    SelectPopupWindows selectPopupWindows2;

    String id;
    double lon = 123.6370661238426;
    double lat = 47.216275430241495;
    ArrayList<String> localPath = new ArrayList<>();

    @Override
    protected int getContentView() {
        return R.layout.activity_company_add;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        ZoomMediaLoader.getInstance().init(new ImageLoader());
        itemRvImages.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ImageDeleteItemAdapter(this, images);
        itemRvImages.setAdapter(adapter);
        adapter.setOnclick(new ImageDeleteItemAdapter.Onclick() {
            @Override
            public void onclickAdd(View v, int option) {
                localPath.clear();
                int sever = 0;
                for (int i = 0; i < images.size(); i++) {
                    if (!TextUtils.isEmpty(images.get(i).getPath())) {
                        if (!images.get(i).getPath().contains("zhongzhi")) {
                            localPath.add(images.get(i).getPath());
                        } else {
                            sever++;
                        }
                    }

                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - sever) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setSelected(localPath) // 把已选的图片传入默认选中。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(AddCompanyActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });

        id = getIntent().getStringExtra("id");
        companyType = getIntent().getStringExtra("companyType") + "";
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(companyType, id);
            toolbarTitle.setText("编辑企业");
            mPresenter.getImage(companyType, id);
            showLoading("");
        }
        et_companyType.setText(getIntent().getStringExtra("companyTypeText") + "");
        if (companyType.equals("1")) {
            ll_loginAccount.setVisibility(View.GONE);
            mPresenter.getDicts("business_type");
            mPresenter.getDicts("specific_type");
        } else if (companyType.equals("2")) {
            ll_loginAccount.setVisibility(View.VISIBLE);
            et_coldstorage.setVisibility(View.VISIBLE);
        } else if (companyType.equals("3") || companyType.equals("4")) {//"companyType": "3","companyTypeText": "药品",
            ll_loginAccount.setVisibility(View.GONE);
            et_coldstorage.setVisibility(View.GONE);
            ll_businessType.setVisibility(View.GONE);
            ll_businessProject.setVisibility(View.GONE);
            ll_jtcompanyType.setVisibility(View.VISIBLE);
            mPresenter.getDicts(companyType.equals("3") ? "ypCompanyType" : "ylqxCompanyType");
            if (companyType.equals("3")) {
                ll_yp.setVisibility(View.VISIBLE);
                etContact.setTitle("企业负责人");
            }

        } else if (companyType.equals("6")){
            ll_loginAccount.setVisibility(View.GONE);
            et_coldstorage.setVisibility(View.GONE);
            ll_businessType.setVisibility(View.GONE);
            ll_businessProject.setVisibility(View.GONE);
            etLicenseNumber.setVisibility(View.GONE);
            ll_jingying.setVisibility(View.GONE);
            ll_fieldTime.setVisibility(View.GONE);
            etOperatorName.setTitle("单位名称");
            etAddress.setTitle("单位地址");
            etSocialCreditCode.setTitle("社会信用代码");
            etLegalRepresentative.setTitle("法定代表人");
            etContact.setTitle("安全管理联系人");
            etContactInformation.setTitle("安全管理联系电话");
        }
        ig_businessScope.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(AddCompanyActivity.this, OperatingitemsActivity.class), 3001);
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @Override
    public CompanyAddPresenter initPresenter() {
        return new CompanyAddPresenter(this);
    }

    @OnClick({R.id.et_location, R.id.et_businessType, R.id.et_jtcompanyType, R.id.et_businessProject, R.id.et_endTime, R.id.et_fieldTime, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_businessType:
                showSelectPopWindow1();
                break;
            case R.id.et_jtcompanyType:
                showSelectPopWindow3();
                break;
            case R.id.et_businessProject:
                if (TextUtils.isEmpty(businessType)) {
                    showToast("请先选择主体业态");
                    return;
                }
                startActivityForResult(new Intent(this, BusinessProjectActivity.class).putExtra("type", businessType).putExtra("projectBeans", projectBeans), 2001);
                break;
            case R.id.et_endTime:
                DatePickDialog dialog = new DatePickDialog(AddCompanyActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog.setYearLimt(20);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        validDate = time;
                        etEndTime.setText(time);
                    }
                });
                dialog.show();
                break;
            case R.id.et_fieldTime:
                DatePickDialog dialog1 = new DatePickDialog(AddCompanyActivity.this);
                //设置上下年分限制
                //设置上下年分限制
                dialog1.setYearLimt(20);
                //设置标题
                dialog1.setTitle("选择时间");
                //设置类型
                dialog1.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog1.setMessageFormat("yyyy-MM-dd");
                //设置选择回调
                dialog1.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        Log.v("+++", date.toString());
                    }
                });
                //设置点击确定按钮回调
                dialog1.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {

                        String time = TimeUtils.getTime(date.getTime(), TimeUtils.DATE_FORMAT_DATE);
                        fieldTime = time;
                        etFieldTime.setText(time);
                    }
                });
                dialog1.show();
                break;
            case R.id.bt_ok:
                postData();

                break;
            case R.id.et_location:
                startActivityForResult(new Intent(AddCompanyActivity.this, SelectLocationActivity.class).putExtra("lat", lat).putExtra("lon", lon), 1002);


                break;
        }
    }


    @Override
    public void showCompanyInfo(CompanyBean data) {
        if (data == null) return;
        etOperatorName.setChooseContent(data.getOperatorName() + "");
        etSocialCreditCode.setChooseContent(data.getSocialCreditCode() + "");
        etLicenseNumber.setChooseContent(data.getLicenseNumber() + "");
        etLegalRepresentative.setChooseContent(data.getLegalRepresentative() + "");
        etAddress.setChooseContent(data.getAddress() + "");
        etBusinessPlace.setChooseContent(data.getBusinessPlace() + "");
        etBusinessType.setText(data.getBusinessTypeText() + "");
        etBusinessProject.setText(data.getBusinessProjectText() + "");
        businessType = data.getBusinessType();
        specificType = data.getSpecificType();
        businessProject = data.getBusinessProject() + "";
        if (businessProject.contains(",")) {
            String[] split = businessProject.split(",");
            projectBeans.clear();
            for (int i = 0; i < split.length; i++) {
                projectBeans.add(new BusinessProjectBean(false, "", split[i], ""));
            }
        }
        companyType = data.getCompanyType() + "";
        validDate = data.getValidDate();
        etEndTime.setText(data.getValidDate() + "");
        etContact.setChooseContent(data.getContact() + "");
        etContactInformation.setChooseContent(data.getContactInformation() + "");
        etFieldTime.setText(data.getFieldTime() + "");
        fieldTime = data.getFieldTime();
        lat = data.getLatitude();
        lon = data.getLongitude();
        etLocation.setText("位置");
        if (companyType.equals("2")) {
            ll_loginAccount.setVisibility(View.VISIBLE);
            et_password.setVisibility(View.GONE);
            et_passwordCon.setVisibility(View.GONE);
            et_loginAccount.setChooseContent(data.getLoginAccount() + "");
        } else if (companyType.equals("3")) {
            et_jtcompanyType.setText(data.getSpecificTypeText() + "");
            ypCompanyType = data.getSpecificType();
        } else if (companyType.equals("4")) {
            et_jtcompanyType.setText(data.getSpecificTypeText() + "");
            ylqxCompanyType = data.getSpecificType();
        }
        ig_businessScope.setChooseContent(data.getBusinessScopeText() + "");
        ig_qualityContact.setChooseContent(data.getQualityContact() + "");
        ig_warehouseAddress.setChooseContent(data.getWarehouseAddress() + "");
        businessScope = data.getBusinessScope();


    }

    @Override
    public void showSubmitResult(String id) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ids.add(images.get(i).getId());
        }
        mPresenter.uploadCompanyImgs(companyType, id, new Gson().toJson(ids));
    }

    @Override
    public void showResult() {
        finish();

        showToast("提交成功");
    }

    @Override
    public void showPostImage(int position, String id) {
        if (!TextUtils.isEmpty(id)) {
            images.get(position).setId(id);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDicts(String type, List<BusinessType> list) {
        if (list != null) {
            if ("business_type".equals(type)) {
                businessTypeList.clear();
                businessTypeList.addAll(list);
            } else if ("specific_type".equals(type)) {
                business2TypeList.clear();
                business2TypeList.addAll(list);
            } else if ("ypCompanyType".equals(type) || "ylqxCompanyType".equals(type)) {
                jtCompanyTypeList.clear();
                jtCompanyTypeList.addAll(list);
            }
        }
    }


    @Override
    public void showImage(List<ImageBack> list) {

        if (list == null) return;
        showLoading("");
        for (ImageBack imageBack : list) {
            String bitmapName = "company_" + imageBack.getId() + ".png";
            String path = getCacheDir() + "/zhongzhi/" + bitmapName;
            File file = new File(path);
            if (file.exists()) {
                imageBack.setPath(path);
            } else {
                Bitmap s1 = GlideUtils.base64ToBitmap(imageBack.getBase64());
                String s = BASE64.saveBitmap(this, imageBack.getId(), s1);
                imageBack.setPath(s);
            }
        }
        images.clear();
        images.addAll(list);
        adapter.notifyDataSetChanged();
        dismissLoading();
    }

    private void postData() {
        Map<String, Object> params = new HashMap<>();

        String operatorName = etOperatorName.getValue();
        if (!TextUtils.isEmpty(operatorName)) {
            params.put("operatorName", operatorName);
        }


        String socialCreditCode = etSocialCreditCode.getValue().toString();
        if (!TextUtils.isEmpty(socialCreditCode)) {
            params.put("socialCreditCode", socialCreditCode);
        }


        String licenseNumber = etLicenseNumber.getValue().toString();
        if (!TextUtils.isEmpty(licenseNumber)) {
            params.put("licenseNumber", licenseNumber);
        }


        String legalRepresentative = etLegalRepresentative.getValue().toString();
        if (!TextUtils.isEmpty(legalRepresentative)) {
            params.put("legalRepresentative", legalRepresentative);
        }


        String address = etAddress.getValue().toString();
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }


        String businessPlace = etBusinessPlace.getValue().toString();
        if (!TextUtils.isEmpty(businessPlace)) {
            params.put("businessPlace", businessPlace);
        }


        if (!TextUtils.isEmpty(businessType)) {
            params.put("businessType", businessType);
        }
        if (!TextUtils.isEmpty(specificType)) {
            params.put("specificType", specificType);
        }
        if (!TextUtils.isEmpty(companyType)) {
            params.put("companyType", companyType);
        }
        if (!TextUtils.isEmpty(validDate)) {
            params.put("validDate", validDate);
        }


        params.put("businessProject", businessProject + "");
        params.put("businessProjectText", businessProjectText + "");


        if (lon != 0.0 && lat != 0.0) {
            params.put("longitude", lon);
            params.put("latitude", lat);
        }
        String contact = etContact.getValue().toString();
        if (!TextUtils.isEmpty(contact)) {
            params.put("contact", contact);
        }

        String contactInformation = etContactInformation.getValue().toString();
        if (!TextUtils.isEmpty(contactInformation)) {
            params.put("contactInformation", contactInformation);
        }


        if (!TextUtils.isEmpty(fieldTime)) {
            params.put("fieldTime", fieldTime);
        }

        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
        }


        if (companyType.equals("2")) {
            String edPassword_ = et_password.getValue().toString();
            String edPasswordAgain_ = et_passwordCon.getValue().toString();
            if (TextUtils.isEmpty(edPassword_)) {
                showToast("请输入新密码");
                return;
            }
            if (TextUtils.isEmpty(edPasswordAgain_)) {
                showToast("请输入确认密码");
                return;
            }
            if (!edPassword_.equals(edPasswordAgain_)) {
                showToast("两次新密码不一致");
                return;
            }
            params.put("loginName", getText(et_loginAccount));
            params.put("password", getText(et_password));
            params.put("coldstorage", getText(et_coldstorage));
        }
        if (companyType.equals("3")) {
            params.put("specificType", ypCompanyType + "");
            params.put("businessScope", businessScope + "");
            params.put("warehouseAddress", getText(ig_warehouseAddress) + "");
            params.put("qualityContact", getText(ig_qualityContact) + "");
        }
        if (companyType.equals("4")) {
            params.put("specificType", ylqxCompanyType + "");
        }
        mPresenter.submitData(params);
    }

    ArrayList<BusinessProjectBean> projectBeans = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data == null) return;
            /*结果回调*/
            if (requestCode == 1101) {
                //获取选择器返回的数据
                ArrayList<String> selectImages = data.getStringArrayListExtra(
                        ImageSelectorUtils.SELECT_RESULT);
                for (String path : selectImages) {
                    if (localPath.contains(path)) {
                        continue;
                    }
                    Luban.with(this)
                            .load(path)
                            .ignoreBy(100)
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {
                                    // TODO 压缩开始前调用，可以在方法内启动 loading UI
                                }

                                @Override
                                public void onSuccess(File file) {
                                    String base = "data:image/jpg;base64," + BASE64.imageToBase64(file.getPath());
                                    ImageBack imageBack = new ImageBack();
                                    imageBack.setPath(path);
                                    imageBack.setBase64(base);
                                    images.add(imageBack);
                                    mPresenter.postImage(images.size() - 1, base);
                                }

                                @Override
                                public void onError(Throwable e) {
                                    // TODO 当压缩过程出现问题时调用
                                }
                            }).launch();

                }
            }
            if (requestCode == 2001) {
                //获取选择器返回的数据
                ArrayList<BusinessProjectBean> projectBeans = data.getParcelableArrayListExtra("bnpj");
                if (projectBeans == null) return;
                this.projectBeans = projectBeans;
                String str = "";
                String content = "";
                for (int i = 0; i < projectBeans.size(); i++) {
                    if (i == projectBeans.size() - 1) {
                        str = str + projectBeans.get(i).getTitle();
                        content = content + projectBeans.get(i).getValue();
                    } else {
                        str = str + projectBeans.get(i).getTitle() + ",";
                        content = content + projectBeans.get(i).getValue() + ",";
                    }
                }
                etBusinessProject.setText(str);
                businessProject = content;
                businessProjectText = str;
            }
            if (requestCode == 1002) {
                if (data == null) return;
                PLocation poiInfo = data.getParcelableExtra("location");
                if (poiInfo == null) return;
                if (poiInfo.getLocation() == null) return;
                lat = poiInfo.getLocation().latitude;
                lon = poiInfo.getLocation().longitude;

                String s = new BigDecimal(lat).toString();
                String s1 = new BigDecimal(lon).toString();
                etLocation.setText(poiInfo.getAddress() + "");
            }
            if (requestCode == 3001) {
                if (data == null) return;
                ArrayList<BusinessType> select = data.getParcelableArrayListExtra("select");
                if (select == null) return;
                String str = "";
                String content = "";
                for (int i = 0; i < select.size(); i++) {
                    if (i == select.size() - 1) {
                        str = str + select.get(i).getDictLabel();
                        content = content + select.get(i).getDictValue();
                    } else {
                        str = str + select.get(i).getDictLabel() + ",";
                        content = content + select.get(i).getDictValue() + ",";
                    }
                }
                ig_businessScope.setChooseContent(str);
                businessScope = content;
            }


        }
    }

    void showSelectPopWindow1() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < businessTypeList.size(); i++) {
            list.add(businessTypeList.get(i).getDictLabel());
            list1.add(businessTypeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows = new SelectPopupWindows(this, array);
        selectPopupWindows.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                etBusinessType.setText(msg);
                businessType = values[index];
                if (businessType.equals("3")) {
                    showSelectPopWindow2();
                }
                businessProject = "";
                businessProjectText = "";
                etBusinessProject.setText("");
                projectBeans.clear();
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    void showSelectPopWindow2() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < business2TypeList.size(); i++) {
            list.add(business2TypeList.get(i).getDictLabel());
            list1.add(business2TypeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows2 = new SelectPopupWindows(this, array);
        selectPopupWindows2.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows2.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                etBusinessType.append("-" + msg);
                specificType = values[index];
            }

            @Override
            public void onCancel() {
                selectPopupWindows2.dismiss();
                showSelectPopWindow1();
            }
        });
    }

    void showSelectPopWindow3() {
        UIAdjuster.closeKeyBoard(this);
        List<String> list = new ArrayList<>();
        List<String> list1 = new ArrayList<>();
        for (int i = 0; i < jtCompanyTypeList.size(); i++) {
            list.add(jtCompanyTypeList.get(i).getDictLabel());
            list1.add(jtCompanyTypeList.get(i).getDictValue());
        }
        String[] array = (String[]) list.toArray(new String[list.size()]);
        String[] values = (String[]) list1.toArray(new String[list1.size()]);
        selectPopupWindows1 = new SelectPopupWindows(this, array);
        selectPopupWindows1.showAtLocation(findViewById(R.id.bg),
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        selectPopupWindows1.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
            @Override
            public void onSelected(int index, String msg) {
                et_jtcompanyType.setText(msg);
                if (companyType.equals("3")) {
                    ypCompanyType = values[index];
                } else {
                    ylqxCompanyType = values[index];
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows1.dismiss();
            }
        });
    }

}
