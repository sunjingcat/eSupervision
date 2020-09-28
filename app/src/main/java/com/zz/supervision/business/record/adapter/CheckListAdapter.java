package com.zz.supervision.business.record.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;

import java.util.List;

public class CheckListAdapter extends BaseQuickAdapter<CompanyBean, BaseViewHolder> {
    public CheckListAdapter(int layoutResId, @Nullable List<CompanyBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.mc_item_delete);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyBean item) {
        helper.setText(R.id.item_title, item.getOperatorName());
        helper.setText(R.id.item_legalRepresentative, "法人代表：" + item.getLegalRepresentative());
        helper.setText(R.id.item_businessType, "主体业态：" + item.getBusinessType());
    }
}