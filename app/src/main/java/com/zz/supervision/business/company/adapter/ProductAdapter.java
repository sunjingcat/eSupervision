package com.zz.supervision.business.company.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.AccessoryBean;
import com.zz.supervision.bean.ProductBean;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public ProductAdapter(@LayoutRes int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final ProductBean item) {
//        holder.setText(R.id.item_title, item.getAccessoryName() +"");
//        holder.setText(R.id.item_content, item.getAccessoryTypeText()+"" );

    }
}