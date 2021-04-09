package com.zz.supervision.business.equipment;


import android.view.View;
import android.widget.Button;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.EquipmentBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.business.equipment.mvp.presenter.EquipmentAddPresenter;
import com.zz.supervision.utils.LogUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 设备添加
 */
public class AddEquipmentActivity extends MyBaseActivity<Contract.IsetEquipmentAddPresenter> implements Contract.IGetEquipmentAddView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.ig_deviceCode)
    ItemGroup ig_deviceCode;
    @BindView(R.id.ig_deviceModel)
    ItemGroup ig_deviceModel;
    @BindView(R.id.ig_deviceType1)
    ItemGroup ig_deviceType1;
    @BindView(R.id.ig_deviceType2)
    ItemGroup ig_deviceType2;
    @BindView(R.id.ig_deviceType3)
    ItemGroup ig_deviceType3;
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
    @BindView(R.id.bt_ok)
    Button bt_ok;
    String deviceType1;
    String deviceType2;
    String deviceType3;

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
        ig_deviceCode.setOnClickListener(this);
        ig_deviceType1.setOnClickListener(this);
        bt_ok.setOnClickListener(this);
        mPresenter.getDeviceType();
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
            case R.id.ig_deviceType1:
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
                        ig_deviceType1.setChooseContent(tx);
                        deviceType1 = options1Items.get(options1).getDictValue();
                        deviceType2 = options2Items.get(options1).get(option2).getDictValue();
                    }
                }).build();
                pvOptions.setPicker(options1Items, options2Items, options3Items);
                pvOptions.show();
                LogUtils.v("-----");

                break;
        }

    }

    private void postData() {
        Map<String, Object> params = new HashMap<>();
        params.put("deviceCode", getText(ig_deviceCode));
        params.put("deviceModel", getText(ig_deviceModel));

        mPresenter.submitData(params);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar);
    }

    @Override
    public void showEquipmentInfo(EquipmentBean data) {

    }

    @Override
    public void showSubmitResult(String id) {

    }

    @Override
    public void showResult() {

    }

    @Override
    public void showPostImage(int position, String id) {

    }

    @Override
    public void showDicts(String type, List<BusinessType> list) {

    }

    @Override
    public void showDeviceType(List<DictBean> list) {

        if (list == null) return;

        for (DictBean categoryBean : list) {
            options1Items.add( categoryBean);
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

    }
}