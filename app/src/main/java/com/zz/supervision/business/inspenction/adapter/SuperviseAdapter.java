package com.zz.supervision.business.inspenction.adapter;

import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.SuperviseBean;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperviseAdapter extends BaseNodeAdapter {
    public SuperviseAdapter(OnProviderOnClick onProviderOnClick, int enable, int type) {
        super();
        addNodeProvider(new RootNodeProvider(onProviderOnClick, enable));
        addNodeProvider(new SecondNodeProvider(onProviderOnClick, enable,type));
        addNodeProvider(new PromptNodeProvider());
        addFooterNodeProvider(new FooterNodeProvider(onProviderOnClick));
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof SuperviseBean) {
            if (((SuperviseBean) node).getChildType() == 0) {
                return 1;
            } else if (((SuperviseBean) node).getChildType() == 2) {
                return 3;
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
