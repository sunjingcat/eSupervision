package com.zz.supervision.business.inspenction.adapter;

import android.view.View;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.business.risk.adapter.RiskSuperviseAdapter;

import org.jetbrains.annotations.NotNull;

public class FooterNodeProvider extends BaseNodeProvider {
    SuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public FooterNodeProvider(SuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

    @Override
    public int getItemViewType() {
        return 2;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_food;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.RootFooterNode entity = (RiskSuperviseBean.RootFooterNode) data;
        baseViewHolder.setImageResource(R.id.image_fold, !entity.isExpanded() ? R.drawable.image_down : R.drawable.image_top);
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RiskSuperviseBean.RootFooterNode) data).setExpanded(!((RiskSuperviseBean.RootFooterNode) data).isExpanded());
                onProviderOnClick.onItemOnclick(data,0);
            }
        });
    }
}
