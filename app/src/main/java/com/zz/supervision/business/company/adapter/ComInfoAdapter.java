package com.zz.supervision.business.company.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.DetailBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ComInfoAdapter extends BaseQuickAdapter<DetailBean, BaseViewHolder> {
    public ComInfoAdapter(@LayoutRes int layoutResId, @Nullable List<DetailBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final DetailBean item) {
        holder.setText(R.id.et_title,item.getTitle());
        holder.setText(R.id.et_value,item.getContent());
    }
}