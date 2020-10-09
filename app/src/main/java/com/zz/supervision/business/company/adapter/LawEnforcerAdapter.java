package com.zz.supervision.business.company.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.LawEnforcerBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LawEnforcerAdapter extends BaseQuickAdapter<LawEnforcerBean, BaseViewHolder> {
    public LawEnforcerAdapter(int layoutResId, @Nullable List<LawEnforcerBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, LawEnforcerBean lawEnforcerBean) {
        if (lawEnforcerBean.isSelect()) {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.parseColor("#eeeeee"));
        } else {
            baseViewHolder.setBackgroundColor(R.id.item_bg, Color.WHITE);
        }
        baseViewHolder.setText(R.id.item_title, lawEnforcerBean.getName() + "");
    }
}
