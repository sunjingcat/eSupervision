package com.zz.supervision.business.record.adapter;

import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RecordBean;

import java.util.List;

import androidx.annotation.Nullable;

public class CheckListAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    public CheckListAdapter(int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecordBean item) {
        helper.setText(R.id.tv_company, item.getOperatorName() + "");
        if (item.getType()>=11&&item.getType()<=18){
            helper.setText(R.id.tv_licenseNumber, "社会信用代码：" + item.getSocialCreditCode());
        }else {
            helper.setText(R.id.tv_licenseNumber, "许可证号：" + item.getLicenseNumber());
        }
        if (item.getType() == 3 || item.getType() == 4) {
            helper.getView(R.id.ll_yearCount).setVisibility(View.GONE);
            helper.setText(R.id.tv_inspectionResult, "" + item.getLevel());
            helper.setText(R.id.tv_inspectionResult_tv, "风险等级");
        } else {
            if (item.getType() >= 6 &&item.getType() <= 18) {
                helper.getView(R.id.ll_yearCount).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_inspectionResult, "" + item.getProblemCount());
                helper.setText(R.id.tv_inspectionResult_tv, "问题项数");
            }else {
                helper.getView(R.id.ll_yearCount).setVisibility(View.VISIBLE);
                helper.setText(R.id.tv_inspectionResult, "" + item.getInspectionResultText());
                helper.setText(R.id.tv_inspectionResult_tv, "检查结果");
            }
        }
        helper.setText(R.id.tv_yearCount, "" + item.getYearCount());

        helper.setText(R.id.tv_inspectionTime, "检查日期：" + item.getInspectionTime());
        helper.setText(R.id.tv_status, "" + item.getStatusText());
        if (item.getStatus() == 3) {
            helper.setTextColor(R.id.tv_status, Color.parseColor("#929292"));
        }
        if (item.getStatus() == 2) {
            helper.setTextColor(R.id.tv_status, Color.parseColor("#ECAE36"));
        }
        if (item.getStatus() == 1) {
            helper.setTextColor(R.id.tv_status, Color.parseColor("#C52A2A"));
        }
        helper.setText(R.id.tv_lawEnforcerName, "" + item.getLawEnforcer1Name() +","+ item.getLawEnforcer2Name() + "");
    }
}
