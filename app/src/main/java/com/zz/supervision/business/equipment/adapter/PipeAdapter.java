package com.zz.supervision.business.equipment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.PipePartBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PipeAdapter extends BaseQuickAdapter<PipePartBean, BaseViewHolder> {
    public PipeAdapter(int layoutResId, @Nullable List<PipePartBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, PipePartBean item) {

        baseViewHolder.setText(R.id.item_title, item.getPipeName() + "");
        baseViewHolder.setText(R.id.item_number, "管道编号："+item.getPipeNumber() + "");
        baseViewHolder.setText(R.id.item_status, "管道长度："+item.getTotalLength() + "");
    }
}
