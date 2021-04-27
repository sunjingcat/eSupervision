package com.zz.supervision.business.equipment.adapter;

import android.graphics.Color;
import android.text.TextUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.EquipmentBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class AccessoryAdapter extends BaseQuickAdapter<AccessoryBean, BaseViewHolder> {
    public AccessoryAdapter(@LayoutRes int layoutResId, @Nullable List<AccessoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final AccessoryBean item) {
        holder.setText(R.id.item_title, item.getAccessoryName() +"");
        holder.setText(R.id.item_content, item.getAccessoryTypeText()+"" );

    }
}