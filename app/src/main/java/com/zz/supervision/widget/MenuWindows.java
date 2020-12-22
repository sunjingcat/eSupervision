package com.zz.supervision.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.zz.supervision.R;
import com.zz.supervision.bean.BusinessType;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 2017-09-25.
 */

public class MenuWindows extends PopupWindow {

    private static final String TAG = "FinishProjectPopupWindows";
    private ArrayList<BusinessType> PLANETS ;
    public Button btnCancelProject, btnOkProject;
    private View mView;
    private RecyclerView rv_menu;
    private OnItemClickListener listener;

    public MenuWindows(final Activity context, ArrayList<BusinessType> strings
                              ) {
        super(context);


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.menu_popupwindow, null);
        PLANETS = strings;
        rv_menu = (RecyclerView) mView.findViewById(R.id.rv_menu);
        rv_menu.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        rv_menu.setLayoutManager(new LinearLayoutManager(context));
        MenuAdapter wheelAdapter = new MenuAdapter(R.layout.item_menu,PLANETS);
        rv_menu.setAdapter(wheelAdapter);

        wheelAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                int currentItem = position;
                listener.onSelected(currentItem,PLANETS.get(position));
                dismiss();
            }
        });
        //设置PopupWindow的View
        this.setContentView(mView);
        //设置PopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
//        设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        backgroundAlpha(context, 0.5f);
        this.setFocusable(true);



    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onSelected(int index, BusinessType msg);

        void onCancel();
    }

}
