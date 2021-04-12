package com.zz.supervision.business.equipment.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.OrganizationBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OrganizationAdapter extends BaseQuickAdapter<OrganizationBean, BaseViewHolder> {
    public OrganizationAdapter(int layoutResId, @Nullable List<OrganizationBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OrganizationBean item) {
        if (item.isSelect()) {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.parseColor("#eeeeee"));
        } else {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.WHITE);
        }
        baseViewHolder.setText(R.id.item_title, item.getOperatorName() + "");
    }
}
