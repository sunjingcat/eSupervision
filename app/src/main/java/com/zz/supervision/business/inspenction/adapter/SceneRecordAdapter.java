package com.zz.supervision.business.inspenction.adapter;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.ProductBean;
import com.zz.supervision.bean.SceneRecord;

import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class SceneRecordAdapter extends BaseQuickAdapter<SceneRecord, BaseViewHolder> {
    public SceneRecordAdapter(@LayoutRes int layoutResId, @Nullable List<SceneRecord> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final SceneRecord item) {
        helper.setText(R.id.tv_company, item.getOperatorName() + "");
        if (item.getType()>=11&&item.getType()<=18){
            helper.setText(R.id.tv_licenseNumber, "社会信用代码：" + item.getSocialCreditCode());
        }else {
            helper.setText(R.id.tv_licenseNumber, "许可证号：" + item.getLicenseNumber());
        }
        helper.getView(R.id.ll_result_reduction).setVisibility(View.GONE);

        helper.setText(R.id.tv_yearCount, "" + item.getYearCount());

        helper.setText(R.id.tv_inspectionTime, "检查日期：" + item.getInspectionTime());
        helper.setText(R.id.tv_status, "" + item.getStatusText());

        helper.setText(R.id.tv_lawEnforcerName, "" + item.getLawEnforcer1Name() +","+ item.getLawEnforcer2Name() + "");
    }

}