package com.zz.supervision.business.risk.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskStaticSuperviseAdapter extends BaseNodeAdapter {
    public RiskStaticSuperviseAdapter(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        super();
        addFullSpanNodeProvider(new RiskStaticRootNodeProvider(onProviderOnClick));
//        addFullSpanNodeProvider(new RiskStaticSecondNodeProvider(onProviderOnClick));
//        addFooterNodeProvider(new RiskFooterNodeProvider(onProviderOnClick));

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof RiskSuperviseBean.RiskItem) {
            return 0;
        }
        return -1;
    }

}
