package com.zz.supervision.widget;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MenuAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final String item) {
        holder.setText(R.id.item_title,item+"");
    }
}