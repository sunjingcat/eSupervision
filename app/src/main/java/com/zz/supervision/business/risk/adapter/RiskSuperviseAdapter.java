package com.zz.supervision.business.risk.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskSuperviseAdapter extends BaseNodeAdapter {
    public RiskSuperviseAdapter(OnProviderOnClick onProviderOnClick) {
        super();
        addNodeProvider(new RiskRootNodeProvider(onProviderOnClick));
        addNodeProvider(new RiskSecondNodeProvider(onProviderOnClick));
        addNodeProvider(new RiskThirdNodeProvider(onProviderOnClick));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof RiskSuperviseBean.RiskItem) {
            return 0;
        } else if (node instanceof RiskSuperviseBean.ChildRisk) {
            return 1;
        }else if (node instanceof RiskSuperviseBean.ChildRisk2) {
            return 2;
        }
        return -1;
    }
    public interface OnProviderOnClick{
        void onItemOnclick(BaseNode node, int type);
    }
}
