package com.zz.supervision.business.equipment;

import android.content.Intent;
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
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BeforeAddDeviceCheck;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.DictBean;
import com.zz.supervision.bean.ImageBack;
import com.zz.supervision.bean.PressurePipePart;
import com.zz.supervision.business.company.adapter.ImageDeleteItemAdapter;
import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.business.equipment.mvp.presenter.PipeAddPresenter;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class AddPipePartActivity extends MyBaseActivity<Contract.IsetPipeAddPresenter> implements Contract.IGetPipeAddView, View.OnClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbar_subtitle;
    @BindView(R.id.ig_manufacturer)
    ItemGroup ig_manufacturer;

    @BindView(R.id.ig_pipeNumber)
    ItemGroup ig_pipeNumber;
    @BindView(R.id.ig_pipeName)
    ItemGroup ig_pipeName;
    @BindView(R.id.ig_manufacturerDate)
    ItemGroup ig_manufacturerDate;
    @BindView(R.id.ig_installationCompany)
    ItemGroup ig_installationCompany;
    @BindView(R.id.ig_completionDate)
    ItemGroup ig_completionDate;
    @BindView(R.id.ig_totalLength)
    ItemGroup ig_totalLength;

    List<BeforeAddDeviceCheck> beforeAddDeviceChecks = new ArrayList<>();
    List<BusinessStatus> businessStatuses = new ArrayList<>();
    List<BusinessType> list_check_status = new ArrayList<>();
    List<BusinessType> list_check_nature = new ArrayList<>();
    ArrayList<ImageBack> images = new ArrayList<>();
    ArrayList<String> localPath = new ArrayList<>();
    ImageDeleteItemAdapter adapter;
    @BindView(R.id.item_rv_images)
    RecyclerView itemRvImages;
    String deviceId;
    @Override
    protected int getContentView() {
        return R.layout.activity_pipe_part_add;

    }

    @Override
    public Contract.IsetPipeAddPresenter initPresenter() {
        return new PipeAddPresenter(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        deviceId = getIntent().getStringExtra("deviceId");
        if (!TextUtils.isEmpty(deviceId)) {
            mPresenter.getData(deviceId);
        }
        mPresenter.getDicts("tzsb_check_status");
        mPresenter.getDicts("tzsb_check_nature");
        mPresenter.getOrganizationalUnit();
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
                        .start(AddPipePartActivity.this, 1101); // 打开相册
            }

            @Override
            public void onclickDelete(View v, int option) {
                images.remove(option);
                adapter.notifyDataSetChanged();
            }
        });
    }
    void postData(){

    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar,1);
    }
    void initClick() {
        toolbar_subtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
        ig_completionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_completionDate);
            }
        });
        ig_manufacturerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_manufacturerDate);
            }
        });
        ig_manufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(AddPipePartActivity.this, OrganizationListActivity.class).putExtra("url", "tzsbCheckOrganizationList"), 2001);
            }
        });

    }
    List<DictBean> options1Items = new ArrayList<>();
    List<List<DictBean>> options2Items = new ArrayList<>();
    List<List<List<DictBean>>> options3Items = new ArrayList<>();
    String type1;
    String type2;
    String type3;
    void showOptionsPicker(ItemGroup itemGroup) {
        UIAdjuster.closeKeyBoard(AddPipePartActivity.this);
        OptionsPickerView pvOptions = new OptionsPickerBuilder(AddPipePartActivity.this, new OnOptionsSelectListener() {
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

        DatePickDialog dialog = new DatePickDialog(AddPipePartActivity.this);
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

    @Override
    public void showPipeInfo(PressurePipePart data) {

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
    public void showOrganizationalUnit(List<DictBean> list) {

    }

    @Override
    public void showImage(List<ImageBack> list) {

    }

    @Override
    public void showBeforeAddDeviceCheck(List<BeforeAddDeviceCheck> list) {

    }
}