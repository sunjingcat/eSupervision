package com.zz.supervision.business.risk.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskStaticAdapter extends BaseNodeAdapter {

    public RiskStaticAdapter(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick,int enable ) {
        super();
        addFullSpanNodeProvider(new RiskStaticRootNodeProvider(onProviderOnClick));
        addFullSpanNodeProvider(new RiskStaticSecondNodeProvider(onProviderOnClick));
        addFullSpanNodeProvider(new RiskStaticThirdNodeProvider(onProviderOnClick,enable));
        addFooterNodeProvider(new RiskFooterNodeProvider(onProviderOnClick));

    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof RiskSuperviseBean.RiskItem) {
            return 0;
        } else if (node instanceof RiskSuperviseBean.ChildRisk&&((RiskSuperviseBean.ChildRisk)node).getChildType()==2) {
            return 1;
        }else if (node instanceof RiskSuperviseBean.ChildRisk&&((RiskSuperviseBean.ChildRisk)node).getChildType()==1) {
            return 1;
        }else if (node instanceof RiskSuperviseBean.ChildRisk&&((RiskSuperviseBean.ChildRisk)node).getChildType()==0) {
            return 3;
        }else if (node instanceof RiskSuperviseBean.RootFooterNode) {
            return 2;
        }
        return -1;
    }


    public interface OnProviderOnClick {
        void onItemOnclick(BaseNode node, int type);

    }
}
