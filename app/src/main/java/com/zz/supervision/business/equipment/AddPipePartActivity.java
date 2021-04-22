package com.zz.supervision.business.equipment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;

import com.zz.lib.commonlib.utils.ToolBarUtils;
import com.zz.supervision.R;
import com.zz.supervision.base.MyBaseActivity;

import com.zz.supervision.bean.PipePartBean;

import com.zz.supervision.business.equipment.mvp.Contract;
import com.zz.supervision.business.equipment.mvp.presenter.PipeAddPresenter;

import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import androidx.appcompat.widget.Toolbar;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    String deviceId;
    String id;

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
        id = getIntent().getStringExtra("id");
        if (!TextUtils.isEmpty(id)) {
            mPresenter.getData(id);
        }
        initClick();
    }

    void postData() {
        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(id)){
            map.put("id", id);
        }
        map.put("deviceId", deviceId);
        map.put("manufacturerName", getText(ig_manufacturer));
        map.put("pipeNumber", getText(ig_pipeNumber));
        map.put("pipeName", getText(ig_pipeName));
        map.put("manufacturerDate", ig_manufacturerDate.getValue());
        map.put("completionDate", ig_completionDate.getValue());
        map.put("installationCompany", getText(ig_installationCompany));
        map.put("totalLength", getText(ig_totalLength));

        mPresenter.submitData(map);
    }

    @Override
    protected void initToolBar() {
        ToolBarUtils.getInstance().setNavigation(toolbar, 1);
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
    public void showPipeInfo(PipePartBean data) {

        ig_manufacturer.setChooseContent(data.getManufacturerName() + "");
        ig_pipeNumber.setChooseContent(data.getPipeNumber() + "");
        ig_pipeName.setChooseContent(data.getPipeName() + "");
        ig_manufacturerDate.setChooseContent(data.getManufacturerDate() + "");
        ig_completionDate.setChooseContent(data.getCompletionDate() + "");
        ig_installationCompany.setChooseContent(data.getInstallationCompany() + "");
        ig_totalLength.setChooseContent(data.getTotalLength() + "");
    }

    @Override
    public void showSubmitResult(String id) {
        finish();

        showToast("提交成功");
    }


}