package com.zz.supervision.business.equipment.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.bean.EquipmentBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class EquipmentAdapter extends BaseQuickAdapter<EquipmentBean, BaseViewHolder> {
    public EquipmentAdapter(@LayoutRes int layoutResId, @Nullable List<EquipmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final EquipmentBean item) {

    }
}