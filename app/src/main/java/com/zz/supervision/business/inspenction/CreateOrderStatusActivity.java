package com.zz.supervision.business.inspenction;

import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.codbking.widget.utils.UIAdjuster;
import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.lib.commonlib.widget.SelectPopupWindows;
import com.zz.lib.core.ui.mvp.BasePresenter;
import com.zz.lib.core.utils.LoadingUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.SyxhRectificationOrder;
import com.zz.supervision.business.equipment.AddPipePartActivity;
import com.zz.supervision.business.record.ShowDocActivity;
import com.zz.supervision.net.ApiService;
import com.zz.supervision.net.JsonT;
import com.zz.supervision.net.RequestObserver;
import com.zz.supervision.net.RxNetUtils;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zz.supervision.business.BusinessUtils.getRecordTypeByType;
import static com.zz.supervision.net.RxNetUtils.getApi;

/**
 *
 */
public class CreateOrderStatusActivity extends MyBaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_illegalActivity)
    EditText igIllegalActivity;
    @BindView(R.id.et_lawContent)
    EditText igLawContent;
    @BindView(R.id.et_accordContent)
    EditText igAccordContent;
    @BindView(R.id.et_reformMeasure)
    EditText igReformMeasure;
    @BindView(R.id.ig_reformTimeType)
    ItemGroup ig_reformTimeType;
    @BindView(R.id.ig_reformTime)
    ItemGroup ig_reformTime;
    @BindView(R.id.ig_contact)
    ItemGroup ig_contact;
    @BindView(R.id.ig_contactInformation)
    ItemGroup ig_contactInformation;
    @BindView(R.id.bg)
    LinearLayout bg;
    String recordId;
    @BindView(R.id.bt_ok)
    Button btOk;
    int type;
    String recordType;

    @Override
    protected int getContentView() {
        return R.layout.activity_order_status;

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        recordId = getIntent().getStringExtra("id");
        type = getIntent().getIntExtra("type", 0);
        getData();
        getDicts("reform_time_type");
        ig_reformTimeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelectPopWindow1(ig_reformTimeType, reformTimeType);
            }
        }); ig_reformTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_reformTime);
            }
        });
    }

    @OnClick({R.id.toolbar_subtitle, R.id.bt_ok, R.id.bt_print})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_subtitle:
                finish();
                break;
            case R.id.bt_ok:
//                completeData();
                break;
            case R.id.bt_print:
                postData();
                break;

        }
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
    }

    void postData() {
        Map<String, Object> map = new HashMap<>();
        map.put("illegalActivity", getText(igIllegalActivity));
        map.put("lawContent", getText(igLawContent));
        map.put("accordContent", getText(igAccordContent));
        map.put("reformMeasure", getText(igReformMeasure));
        map.put("reformTimeType", ig_reformTimeType.getSelectValue());
        map.put("reformTime", getText(ig_reformTime));
        map.put("contact", getText(ig_contact));
        map.put("contactInformation", getText(ig_contactInformation));
        RxNetUtils.request(getApi(ApiService.class).syxhRectificationOrder(getRecordTypeByType(type), recordId, map), new RequestObserver<JsonT>(this) {
            @Override
            protected void onSuccess(JsonT jsonT) {
                startActivity(new Intent(CreateOrderStatusActivity.this, ShowDocActivity.class).putExtra("id", recordId).putExtra("type", type).putExtra("tinspectSheetType", 3).putExtra("tinspectType", type).putExtra("read", 1));

            }

            @Override
            protected void onFail2(JsonT userInfoJsonT) {
                super.onFail2(userInfoJsonT);
                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }

    List<BusinessType> reformTimeType = new ArrayList<>();

    public void getDicts(String type) {
        Map<String, Object> params = new HashMap<>();
        params.put("dictType", type);
        RxNetUtils.request(getApi(ApiService.class).getDicts(params), new RequestObserver<JsonT<List<BusinessType>>>(this) {
            @Override
            protected void onSuccess(JsonT<List<BusinessType>> jsonT) {
                reformTimeType.clear();
                reformTimeType.addAll(jsonT.getData());
            }

            @Override
            protected void onFail2(JsonT<List<BusinessType>> stringJsonT) {
                super.onFail2(stringJsonT);
            }
        }, new LoadingUtils().build(this));
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
                if (values[index].equals("0")) {
                    ig_reformTime.setVisibility(View.VISIBLE);
                } else {
                    ig_reformTime.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancel() {
                selectPopupWindows.dismiss();
            }
        });
    }

    void getData() {
        RxNetUtils.request(getApi(ApiService.class).getSyxhRectificationOrder(getRecordTypeByType(type), recordId), new RequestObserver<JsonT<SyxhRectificationOrder>>(this) {
            @Override
            protected void onSuccess(JsonT<SyxhRectificationOrder> jsonT) {
                SyxhRectificationOrder data = jsonT.getData();
                igIllegalActivity.setText(data.getIllegalActivity() + "");
                igLawContent.setText(data.getIllegalActivity() + "");
                igAccordContent.setText(data.getIllegalActivity() + "");
                igReformMeasure.setText(data.getIllegalActivity() + "");
                ig_reformTimeType.setChooseContent(data.getReformTimeTypeText(), data.getReformTimeType());
                ig_reformTime.setChooseContent(data.getReformTime() + "");
                if (data.getReformTimeType().equals("0")) {
                    ig_reformTime.setVisibility(View.VISIBLE);
                } else {
                    ig_reformTime.setVisibility(View.GONE);
                }
                ig_contact.setChooseContent(data.getContact() + "");
                ig_contactInformation.setChooseContent(data.getContactInformation() + "");
            }

            @Override
            protected void onFail2(JsonT<SyxhRectificationOrder> userInfoJsonT) {
                super.onFail2(userInfoJsonT);
//                showToast(userInfoJsonT.getMessage());
            }
        }, LoadingUtils.build(this));
    }
    private void selectTime(ItemGroup itemGroup) {

        DatePickDialog dialog = new DatePickDialog(CreateOrderStatusActivity.this);
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

                itemGroup.setChooseContent(time,time);

            }
        });
        dialog.show();
    }
}