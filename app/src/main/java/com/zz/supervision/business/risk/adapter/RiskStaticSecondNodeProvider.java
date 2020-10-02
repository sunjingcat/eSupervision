package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

public class RiskStaticSecondNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public RiskStaticSecondNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_static_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.ChildRisk entity = (RiskSuperviseBean.ChildRisk) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(entity.getIsKey()==0?View.INVISIBLE:View.VISIBLE);
    }
}
