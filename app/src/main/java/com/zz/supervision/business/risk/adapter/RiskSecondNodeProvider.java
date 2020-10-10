package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.utils.TabUtils;

import org.jetbrains.annotations.NotNull;

public class RiskSecondNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;
    int enable;

    public RiskSecondNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick, int enable) {
        this.onProviderOnClick = onProviderOnClick;
        this.enable = enable;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.ChildRisk entity = (RiskSuperviseBean.ChildRisk) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(entity.getIsKey() == 0 ? View.INVISIBLE : View.VISIBLE);
        if (((RiskSuperviseBean.ChildRisk) data).isCheck() == 1) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_uncheck_circle);
        }

        if (((RiskSuperviseBean.ChildRisk) data).isCheck() == 2) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_uncheck_circle);
        }
        if (enable == 1) {
            if (((RiskSuperviseBean.ChildRisk) data).isCheck() == 1) {
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.VISIBLE);
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_check_enable_circle);
            }else {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.VISIBLE);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.GONE);
            }
        }
        baseViewHolder.getView(R.id.item_check_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 1);
            }
        });
        baseViewHolder.getView(R.id.item_check_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 2);
            }
        });
    }
}
