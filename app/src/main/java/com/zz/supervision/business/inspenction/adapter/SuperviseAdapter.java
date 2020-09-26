package com.zz.supervision.business.inspenction.adapter;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperviseAdapter extends BaseNodeAdapter {
    public SuperviseAdapter() {
        super();

        // 需要占满一行的，使用此方法（例如section）
        addFullSpanNodeProvider(new RootNodeProvider());
        addFullSpanNodeProvider(new SecondNodeProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof SuperviseBean) {
            return 0;
        } else if (node instanceof SuperviseBean.Children) {
            return 1;
        }
        return -1;
    }
}
