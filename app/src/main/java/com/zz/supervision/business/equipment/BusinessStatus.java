package com.zz.supervision.business.equipment;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.zz.supervision.R;
import com.zz.supervision.utils.TimeUtils;
import com.zz.supervision.widget.ItemGroup;

import java.util.Date;

public class BusinessStatus extends FrameLayout {

    private ItemGroup ig_organizationalUnit; //
    private ItemGroup ig_checker; //
    private ItemGroup ig_reportId; //
    private ItemGroup ig_lastCheckDate; //
    private ItemGroup ig_checkDate; //
    private ItemGroup ig_nextCheckDate; //
    private ItemGroup ig_checkReduction; //
    private ItemGroup ig_deviceProblem; //
    private ItemGroup ig_manageProblem; //
    private TextView titleTv; //标题
    private OnClickListener onClickListener;

    public BusinessStatus(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BusinessStatus(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    @Override
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public BusinessStatus(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }


    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inspect_edit, null);
        ig_organizationalUnit = (ItemGroup) view.findViewById(R.id.ig_organizationalUnit);
        ig_checker = (ItemGroup) view.findViewById(R.id.ig_checker);
        titleTv = (TextView) view.findViewById(R.id.title);
        ig_reportId = (ItemGroup) view.findViewById(R.id.ig_reportId);
        ig_lastCheckDate = (ItemGroup) view.findViewById(R.id.ig_lastCheckDate);
        ig_checkDate = (ItemGroup) view.findViewById(R.id.ig_checkDate);
        ig_nextCheckDate = (ItemGroup) view.findViewById(R.id.ig_nextCheckDate);
        ig_checkReduction = (ItemGroup) view.findViewById(R.id.ig_checkReduction);
        ig_deviceProblem = (ItemGroup) view.findViewById(R.id.ig_deviceProblem);
        ig_manageProblem = (ItemGroup) view.findViewById(R.id.ig_manageProblem);
        ig_lastCheckDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_lastCheckDate);
            }
        });
        ig_checkDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_checkDate);
            }
        });
        ig_nextCheckDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(ig_nextCheckDate);
            }
        });
        ig_checkReduction.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(ig_checkReduction);
            }
        });
        addView(view); //把自定义的这个组合控件的布局加入到当前FramLayout
    }

    /**
     * 初始化相关属性，引入相关属性
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        //标题的默认字体颜色
        int defaultTitleColor = context.getResources().getColor(R.color.colorTextBlack33);
        //输入框的默认字体颜色
        int defaultEdtColor = context.getResources().getColor(R.color.colorTextBlack33);
        //输入框的默认的提示内容的字体颜色
        int defaultHintColor = context.getResources().getColor(R.color.colorTextBlack99);

    }

    public String getOrganizationalUnit() {
        return ig_organizationalUnit.getValue();
    }

    public String getChecker() {
        return ig_checker.getValue();
    }

    public String getReportId() {
        return ig_reportId.getValue();
    }

    public String getTitleTv() {
        return titleTv.getText().toString();
    }

    public void setOrganizationalUnit(String organizationalUnit) {
        this.ig_organizationalUnit.setTitle(organizationalUnit + "");
    }

    public String getLastCheckDate() {
        return ig_lastCheckDate.getValue();
    }

    public void setLastCheckDate(String lastCheckDate) {
        this.ig_lastCheckDate.setChooseContent(lastCheckDate);
    }

    public String getIg_checkDate() {
        return ig_checkDate.getValue();
    }

    public void setICheckDate(String checkDate) {
        this.ig_checkDate.setChooseContent(checkDate);
        ;
    }

    public String getNextCheckDate() {
        return ig_nextCheckDate.getValue();
    }

    public void setNextCheckDate(String nextCheckDate) {
        this.ig_nextCheckDate.setChooseContent(nextCheckDate);
    }

    public String getCheckReduction() {
        return ig_checkReduction.getValue();
    }

    public void setCheckReduction(String checkReduction) {
        this.ig_checkReduction.setChooseContent(checkReduction);
    }

    public String getDeviceProblem() {
        return ig_deviceProblem.getValue();
    }

    public void setDeviceProblem(String deviceProblem) {
        this.ig_deviceProblem.setChooseContent(deviceProblem);
    }

    public String getManageProblem() {
        return ig_manageProblem.getValue();
    }

    public void setManageProblem(String manageProblem) {
        this.ig_manageProblem.setChooseContent(manageProblem);
    }

    public void setChecker(String checker) {
        this.ig_checker.setTitle(checker + "");
    }

    public void setReportId(String reportId) {
        this.ig_reportId.setTitle(reportId + "");
    }

    public void setTitleTv(String titleTv) {
        this.titleTv.setText(titleTv + "");
    }

    private void selectTime(ItemGroup itemGroup) {

        DatePickDialog dialog = new DatePickDialog(getContext());
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