package com.zz.supervision.business.equipment.adapter;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.EquipmentBean;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class EquipmentAdapter extends BaseQuickAdapter<EquipmentBean, BaseViewHolder> {
    public EquipmentAdapter(@LayoutRes int layoutResId, @Nullable List<EquipmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final EquipmentBean item) {
        holder.setText(R.id.item_title, item.getRegistNumber()+"" );
        holder.setText(R.id.tv_alarmStatus, item.getAlarmStatusText() + "");
        if (!TextUtils.isEmpty(item.getAlarmStatusColor())) {
            try {
                holder.setTextColor(R.id.tv_alarmStatus, Color.parseColor(item.getAlarmStatusColor() + ""));
            }catch (Exception e){

            }

        }
        holder.setText(R.id.item_use_status, item.getUsageStatusText() + "");
        holder.setText(R.id.item_reg_time, item.getRegistCode() + "");
        if (item.isSelect()) {
            holder.setBackgroundColor(R.id.content, Color.parseColor("#eeeeee"));
        } else {
            holder.setBackgroundColor(R.id.content, Color.WHITE);
        }
    }
}