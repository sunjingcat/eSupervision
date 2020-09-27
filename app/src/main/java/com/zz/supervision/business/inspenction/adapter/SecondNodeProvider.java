package com.zz.supervision.business.inspenction.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.ScoreBean;
import com.zz.supervision.bean.SuperviseBean;

import org.jetbrains.annotations.NotNull;

public class SecondNodeProvider extends BaseNodeProvider {
    SuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public SecondNodeProvider(SuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

    @Override
    public int getItemViewType() {
        return 1;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        SuperviseBean.Children entity = (SuperviseBean.Children) data;
        baseViewHolder.setText(R.id.itemName, entity.getItemName());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(entity.getIsImportant() == 0 ? View.GONE : View.VISIBLE);
        ((RadioGroup) baseViewHolder.getView(R.id.item_rg)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.item_check_yes:
                        ((SuperviseBean.Children) data).setCheck(true);
                        break;
                    case R.id.item_check_no:
                        ((SuperviseBean.Children) data).setCheck(false);
                        break;
                }
            }
        });
        ((RadioButton) baseViewHolder.getView(R.id.item_check_yes)).setChecked(entity.isCheck());
    }
}
