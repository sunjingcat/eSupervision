package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

public class RiskStaticRootNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public RiskStaticRootNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_root;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.RiskItem entity = (RiskSuperviseBean.RiskItem) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.setText(R.id.itemContent, "");
        baseViewHolder.getView(R.id.itemContent).setVisibility(View.GONE);
        ((CheckBox) baseViewHolder.getView(R.id.item_check)).setVisibility(View.GONE);
        baseViewHolder.getView(R.id.item_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 0);
                entity.setCheck(!entity.isCheck());
            }
        });
        baseViewHolder.setImageResource(R.id.image_fold, !entity.isExpanded() ? R.drawable.image_down : R.drawable.image_top);

        baseViewHolder.getView(R.id.ll_itemName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 10);
            }
        });
    }
}
