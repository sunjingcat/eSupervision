package com.zz.supervision.business.inspenction.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;

import org.jetbrains.annotations.NotNull;

public class RootNodeProvider extends BaseNodeProvider {
    SuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public RootNodeProvider(SuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

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
        baseViewHolder.setText(R.id.itemContent, entity.getItemRemark());
        baseViewHolder.getView(R.id.item_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SuperviseBean) data).setCheck(!((SuperviseBean) data).isCheck());
                onProviderOnClick.onItemOnclick(data, 0);
            }
        });
        ((CheckBox) baseViewHolder.getView(R.id.item_check)).setChecked(entity.isCheck());
        baseViewHolder.setImageResource(R.id.image_fold, !((BaseExpandNode) data).isExpanded() ? R.drawable.image_down : R.drawable.image_top);
//        baseViewHolder.getView(R.id.image_fold).setVisibility(data.getChildNode() != null && data.getChildNode().size() > 0 ? View.VISIBLE : View.INVISIBLE);
        baseViewHolder.getView(R.id.image_fold).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().expandOrCollapse(baseViewHolder.getAdapterPosition());
            }
        });
    }
}
