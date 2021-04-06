package com.zz.supervision.business.equipment;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.supervision.R;
import com.zz.supervision.widget.ItemGroup;

public class BusinessStatus extends FrameLayout {

    private ItemGroup department; //
    private ItemGroup persion; //
    private ItemGroup number; //
    private TextView titleTv; //标题

    public BusinessStatus(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public BusinessStatus(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public BusinessStatus(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }


    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_inspect_edit, null);
        department = (ItemGroup) view.findViewById(R.id.department);
        persion = (ItemGroup) view.findViewById(R.id.persion);
        titleTv = (TextView) view.findViewById(R.id.title);
        number = (ItemGroup) view.findViewById(R.id.number);
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

    public String getDepartment() {
        return department.getValue();
    }

    public String getPersion() {
        return persion.getValue();
    }

    public String getNumber() {
        return number.getValue();
    }

    public String getTitleTv() {
        return titleTv.getText().toString();
    }

    public void setDepartment(String department) {
        this.department.setTitle(department + "");
    }

    public void setPersion(String persion) {
        this.persion.setTitle(persion + "");
    }

    public void setNumber(String number) {
        this.number.setTitle(number + "");
    }

    public void setTitleTv(String titleTv) {
        this.titleTv.setText(titleTv + "");
    }
}