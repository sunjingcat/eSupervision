package com.zz.supervision.business.inspenction.adapter;


import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.widget.CenterAlignImageSpan;

import org.jetbrains.annotations.NotNull;

public class PromptNodeProvider extends BaseNodeProvider {


    public PromptNodeProvider() {
    }

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_prompt;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        SuperviseBean entity = (SuperviseBean) data;
        baseViewHolder.setText(R.id.text, entity.getItemName());

    }
}
