package com.zz.supervision.business.equipment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DeviceCheck;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.InspectEdit;
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.bean.PLocation;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.business.equipment.mvp.presenter.CheckAddPresenter;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.LogUtils;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 检验
 */
public class InspectActivity extends MyBaseActivity<Contract.IsetCheckAddPresenter> implements Contract.IGetCheckAddView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.ig_inspectionOrganization)
    ItemGroup ig_inspectionOrganization;
    @BindView(R.id.ig_inspectionOrganizationId)
    ItemGroup ig_inspectionOrganizationId;
    @BindView(R.id.ig_organizationalUnit)
    ItemGroup ig_organizationalUnit;
    @BindView(R.id.ig_checkNature)
    ItemGroup ig_checkNature;
    @BindView(R.id.ig_firstCheckDate)
    ItemGroup ig_firstCheckDate;

    @BindView(R.id.ig_guoluCheckStatus)
    ItemGroup ig_guoluCheckStatus;
    @BindView(R.id.ig_anquanfaCheckStatus)
    ItemGroup ig_anquanfaCheckStatus;
    @BindView(R.id.ig_xiansuqiCheckStatus)
    ItemGroup ig_xiansuqiCheckStatus;

    @BindView(R.id.ig_checkCategory)
    ItemGroup ig_checkCategory;
    @BindView(R.id.ig_processStatus)
    ItemGroup ig_processStatus;
    @BindView(R.id.ig_processDate)
    ItemGroup ig_processDate;

    List<BeforeAddDeviceCheck> beforeAddDeviceChecks = new ArrayList<>();
    List<BusinessStatus> businessStatuses = new ArrayList<>();
    List<BusinessType> list_check_status = new ArrayList<>();
    List<BusinessType> list_check_nature = new ArrayList<>();
    List<BusinessType> list_check_category = new ArrayList<>();
    List<BusinessType> list_process_status = new ArrayList<>();
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<String> localPath = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String deviceId;
    String deviceType;

    @Override
    protected int getContentView() {
        return R.layout.activity_inspect_add;

    }

    @Override
    public Contract.IsetCheckAddPresenter initPresenter() {
        return new CheckAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        deviceId = getIntent().getStringExtra("deviceId");
        deviceType = getIntent().getStringExtra("deviceType");
        if (!TextUtils.isEmpty(deviceId)) {
            mPresenter.getData(deviceId);
        }
        mPresenter.getDicts("tzsb_check_status");
        mPresenter.getDicts("tzsb_check_nature");
        mPresenter.getDicts("tzsb_check_category");
        mPresenter.getDicts("tzsb_process_status");
        mPresenter.getOrganizationalUnit();
        mPresenter.getImage(deviceId);
        if (deviceType.equals("1")) {
            ig_anquanfaCheckStatus.setVisibility(View.VISIBLE);
            ig_guoluCheckStatus.setVisibility(View.VISIBLE);
        } else if (deviceType.equals("2")) {
            ig_anquanfaCheckStatus.setVisibility(View.VISIBLE);
        }else if (deviceType.equals("3")) {
            ig_anquanfaCheckStatus.setVisibility(View.VISIBLE);
        }else if (deviceType.equals("4")) {
            ig_xiansuqiCheckStatus.setVisibility(View.VISIBLE);
        }else if (deviceType.equals("5")) {
            ig_xiansuqiCheckStatus.setVisibility(View.VISIBLE);
        }else if (deviceType.equals("6")) {
            ig_xiansuqiCheckStatus.setVisibility(View.VISIBLE);
        }
        initClick();
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
                        .start(InspectActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void postData() {
        DeviceCheck deviceCheck = new DeviceCheck();
        deviceCheck.setDeviceId(deviceId);
        deviceCheck.setInspectionOrganizationId(ig_inspectionOrganization.getSelectValue());
        deviceCheck.setOrganizationalUnitId(ig_organizationalUnit.getSelectValue());
        deviceCheck.setCheckNature(ig_checkNature.getSelectValue());
        deviceCheck.setFirstCheckDate(ig_firstCheckDate.getValue());
        for (int i = 0; i < businessStatuses.size(); i++) {
            BeforeAddDeviceCheck beforeAddDeviceCheck = beforeAddDeviceChecks.get(i);
            BusinessStatus businessStatus = businessStatuses.get(i);
            beforeAddDeviceCheck.setOrganizationalUnitId(businessStatus.getOrganizationalUnit() + "");
            beforeAddDeviceCheck.setChecker(businessStatus.getChecker() + "");
            beforeAddDeviceCheck.setReportId(businessStatus.getReportId() + "");
            beforeAddDeviceCheck.setLastCheckDate(businessStatus.getLastCheckDate() + "");
            beforeAddDeviceCheck.setCheckDate(businessStatus.getCheckDate() + "");
            beforeAddDeviceCheck.setLastCheckDate(businessStatus.getLastCheckDate() + "");
            beforeAddDeviceCheck.setCheckReduction(businessStatus.getCheckReduction() + "");
            beforeAddDeviceCheck.setDeviceProblem(businessStatus.getDeviceProblem() + "");
            beforeAddDeviceCheck.setManageProblem(businessStatus.getManageProblem() + "");
        }
        deviceCheck.setTzsbDeviceCheckDetailList(beforeAddDeviceChecks);

        deviceCheck.setAnquanfaCheckStatus(ig_anquanfaCheckStatus.getSelectValue() );
        deviceCheck.setGuoluCheckStatus(ig_guoluCheckStatus.getSelectValue() );
        deviceCheck.setXiansuqiCheckStatus(ig_xiansuqiCheckStatus.getSelectValue() );

        deviceCheck.setCheckModelType(ig_checkCategory.getSelectValue() );
        deviceCheck.setProcessStatus(ig_processStatus.getSelectValue() );
        deviceCheck.setProcessDate(ig_processStatus.getValue() );

        mPresenter.submitData(deviceCheck);
    }

    @Override
    public void showCheckInfo(DeviceCheck data) {
        if (data == null) {
            mPresenter.beforeAddDeviceCheck(deviceId);
        } else {
            ig_inspectionOrganization.setChooseContent(data.getInspectionOrganizationName());
            ig_inspectionOrganization.setSelectValue(data.getInspectionOrganizationId());
            ig_inspectionOrganizationId.setChooseContent(data.getInspectionOrganizationId());
            ig_organizationalUnit.setChooseContent(data.getOrganizationalUnitName());
            ig_organizationalUnit.setSelectValue(data.getOrganizationalUnitId());
            ig_checkNature.setChooseContent(data.getCheckNatureText());
            ig_checkNature.setSelectValue(data.getCheckNature());
            ig_firstCheckDate.setChooseContent(data.getFirstCheckDate());

            beforeAddDeviceChecks.clear();
            businessStatuses.clear();
            for (BeforeAddDeviceCheck item : data.getTzsbDeviceCheckDetailList()) {
                BusinessStatus businessStatus = new BusinessStatus(this);
                businessStatus.setTitleTv(item.getCheckModelTypeText());
                businessStatus.setOrganizationalUnit(item.getOrganizationalUnitName() + "");
                businessStatus.setSelectOrganizationalUnit(item.getOrganizationalUnitId() + "");
                businessStatus.setChecker(item.getChecker() + "");
                businessStatus.setReportId(item.getReportId() + "");
                businessStatus.setLastCheckDate(item.getLastCheckDate() + "");
                businessStatus.setCheckDate(item.getCheckDate() + "");
                businessStatus.setNextCheckDate(item.getNextCheckDate() + "");
                businessStatus.setDeviceProblem(item.getDeviceProblem() + "");
                businessStatus.setManageProblem(item.getManageProblem() + "");
                businessStatus.setOnClickListener(new BusinessStatus.OnItemClickListener() {
                    @Override
                    public void onOptionPicker(ItemGroup itemGroup) {
                        showOptionsPicker(itemGroup);
                    }

                    @Override
                    public void onClick(ItemGroup itemGroup) {
                        showSelectPopWindow1(itemGroup, list_check_status);
                    }
                });
                businessStatuses.add(businessStatus);
                ll_content.addView(businessStatus);
            }

            ig_anquanfaCheckStatus.setChooseContent(data.getAnquanfaCheckStatusText(),data.getAnquanfaCheckStatus());
            ig_guoluCheckStatus.setChooseContent(data.getGuoluCheckStatusText(),data.getGuoluCheckStatus());
            ig_xiansuqiCheckStatus.setChooseContent(data.getXiansuqiCheckStatusText(),data.getXiansuqiCheckStatus());

            ig_checkCategory.setChooseContent(data.getGuoluCheckStatusText(),data.getGuoluCheckStatus());
            ig_processStatus.setChooseContent(data.getProcessStatusText(),data.getProcessStatus());
            ig_processDate.setChooseContent(data.getProcessDate());


            beforeAddDeviceChecks.addAll(data.getTzsbDeviceCheckDetailList());
        }
    }

    @Override
    public void showSubmitResult(String id) {
        ArrayList<String> ids = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            ids.add(images.get(i).getId());
        }
        mPresenter.uploadCheckImgs(id, new Gson().toJson(ids));
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

        if (type.equals("tzsb_check_status")) {
            list_check_status.clear();
            list_check_status.addAll(list);
        } else if (type.equals("tzsb_check_nature")) {
            list_check_nature.clear();
            list_check_nature.addAll(list);
        } else if (type.equals("tzsb_check_category")) {
            list_check_category.clear();
            list_check_category.addAll(list);
        } else if (type.equals("tzsb_process_status")) {
            list_process_status.clear();
            list_process_status.addAll(list);
        }
    }

    @Override
    public void showOrganizationalUnit(List<DictBean> list) {
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

    @Override
    public void showBeforeAddDeviceCheck(List<BeforeAddDeviceCheck> list) {
        beforeAddDeviceChecks.clear();
        businessStatuses.clear();
        for (BeforeAddDeviceCheck item : list) {
            BusinessStatus businessStatus = new BusinessStatus(this);
            businessStatus.setTitleTv(item.getCheckModelTypeText());
            businessStatus.setTag(item.getCheckModelType());
            businessStatus.setOnClickListener(new BusinessStatus.OnItemClickListener() {
                @Override
                public void onOptionPicker(ItemGroup itemGroup) {
                    showOptionsPicker(itemGroup);
                }

                @Override
                public void onClick(ItemGroup itemGroup) {
                    showSelectPopWindow1(itemGroup, list_check_status);
                }
            });
            businessStatuses.add(businessStatus);
            ll_content.addView(businessStatus);
        }
        beforeAddDeviceChecks.addAll(list);

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
                ig_inspectionOrganization.setChooseContent(select.getOperatorName() + "");
                ig_inspectionOrganization.setSelectValue(select.getId() + "");
                ig_inspectionOrganizationId.setChooseContent(select.getOrganizationCode());
            }

        }
    }

    List<DictBean> options1Items = new ArrayList<>();
    List<List<DictBean>> options2Items = new ArrayList<>();
    List<List<List<DictBean>>> options3Items = new ArrayList<>();
    String type1;
    String type2;
    String type3;

    void initClick() {
        toolbar_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
        ig_firstCheckDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_firstCheckDate);
            }
        });
        ig_processDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_processDate);
            }
        });
        ig_inspectionOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(InspectActivity.this, OrganizationListActivity.class).putExtra("url", "tzsbCheckOrganizationList"), 2001);
            }
        });
        ig_checkNature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_checkNature, list_check_nature);
            }
        });
        ig_anquanfaCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_anquanfaCheckStatus, list_check_status);
            }
        });
        ig_guoluCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_guoluCheckStatus, list_check_status);
            }
        });
        ig_checkCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_checkCategory, list_check_category);
            }
        });
        ig_processStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_processStatus, list_process_status);
            }
        });
        ig_xiansuqiCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_xiansuqiCheckStatus, list_check_status);
            }
        });
        ig_organizationalUnit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsPicker(ig_organizationalUnit);
            }
        });
    }

    void showOptionsPicker(ItemGroup itemGroup) {
        UIAdjuster.closeKeyBoard(InspectActivity.this);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(InspectActivity.this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String tx = options1Items.get(options1).getPickerViewText()
                        + options2Items.get(options1).get(option2).getPickerViewText();
                if (options3Items.get(options1) != null && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(option2) != null && options3Items.get(options1).get(option2).size() > 0
                        && options3Items.get(options1).get(option2).get(options3) != null) {
                    tx = tx + options3Items.get(options1).get(option2).get(options3).getPickerViewText();
                    type3 = options3Items.get(options1).get(option2).get(options3).getDictValue();
                } else {
                    type3 = "";
                }
                type1 = options1Items.get(options1).getDictValue();
                type2 = options2Items.get(options1).get(option2).getDictValue();
                itemGroup.setChooseContent(tx);
                String type = type1;
                if (!TextUtils.isEmpty(type2)) {
                    type = type + "," + type2;
                }
                if (!TextUtils.isEmpty(type3)) {
                    type = type + "," + type3;
                }
                itemGroup.setSelectValue(type);


            }
        }).build();
        pvOptions.setPicker(options1Items, options2Items, options3Items);
        pvOptions.show();
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

        DatePickDialog dialog = new DatePickDialog(InspectActivity.this);
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