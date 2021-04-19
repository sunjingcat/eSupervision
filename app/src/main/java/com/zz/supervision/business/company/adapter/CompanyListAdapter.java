package com.zz.supervision.business.company.adapter;

import android.graphics.Color;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;

import java.util.List;

import androidx.annotation.Nullable;

public class CompanyListAdapter extends BaseQuickAdapter<CompanyBean, BaseViewHolder> {
    public CompanyListAdapter(int layoutResId, @Nullable List<CompanyBean> data) {
        super(layoutResId, data);
//        addChildClickViewIds(R.id.mc_item_delete);
        addChildClickViewIds(R.id.content);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyBean item) {
        helper.setText(R.id.item_title, item.getOperatorName());
        helper.setText(R.id.item_legalRepresentative, "法人代表：" + item.getLegalRepresentative());
        helper.setText(R.id.item_businessType, "检查次数：" + item.getRecordCount());
        helper.setVisible(R.id.tv_alarmStatus,item.getCompanyType()==6);
        helper.setText(R.id.tv_alarmStatus, item.getAlarmStatusText()+"");
        if (!TextUtils.isEmpty(item.getAlarmStatusColor())){
            helper.setTextColor(R.id.tv_alarmStatus, Color.parseColor(item.getAlarmStatusColor()+""));
        }

    }
}
