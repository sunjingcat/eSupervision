package com.zz.supervision.business.inspenction.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.utils.TabUtils;

import org.jetbrains.annotations.NotNull;

public class SecondNodeProvider extends BaseNodeProvider {
    SuperviseAdapter.OnProviderOnClick onProviderOnClick;
    int enable;

    public SecondNodeProvider(SuperviseAdapter.OnProviderOnClick onProviderOnClick, int enable) {
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
        SuperviseBean.Children entity = (SuperviseBean.Children) data;
        baseViewHolder.setText(R.id.itemName, entity.getItemName());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(entity.getIsImportant() == 0 ? View.INVISIBLE : View.VISIBLE);
        baseViewHolder.setBackgroundColor(R.id.itemName,((SuperviseBean.Children) data).getIsLastNotSatisfy()==1? Color.parseColor("#FFFCC2BF"):Color.parseColor("#ffffff"));

        if (((SuperviseBean.Children) data).getIsSatisfy() == 1) {

            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_check_circle);

        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_uncheck_circle);
        }

        if (((SuperviseBean.Children) data).getIsSatisfy() == 2) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_uncheck_circle);
        }
        if (enable == 1) {
            if (((SuperviseBean.Children) data).getIsSatisfy() == 0) {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.VISIBLE);
            } else {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.VISIBLE);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.GONE);
            }

        }
        if (enable == 1) {

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
