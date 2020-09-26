package com.zz.supervision.business.inspenction.adapter;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;

import org.jetbrains.annotations.NotNull;

public class RootNodeProvider extends BaseNodeProvider {
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
        SuperviseBean entity = (SuperviseBean) data;
        baseViewHolder.setText(R.id.itemName, entity.getItemName());
    }
}
