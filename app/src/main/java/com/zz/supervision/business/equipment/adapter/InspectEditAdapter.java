package com.zz.supervision.business.equipment.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.bean.InspectEdit;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class InspectEditAdapter extends BaseQuickAdapter<InspectEdit, BaseViewHolder> {
    public InspectEditAdapter(@LayoutRes int layoutResId, @Nullable List<InspectEdit> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final InspectEdit item) {

    }
}