package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.utils.TabUtils;

import org.jetbrains.annotations.NotNull;

public class RiskStaticThirdNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;
    int enable;

    public RiskStaticThirdNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick,int enable) {
        this.onProviderOnClick = onProviderOnClick;
        this.enable = enable;
    }

    @Override
    public int getItemViewType() {
        return 3;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_static_third;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.ChildRisk entity = (RiskSuperviseBean.ChildRisk) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        if (entity.isCheck()==1){

            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check), R.drawable.image_check_circle);
        }else {
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check), R.drawable.image_uncheck_circle);
        }
        if (enable==1){
            TabUtils.setDrawableLeft(getContext(), (TextView) baseViewHolder.getView(R.id.item_check), R.drawable.image_check_enable_circle);
        }
        baseViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onProviderOnClick.onItemOnclick(data,3);
           }
       });

    }
}
