package com.zz.supervision.business.equipment.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.OrganizationBean;
import com.zz.supervision.bean.PressurePipePart;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PipeAdapter extends BaseQuickAdapter<PressurePipePart, BaseViewHolder> {
    public PipeAdapter(int layoutResId, @Nullable List<PressurePipePart> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PressurePipePart item) {

//        baseViewHolder.setText(R.id.item_title, item.getOperatorName() + "");
    }
}
