package com.zz.supervision.business.company.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.CompanyBean;

import java.util.List;

public class CompanyListAdapter extends BaseQuickAdapter<CompanyBean, BaseViewHolder> {
    public CompanyListAdapter(int layoutResId, @Nullable List<CompanyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyBean item) {
//        helper.setText(R.id.text_content,item.getPhone());
    }
}
