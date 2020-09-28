package com.zz.supervision.business.record.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.bean.SuperviseBean;

import java.util.List;

public class CheckListAdapter extends BaseQuickAdapter<SuperviseBean.ResposeBean, BaseViewHolder> {
    public CheckListAdapter(int layoutResId, @Nullable List<SuperviseBean.ResposeBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.mc_item_delete);
    }

    @Override
    protected void convert(BaseViewHolder helper, SuperviseBean.ResposeBean item) {
        helper.setText(R.id.item_title, item.getCompanyInfo().getOperatorName());
        helper.setText(R.id.item_legalRepresentative, "法人代表：" + item.getLawEnforcer1Name());
        helper.setText(R.id.item_businessType, "主体业态：" + item.getDeptName());
    }
}
