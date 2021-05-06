package com.zz.supervision.business.equipment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessProjectBean;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.bean.PLocation;
import com.zz.supervision.business.company.AddCompanyActivity;
import com.zz.supervision.business.company.SelectLocationActivity;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.business.equipment.mvp.presenter.EquipmentAddPresenter;
import com.zz.supervision.business.inspenction.XCHZFActivity;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.LogUtils;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 设备添加
 */
public class AddEquipmentActivity extends MyBaseActivity<Contract.IsetEquipmentAddPresenter> implements Contract.IGetEquipmentAddView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbar_title;

    @BindView(R.id.ig_deviceCode)
    ItemGroup ig_deviceCode;
    @BindView(R.id.ig_deviceModel)
    ItemGroup ig_deviceModel;
    @BindView(R.id.ig_deviceType)
    ItemGroup ig_deviceType;
    @BindView(R.id.ig_deviceName)
    ItemGroup ig_deviceName;
    @BindView(R.id.ig_registStatus)
    ItemGroup ig_registStatus;
    @BindView(R.id.ig_registOrganizationId)
    ItemGroup ig_registOrganizationId;
    @BindView(R.id.ig_registTime)
    ItemGroup ig_registTime;
    @BindView(R.id.ig_registRecorder)
    ItemGroup ig_registRecorder;
    @BindView(R.id.ig_registNumber)
    ItemGroup ig_registNumber;
    @BindView(R.id.ig_registCode)
    ItemGroup ig_registCode;
    @BindView(R.id.ig_usageStatus)
    ItemGroup ig_usageStatus;
    @BindView(R.id.ig_usageUpdateDate)
    ItemGroup ig_usageUpdateDate;
    @BindView(R.id.ig_manufacturerName)
    ItemGroup ig_manufacturerName;
    @BindView(R.id.ig_projectNumber)
    ItemGroup ig_projectNumber;
    @BindView(R.id.ig_manufacturerDate)
    ItemGroup ig_manufacturerDate;
    @BindView(R.id.ig_installationCompany)
    ItemGroup ig_installationCompany;
    @BindView(R.id.ig_completionDate)
    ItemGroup ig_completionDate;
    @BindView(R.id.ig_totalLength)
    ItemGroup ig_totalLength;
    @BindView(R.id.ig_licensePlate)
    ItemGroup ig_licensePlate;
    @BindView(R.id.ig_maintenanceCompany)
    ItemGroup ig_maintenanceCompany;

    @BindView(R.id.ig_companyInternalNum)
    ItemGroup ig_companyInternalNum;
    @BindView(R.id.ig_factoryNum)
    ItemGroup ig_factoryNum;
    @BindView(R.id.ig_locationName)
    ItemGroup ig_locationName;
    @BindView(R.id.ig_certifyingAuthority)
    ItemGroup ig_certifyingAuthority;
    @BindView(R.id.ig_authorityDate)
    ItemGroup ig_authorityDate;
    @BindView(R.id.ig_maintenanceContact)
    ItemGroup ig_maintenanceContact;
    @BindView(R.id.ig_emergencyCall)
    ItemGroup ig_emergencyCall;

    @BindView(R.id.ig_location)
    ItemGroup ig_location;
    @BindView(R.id.bt_ok)
    Button bt_ok;
    String deviceType1;
    String deviceType2;
    String deviceType3;
    List<BusinessType> list_regist_status = new ArrayList<>();
    List<BusinessType> list_usage_status = new ArrayList<>();
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<String> localPath = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    CompanyBean company;
    String companyId;
    String id;

    double lon = 123.6370661238426;
    double lat = 47.216275430241495;

    @Override
    protected int getContentView() {
        return R.layout.activity_equipment_add;

    }

    @Override
    public Contract.IsetEquipmentAddPresenter initPresenter() {
        return new EquipmentAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        companyId = getIntent().getStringExtra("companyId");

        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)){
            mPresenter.getData(id);
            mPresenter.getImage( id);
            toolbar_title.setText("编辑设备");
        }
        mPresenter.getDeviceType();
        mPresenter.getDicts("tzsb_regist_status");
        mPresenter.getDicts("tzsb_usage_status");
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
                        .start(AddEquipmentActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
        init();

    }

    List<DictBean> options1Items = new ArrayList<>();
    List<List<DictBean>> options2Items = new ArrayList<>();
    List<List<List<DictBean>>> options3Items = new ArrayList<>();
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_ok:
                postData();
                break;
        }
    }

    private void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("companyId", companyId + "");
        if (!TextUtils.isEmpty(id)){
            params.put("id", id);
        }

        params.put("deviceCode", getText(ig_deviceCode));
        params.put("deviceModel", getText(ig_deviceModel));
        params.put("deviceType1", deviceType1 + "");
        params.put("deviceType2", deviceType2 + "");
        params.put("deviceType3", deviceType3 + "");
        params.put("deviceName", getText(ig_deviceName));
        params.put("registStatus", ig_registStatus.getSelectValue() + "");
        params.put("registOrganizationId", ig_registOrganizationId.getSelectValue() + "");
        params.put("registTime", getText(ig_registTime) + "");
        params.put("registRecorder", getText(ig_registRecorder) + "");
        params.put("registNumber", getText(ig_registNumber) + "");
        params.put("registCode", getText(ig_registCode) + "");
        params.put("usageStatus", ig_usageStatus.getSelectValue() + "");
        params.put("usageUpdateDate", getText(ig_usageUpdateDate) + "");
        params.put("manufacturerDate", getText(ig_manufacturerDate) + "");
        params.put("completionDate", getText(ig_completionDate) + "");
        params.put("manufacturerName", getText(ig_manufacturerName) + "");
        params.put("projectNumber", getText(ig_projectNumber) + "");
        params.put("installationCompany", getText(ig_installationCompany) + "");
        params.put("licensePlate", getText(ig_licensePlate) + "");
        params.put("totalLength", getText(ig_totalLength) + "");
        params.put("maintenanceCompany", getText(ig_maintenanceCompany) + "");

        params.put("companyInternalNum", getText(ig_companyInternalNum) + "");
        params.put("factoryNum", getText(ig_factoryNum) + "");
        params.put("locationName", getText(ig_locationName) + "");
        params.put("certifyingAuthority", getText(ig_certifyingAuthority) + "");
        params.put("authorityDate", getText(ig_authorityDate) + "");

        params.put("maintenanceContact", getText(ig_maintenanceContact));
        params.put("emergencyCall", getText(ig_emergencyCall));


        if (lon != 0.0 && lat != 0.0) {
            params.put("longitude", lon);
            params.put("latitude", lat);
        }
        mPresenter.submitData(params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

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
            } else if (requestCode == 2001) {
                OrganizationBean select = data.getParcelableExtra("select");
                ig_registOrganizationId.setChooseContent(select.getOperatorName() + "");
                ig_registOrganizationId.setSelectValue(select.getId() + "");
            } else if (requestCode == 3001) {
                if (data == null) return;
                PLocation poiInfo = data.getParcelableExtra("location");
                if (poiInfo == null) return;
                if (poiInfo.getLocation() == null) return;
                lat = poiInfo.getLocation().latitude;
                lon = poiInfo.getLocation().longitude;
                ig_location.setChooseContent(poiInfo.getAddress() + "");

            }

        }
    }

    @Override
    public void showEquipmentInfo(EquipmentBean data) {
        ig_deviceCode.setChooseContent(data.getDeviceCode() + "");
        ig_deviceModel.setChooseContent(data.getDeviceModel() + "");
        ig_deviceType.setChooseContent(data.getDeviceType1Text() + "" + data.getDeviceType2Text() + "" + data.getDeviceType3Text());
        deviceType1 = data.getDeviceType1();
        deviceType2 = data.getDeviceType2();
        deviceType3 = data.getDeviceType3();
        ig_deviceName.setChooseContent(data.getDeviceName() + "");
        ig_registStatus.setChooseContent(data.getRegistStatusText() + "");
        ig_registStatus.setSelectValue(data.getRegistStatus() + "");
        ig_registOrganizationId.setChooseContent(data.getRegistOrganizationName());
        ig_registOrganizationId.setSelectValue(data.getRegistOrganizationId() + "");
        ig_registTime.setChooseContent(data.getRegistTime() + "");
        ig_registRecorder.setChooseContent(data.getRegistRecorder() + "");
        ig_registNumber.setChooseContent(data.getRegistNumber() + "");
        ig_registCode.setChooseContent(data.getRegistCode() + "");
        ig_usageStatus.setChooseContent(data.getUsageStatusText() + "");
        ig_usageStatus.setSelectValue(data.getUsageStatus() + "");
        ig_manufacturerName.setChooseContent(data.getManufacturerName() + "");
        ig_usageUpdateDate.setChooseContent(data.getUsageUpdateDate() + "");
        ig_manufacturerDate.setChooseContent(data.getManufacturerDate() + "");
        ig_completionDate.setChooseContent(data.getCompletionDate() + "");
        ig_projectNumber.setChooseContent(data.getProjectNumber() + "");
        ig_installationCompany.setChooseContent(data.getInstallationCompany() + "");

        ig_companyInternalNum.setChooseContent(data.getCompanyInternalNum()+"");
        ig_factoryNum.setChooseContent(data.getFactoryNum()+"");
        ig_locationName.setChooseContent(data.getLocationName()+"");
        ig_certifyingAuthority.setChooseContent(data.getCertifyingAuthority()+"");
        ig_authorityDate.setChooseContent(data.getAuthorityDate()+"");


        ig_maintenanceContact.setVisibility(deviceType1.equals("4") ? View.VISIBLE : View.GONE);
        ig_maintenanceContact.setChooseContent(deviceType1.equals("4") ?data.getMaintenanceContact()+"" :"");

        ig_emergencyCall.setVisibility(deviceType1.equals("4") ? View.VISIBLE : View.GONE);
        ig_emergencyCall.setChooseContent(deviceType1.equals("4") ?data.getEmergencyCall()+"" :"");

//        ig_totalLength.setVisibility(deviceType1.equals("3") ? View.VISIBLE : View.GONE);
//        ig_totalLength.setChooseContent(deviceType1.equals("3") ?data.getTotalLength()+"" :"");

        ig_licensePlate.setVisibility(deviceType1.equals("8") ? View.VISIBLE : View.GONE);
        ig_licensePlate.setChooseContent(deviceType1.equals("8") ?data.getLicensePlate()+ "": "");

        ig_maintenanceCompany.setChooseContent(deviceType1.equals("4") ?data.getMaintenanceCompany()+ "": "");
        ig_maintenanceCompany.setVisibility(deviceType1.equals("4") ? View.VISIBLE : View.GONE);
//        ig_location.setChooseContent();
        lon = data.getLongitude();
        lat = data.getLatitude();
    }

    @Override
    public void showSubmitResult(String id) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ids.add(images.get(i).getId());
        }
        mPresenter.uploadEquipmentImgs(id, new Gson().toJson(ids));
    }

    @Override
    public void showResult() {
        showToast("提交成功");
        String  from = getIntent().getStringExtra("from");
        if ("companyInfo".equals(from)) {
            setResult(RESULT_OK);
        } finish();

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
        if (type.equals("tzsb_regist_status")) {
            list_regist_status.clear();
            list_regist_status.addAll(list);
        } else if (type.equals("tzsb_usage_status")) {
            list_usage_status.clear();
            list_usage_status.addAll(list);
        }
    }

    @Override
    public void showDeviceType(List<DictBean> list) {

        if (list == null) return;

        for (DictBean categoryBean : list) {
            options1Items.add(categoryBean);
            List<List<DictBean>> optionsItems3 = new ArrayList<>();
            List<DictBean> optionsItems2 = new ArrayList<>();
            if (categoryBean.getClist() != null && categoryBean.getClist().size() > 0) {
                for (DictBean child1 : categoryBean.getClist()) {
                    List<DictBean> optionsItems = new ArrayList<>();
                    for (DictBean child2 : child1.getClist()) {
                        optionsItems.add(child2);
                    }
                    optionsItems3.add(optionsItems);
                    optionsItems2.add(child1);
                }

            } else {
                optionsItems2.add(new DictBean());
                List<DictBean> optionsItems = new ArrayList<>();
                optionsItems.add(new DictBean());
                optionsItems3.add(optionsItems);
            }
            options2Items.add(optionsItems2);
            options3Items.add(optionsItems3);

        }
        LogUtils.v(list.toString());
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

    void init() {
        ig_registOrganizationId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddEquipmentActivity.this, OrganizationListActivity.class).putExtra("url","tzsbRegistOrganizationList"), 2001);
            }
        });
        ig_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddEquipmentActivity.this, SelectLocationActivity.class).putExtra("lat", lat).putExtra("lon", lon), 3001);

            }
        });

        ig_registTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_registTime);
            }
        });
        ig_usageUpdateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_usageUpdateDate);
            }
        });
        ig_manufacturerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_manufacturerDate);
            }
        });
        ig_completionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_completionDate);
            }
        });
        ig_authorityDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_authorityDate);
            }
        });
        ig_registStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_registStatus, list_regist_status);
            }
        });
        ig_usageStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_usageStatus, list_usage_status);
            }
        });
        ig_deviceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIAdjuster.closeKeyBoard(AddEquipmentActivity.this);
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AddEquipmentActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置

                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(option2).getPickerViewText();
                        if (options3Items.get(options1) != null && options3Items.get(options1).size() > 0
                                && options3Items.get(options1).get(option2) != null && options3Items.get(options1).get(option2).size() > 0
                                && options3Items.get(options1).get(option2).get(options3) != null) {
                            tx = tx + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                            deviceType3 = options3Items.get(options1).get(option2).get(options3).getDictValue();
                        } else {
                            deviceType3 = "";
                        }
                        ig_deviceType.setChooseContent(tx);
                        deviceType1 = options1Items.get(options1).getDictValue();
                        deviceType2 = options2Items.get(options1).get(option2).getDictValue();
//                        ig_totalLength.setVisibility(deviceType1.equals("3") ? View.VISIBLE : View.GONE);
                        ig_licensePlate.setVisibility(deviceType1.equals("8") ? View.VISIBLE : View.GONE);
                        ig_maintenanceCompany.setVisibility(deviceType1.equals("4") ? View.VISIBLE : View.GONE);
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();

            }
        });
        bt_ok.setOnClickListener(this);
    }

    SelectPopupWindows selectPopupWindows;

    void showSelectPopWindow1(ItemGroup itemGroup, List<BusinessType> businessTypeList) {
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
                itemGroup.setChooseContent(msg);
                itemGroup.setSelectValue(values[index]);
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    private void selectTime(ItemGroup itemGroup) {

        DatePickDialog dialog = new DatePickDialog(AddEquipmentActivity.this);
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

                itemGroup.setChooseContent(time);

            }
        });
        dialog.show();
    }
}