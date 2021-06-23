package com.zz.supervision.business.company.adapter;

import android.graphics.Color;

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
        if (item.isSelect()){
            holder.setBackgroundColor(R.id.item_bg, Color.parseColor("#eeeeee"));
        }
        holder.setText(R.id.item_title, item.getName() +"");
        holder.setText(R.id.item_isImported, item.getProductionSituationText()+"" );
        holder.setText(R.id.item_category, item.getCategory()+"" );
        holder.setText(R.id.item_Spec, item.getVarietySpecModel()+"" );

    }
}