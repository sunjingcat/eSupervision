package com.zz.supervision.business.inspenction.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.risk.adapter.RiskFooterNodeProvider;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperviseAdapter extends BaseNodeAdapter {
    public SuperviseAdapter(OnProviderOnClick onProviderOnClick, int enable, int type) {
        super();
        addNodeProvider(new RootNodeProvider(onProviderOnClick, enable));
        addNodeProvider(new SecondNodeProvider(onProviderOnClick, enable,type));
//        addNodeProvider(new ThrirdNodeProvider(onProviderOnClick,enable));
        addFooterNodeProvider(new FooterNodeProvider(onProviderOnClick));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof SuperviseBean) {
            if (((SuperviseBean) node).getChildType() == 0) {
                return 1;
            } else {
                return 0;
            }
        }
        else if (node instanceof SuperviseBean.RootFooterNode) {
            return 2;
        }
        return -1;
    }
}
