package com.zz.supervision.business.record.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.CompanyBean;
import com.zz.supervision.R;
import com.zz.supervision.bean.RecordBean;
import com.zz.supervision.bean.SuperviseBean;

import java.util.List;

public class CheckListAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    public CheckListAdapter(int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.mc_item_delete);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
        helper.setText(R.id.tv_company, item.getOperatorName()+"");
        helper.setText(R.id.tv_licenseNumber, "许可证号：" + item.getLicenseNumber());
        helper.setText(R.id.tv_yearCount, "" + item.getYearCount());
        helper.setText(R.id.tv_inspectionResult, "" + item.getInspectionResult());
        helper.setText(R.id.tv_inspectionTime, "检查日期：" + item.getInspectionTime());
        helper.setText(R.id.tv_status, "" + item.getStatusText());
        helper.setText(R.id.tv_lawEnforcerName, "" + item.getLawEnforcer1Name()+item.getLawEnforcer2Name()+"");
    }
}
