package com.zz.supervision.business.inspenction.adapter;
import com.chad.library.adapter.base.BaseNodeAdapter;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.zz.supervision.bean.ScoreBean;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScoreAdapter extends BaseNodeAdapter {
    public ScoreAdapter() {
        super();

        // 需要占满一行的，使用此方法（例如section）
        addFullSpanNodeProvider(new RootNodeProvider());
        addFullSpanNodeProvider(new SecondNodeProvider());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseNode> list, int i) {
        BaseNode node = list.get(i);
        if (node instanceof ScoreBean) {
            return 0;
        } else if (node instanceof ScoreBean.ChildrenItem) {
            return 1;
        }
        return -1;
    }
}
