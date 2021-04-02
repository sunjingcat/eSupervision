package com.zz.supervision.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zz.supervision.R;

public class ItemGroup extends FrameLayout {

    private LinearLayout itemGroupLayout; //组合控件的布局
    private TextView titleTv; //标题
    private TextView chooseTv; //标题
    private EditText contentEdt; //输入框
    private ItemOnClickListener itemOnClickListener; //Item的点击事件

    public ItemGroup(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public ItemGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public ItemGroup(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
    }

    //初始化View
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_edit, null);
        itemGroupLayout = (LinearLayout) view.findViewById(R.id.ll_view_container);
        titleTv = (TextView) view.findViewById(R.id.tv_choose_item_name);
        chooseTv = (TextView) view.findViewById(R.id.tv_choose_item_choose);
        contentEdt = (EditText) view.findViewById(R.id.et_choose_item_choose);
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
        //默认输入框可以编辑
        boolean isEditable = typedArray.getBoolean(R.styleable.ItemGroup_isEditable, true);
        typedArray.recycle();

        //设置数据
        //设置item的内边距
//        itemGroupLayout.setPadding((int) paddingLeft, (int) paddingTop, (int) paddingRight, (int) paddingBottom);
        titleTv.setText(title);
        titleTv.setTextSize(titleSize);
        titleTv.setTextColor(titleColor);

        contentEdt.setText(content);
        contentEdt.setTextSize(contentSize);
        contentEdt.setTextColor(contentColor);
        contentEdt.setHint(hintContent);
        contentEdt.setHintTextColor(hintColor);
        contentEdt.setFocusableInTouchMode(isEditable); //设置输入框是否可以编辑
        contentEdt.setLongClickable(false); //输入框不允许长按
        chooseTv.setVisibility(isSelect ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
        contentEdt.setVisibility(!isSelect ? View.VISIBLE : View.GONE);  //设置向右的箭头图标是否可见
        chooseTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemOnClickListener.onClickRight(v);
            }
        });
    }

    public String getValue() {
        return contentEdt.getText().toString();
    }

    public interface ItemOnClickListener {
        void onClickRight(View view);
    }
}