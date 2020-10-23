package com.zz.supervision.business.company;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
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
    EditText etOperatorName;
    @BindView(R.id.et_socialCreditCode)
    EditText etSocialCreditCode;
    @BindView(R.id.et_licenseNumber)
    EditText etLicenseNumber;
    @BindView(R.id.et_legalRepresentative)
    EditText etLegalRepresentative;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.et_businessPlace)
    EditText etBusinessPlace;
    @BindView(R.id.et_businessType)
    TextView etBusinessType;
    @BindView(R.id.et_businessProject)
    TextView etBusinessProject;
    @BindView(R.id.et_endTime)
    TextView etEndTime;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.et_companyType)
    TextView etCompanyType;
    @BindView(R.id.et_contact)
    EditText etContact;
    @BindView(R.id.et_contactInformation)
    EditText etContactInformation;
    @BindView(R.id.et_fieldTime)
    TextView etFieldTime;
    @BindView(R.id.et_location)
    TextView etLocation;
    ArrayList<ImageBack> images = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String fieldTime;
    String validDate;
    String businessType = "";
    String specificType = "";
    String companyType = "";
    String businessProject = "";
    String businessProjectText = "";
    List<BusinessType> businessTypeList = new ArrayList<>();
    List<BusinessType> business2TypeList = new ArrayList<>();
    List<BusinessType> companyTypeList = new ArrayList<>();

    SelectPopupWindows selectPopupWindows;
    SelectPopupWindows selectPopupWindows1;
    SelectPopupWindows selectPopupWindows2;

    String id;
    double lon = 123.6370661238426;
    double lat = 47.216275430241495;

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
                ArrayList<String> localPath = new ArrayList<>();
                for (int i = 0; i < images.size(); i++) {
                    if (!TextUtils.isEmpty(images.get(i).getPath())) {
                        localPath.add(images.get(i).getPath());
                    } else {
                    }
                }
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(9 - images.size()) // 图片的最大选择数量，小于等于0时，不限数量。
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
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
            toolbarTitle.setText("编辑企业");
            mPresenter.getImage("company", id);
            showLoading("");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", "business_type");
        mPresenter.getBusinessType(params);

        Map<String, Object> params1 = new HashMap<>();
        params1.put("dictType", "company_type");
        mPresenter.getCompanyType(params1);


        Map<String, Object> params2 = new HashMap<>();
        params2.put("dictType", "specific_type");
        mPresenter.getBusiness2Type(params2);

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    @Override
    public CompanyAddPresenter initPresenter() {
        return new CompanyAddPresenter(this);
    }

    @OnClick({R.id.et_companyType, R.id.et_location, R.id.et_businessType, R.id.et_businessProject, R.id.et_endTime, R.id.et_fieldTime, R.id.bt_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_businessType:
                showSelectPopWindow1();
                break;
            case R.id.et_companyType:
                UIAdjuster.closeKeyBoard(this);
                List<String> list2 = new ArrayList<>();
                List<String> list12 = new ArrayList<>();
                for (int i = 0; i < companyTypeList.size(); i++) {
                    list2.add(companyTypeList.get(i).getDictLabel());
                    list12.add(companyTypeList.get(i).getDictValue());
                }
                String[] array2 = (String[]) list2.toArray(new String[list2.size()]);
                String[] values2 = (String[]) list12.toArray(new String[list12.size()]);
                selectPopupWindows1 = new SelectPopupWindows(this, array2);
                selectPopupWindows1.showAtLocation(findViewById(R.id.bg),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                selectPopupWindows1.setOnItemClickListener(new SelectPopupWindows.OnItemClickListener() {
                    @Override
                    public void onSelected(int index, String msg) {
                        etCompanyType.setText(msg);
                        companyType = values2[index];
                    }

                    @Override
                    public void onCancel() {
                        selectPopupWindows1.dismiss();
                    }
                });
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
        etOperatorName.setText(data.getOperatorName() + "");
        etSocialCreditCode.setText(data.getSocialCreditCode() + "");
        etLicenseNumber.setText(data.getLicenseNumber() + "");
        etLegalRepresentative.setText(data.getLegalRepresentative() + "");
        etAddress.setText(data.getAddress() + "");
        etBusinessPlace.setText(data.getBusinessPlace() + "");
        etBusinessType.setText(data.getBusinessTypeText() + "");
        etBusinessProject.setText(data.getBusinessProjectText() + "");
        businessType = data.getBusinessType();
        specificType = data.getSpecificType();
        businessProject = data.getBusinessProject();
        String[] split = businessProject.split(",");
        projectBeans.clear();
        for (int i = 0; i < split.length; i++) {
            projectBeans.add(new BusinessProjectBean(false, "", split[i], ""));
        }
        companyType = data.getCompanyType();
        validDate = data.getValidDate();
        etEndTime.setText(data.getValidDate() + "");
        etContact.setText(data.getContact() + "");
        etContactInformation.setText(data.getContactInformation() + "");
        etFieldTime.setText(data.getFieldTime() + "");
        fieldTime = data.getFieldTime();
        lat = data.getLatitude();
        lon = data.getLongitude();
        etLocation.setText("位置");
        etCompanyType.setText(data.getCompanyTypeText() + "");
    }

    @Override
    public void showSubmitResult(String id) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ids.add(images.get(i).getId());
        }
        mPresenter.uploadCompanyImgs(id, new Gson().toJson(ids));
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
    public void showBusinessType(List<BusinessType> list) {
        if (list != null) {
            businessTypeList.clear();
            businessTypeList.addAll(list);
        }
    }

    @Override
    public void showBusiness2Type(List<BusinessType> list) {
        if (list != null) {
            business2TypeList.clear();
            business2TypeList.addAll(list);
        }
    }

    @Override
    public void showCompanyType(List<BusinessType> list) {
        if (list != null) {
            companyTypeList.clear();
            companyTypeList.addAll(list);
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

        String operatorName = etOperatorName.getText().toString();
        if (!TextUtils.isEmpty(operatorName)) {
            params.put("operatorName", operatorName);
        }


        String socialCreditCode = etSocialCreditCode.getText().toString();
        if (!TextUtils.isEmpty(socialCreditCode)) {
            params.put("socialCreditCode", socialCreditCode);
        }


        String licenseNumber = etLicenseNumber.getText().toString();
        if (!TextUtils.isEmpty(licenseNumber)) {
            params.put("licenseNumber", licenseNumber);
        }


        String legalRepresentative = etLegalRepresentative.getText().toString();
        if (!TextUtils.isEmpty(legalRepresentative)) {
            params.put("legalRepresentative", legalRepresentative);
        }


        String address = etAddress.getText().toString();
        if (!TextUtils.isEmpty(address)) {
            params.put("address", address);
        }


        String businessPlace = etBusinessPlace.getText().toString();
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
        String contact = etContact.getText().toString();
        if (!TextUtils.isEmpty(contact)) {
            params.put("contact", contact);
        }

        String contactInformation = etContactInformation.getText().toString();
        if (!TextUtils.isEmpty(contactInformation)) {
            params.put("contactInformation", contactInformation);
        }


        if (!TextUtils.isEmpty(fieldTime)) {
            params.put("fieldTime", fieldTime);
        }

        if (!TextUtils.isEmpty(id)) {
            params.put("id", id);
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
                                    imageBack.setPath(file.getPath());
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


}
