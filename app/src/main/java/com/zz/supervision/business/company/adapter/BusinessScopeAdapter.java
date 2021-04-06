package com.zz.supervision.business.company.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.BusinessType;
import com.zz.supervision.bean.LawEnforcerBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BusinessScopeAdapter extends BaseQuickAdapter<BusinessType, BaseViewHolder> {
    public BusinessScopeAdapter(int layoutResId, @Nullable List<BusinessType> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BusinessType lawEnforcerBean) {
        if (lawEnforcerBean.isSelect()) {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.parseColor("#eeeeee"));
        } else {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.WHITE);
        }
        baseViewHolder.setText(R.id.item_title, lawEnforcerBean.getDictLabel() + "");
    }
}
