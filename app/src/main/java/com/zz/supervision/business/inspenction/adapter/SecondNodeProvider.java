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
    OnProviderOnClick onProviderOnClick;
    int enable;
    int type;

    public SecondNodeProvider(OnProviderOnClick onProviderOnClick, int enable, int type) {
        this.onProviderOnClick = onProviderOnClick;
        this.enable = enable;
        this.type = type;
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
        SuperviseBean entity = (SuperviseBean) data;
        baseViewHolder.setText(R.id.itemName, entity.getItemName());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(entity.getIsImportant() == 0 ? View.INVISIBLE : View.VISIBLE);
        baseViewHolder.setTextColor(R.id.itemName, entity.getIsLastNotSatisfy() == 1 ? Color.parseColor("#FFC12A2A") : Color.parseColor("#4A4A4A"));

        if (entity.getIsSatisfy() == 1) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_yes), R.drawable.image_uncheck_circle);
        }
        if (entity.getIsSatisfy() == 2) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_uncheck_circle);
        }

        baseViewHolder.setVisible(R.id.item_check_ignore, type == 1);

        if (entity.getIsSatisfy() == 3) {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_ignore), R.drawable.image_check_circle);
        } else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_ignore), R.drawable.image_uncheck_circle);
        }
        if (enable == 1) {
            if (entity.getIsSatisfy() == 0) {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_ignore).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.VISIBLE);
            } else if (entity.getIsSatisfy() == 1) {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_no), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.VISIBLE);
                baseViewHolder.getView(R.id.item_check_ignore).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.GONE);
            } else {
                TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check_ignore), R.drawable.image_check_enable_circle);
                baseViewHolder.getView(R.id.item_check_yes).setVisibility(View.GONE);
                baseViewHolder.getView(R.id.item_check_ignore).setVisibility(View.VISIBLE);
                baseViewHolder.getView(R.id.item_check_no).setVisibility(View.GONE);
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
        baseViewHolder.getView(R.id.item_check_ignore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 3);
            }
        });
    }
}
