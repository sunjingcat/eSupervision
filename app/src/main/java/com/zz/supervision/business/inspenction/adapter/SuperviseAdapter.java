package com.zz.supervision.business.inspenction.adapter;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.business.risk.adapter.RiskFooterNodeProvider;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperviseAdapter extends BaseNodeAdapter {
    public SuperviseAdapter(OnProviderOnClick onProviderOnClick) {
        super();
        addNodeProvider(new RootNodeProvider(onProviderOnClick));
        addNodeProvider(new SecondNodeProvider(onProviderOnClick));
        addFooterNodeProvider(new FooterNodeProvider(onProviderOnClick));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof SuperviseBean) {
            return 0;
        } else if (node instanceof SuperviseBean.Children) {
            return 1;
        }else if (node instanceof SuperviseBean.Children) {
            return 1;
        }
        return -1;
    }
    public interface OnProviderOnClick{
        void onItemOnclick(BaseNode node,int type);
    }
}
