package com.zz.supervision.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.lib.commonlib.utils.DensityUtils;
import com.zz.supervision.R;

public class ItemArea extends FrameLayout {
    private TextView titleTv; //标题
    private EditText contentEdt; //输入框
    private OnClickListener itemOnClickListener; //Item的点击事件

    public ItemArea(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ItemArea(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public ItemArea(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    public void setOnClickListener(OnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_area, null);
        titleTv = (TextView) view.findViewById(R.id.tv);
        contentEdt = (EditText) view.findViewById(R.id.et);
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

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroup);
        String title = typedArray.getString(R.styleable.ItemGroup_title);
        float paddingLeft = typedArray.getDimension(R.styleable.ItemGroup_paddingLeft, 15);
        float paddingRight = typedArray.getDimension(R.styleable.ItemGroup_paddingRight, 15);
        float paddingTop = typedArray.getDimension(R.styleable.ItemGroup_paddingTop, 5);
        float paddingBottom = typedArray.getDimension(R.styleable.ItemGroup_paddingTop, 5);
        float titleSize = typedArray.getDimension(R.styleable.ItemGroup_title_size, 15);
        int titleColor = typedArray.getColor(R.styleable.ItemGroup_title_color, defaultTitleColor);
        String content = typedArray.getString(R.styleable.ItemGroup_edt_content);
        float contentSize = typedArray.getDimension(R.styleable.ItemGroup_edt_text_size, 13);
        int contentColor = typedArray.getColor(R.styleable.ItemGroup_edt_text_color, defaultEdtColor);
        String hintContent = typedArray.getString(R.styleable.ItemGroup_edt_hint_content);
        int hintColor = typedArray.getColor(R.styleable.ItemGroup_edt_hint_text_color, defaultHintColor);
        boolean isSelect = typedArray.getBoolean(R.styleable.ItemGroup_isSelect, false);
        String inputType = typedArray.getString(R.styleable.ItemGroup_inputType);
        //默认输入框可以编辑
        boolean isEditable = typedArray.getBoolean(R.styleable.ItemGroup_isEditable, true);
        typedArray.recycle();

        //设置数据
        //设置item的内边距
//        itemGroupLayout.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        titleTv.setText(title);
        titleTv.setTextSize(titleSize);
        titleTv.setTextColor(titleColor);
        if (!TextUtils.isEmpty(inputType)) {
            if (inputType.equals("numberDecimal")) {
                contentEdt.setInputType(8194);
            }
        }
        contentEdt.setText(content);
        contentEdt.setTextSize(contentSize);
        contentEdt.setTextColor(contentColor);
        contentEdt.setHint(TextUtils.isEmpty(hintContent) ? isSelect ? "请选择" : "请填写" : hintContent);
        contentEdt.setHintTextColor(hintColor);
        contentEdt.setFocusable(isEditable); //设置输入框是否可以编辑
        contentEdt.setLongClickable(false); //输入框不允许长按
        contentEdt.setVisibility(!isSelect ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
    }

    public void setTitle(String titleTv) {
        this.titleTv.setText(titleTv + "");
    }

    public void setChooseContent(String choose) {

        this.contentEdt.setText(choose + "");


    }

    public String getValue() {
        return contentEdt.getText().toString();

    }


}