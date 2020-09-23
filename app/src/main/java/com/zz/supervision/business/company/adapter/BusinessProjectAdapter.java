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
        baseViewHolder.setText(R.id.title,businessProjectBean.getValue()+" "+businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onHeaderClick(v,baseViewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, BusinessProjectBean businessProjectBean) {
        baseViewHolder.setText(R.id.title,businessProjectBean.getValue()+" "+businessProjectBean.getTitle());
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBnpjClickListener.onContentClick(v,baseViewHolder.getAdapterPosition());
            }
        });
        if (businessProjectBean.isSelect()){
            baseViewHolder.setTextColor(R.id.title, Color.GRAY);
        }else {
            baseViewHolder.setTextColor(R.id.title, R.color.colorAccent);
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
