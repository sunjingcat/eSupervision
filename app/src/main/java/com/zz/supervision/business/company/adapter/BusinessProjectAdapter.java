package com.zz.supervision.business.company.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.BusinessProjectBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BusinessProjectAdapter extends BaseSectionQuickAdapter<BusinessProjectBean, BaseViewHolder> {
   private OnBnpjClickListener onBnpjClickListener;

    public BusinessProjectAdapter(int sectionHeadResId, int layoutResId, @Nullable List<BusinessProjectBean> data) {
        super(sectionHeadResId, layoutResId, data);
    }

    public void setOnBnpjClickListener(OnBnpjClickListener onBnpjClickListener) {
        this.onBnpjClickListener = onBnpjClickListener;
    }

    @Override
    protected void convertHeader(@NotNull BaseViewHolder baseViewHolder, @NotNull BusinessProjectBean businessProjectBean) {
        baseViewHolder.setText(R.id.title,businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onHeaderClick(v,baseViewHolder.getAdapterPosition());
            }
        });
        if (businessProjectBean.isSelect()){
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#0851A2"));
        }else {
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#999999"));
        }
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BusinessProjectBean businessProjectBean) {
        baseViewHolder.setText(R.id.title,businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onContentClick(v,baseViewHolder.getAdapterPosition());
            }
        });
        if (businessProjectBean.isSelect()){
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#0851A2"));
            baseViewHolder.setBackgroundResource(R.id.title, R.drawable.border_radius_3_dae);
        }else {
            baseViewHolder.setTextColor(R.id.title, Color.parseColor("#4A4A4A"));
            baseViewHolder.setBackgroundResource(R.id.title, R.drawable.border_radius_3_gray);
        }
    }

    public interface OnBnpjClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onHeaderClick(View v,int p);
        void onContentClick(View v,int p);
    }
}
