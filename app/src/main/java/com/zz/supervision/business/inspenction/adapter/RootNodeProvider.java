package com.zz.supervision.business.inspenction.adapter;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.widget.CenterAlignImageSpan;

import org.jetbrains.annotations.NotNull;

public class RootNodeProvider extends BaseNodeProvider {
    OnProviderOnClick onProviderOnClick;
    int enable;

    public RootNodeProvider(OnProviderOnClick onProviderOnClick, int enable) {
        this.onProviderOnClick = onProviderOnClick;
        this.enable = enable;
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

        baseViewHolder.setText(R.id.itemContent, entity.getItemRemark());
        baseViewHolder.getView(R.id.item_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SuperviseBean) data).setCheck(!((SuperviseBean) data).isCheck());
                onProviderOnClick.onItemOnclick(data, 0);
            }
        });

        String showText = entity.getItemName();
//注意此处showText后+ " "主要是为了占位
        SpannableString ss = new SpannableString(showText + "  ");
        int len = ss.length();
        //图片
        Drawable d = ContextCompat.getDrawable(getContext(), !entity.isExpanded() ? R.drawable.image_down : R.drawable.image_top);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        //构建ImageSpan
        CenterAlignImageSpan imageSpan = new CenterAlignImageSpan(d);
        ss.setSpan(imageSpan, len - 1, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        baseViewHolder.setText(R.id.itemName, ss);
        TextView itemName = baseViewHolder.getView(R.id.itemName);
        switch (entity.getItemDegree()) {
            case 2:
                itemName.setTextSize(16);
                break;
            case 3:
                itemName.setTextSize(15);
                break;
            case 4:
                itemName.setTextSize(14);
                break;
            default:
                itemName.setTextSize(17);
                break;
        }

        baseViewHolder.getView(R.id.ll_itemName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 10);
            }
        });
        baseViewHolder.setTextColor(R.id.itemName, entity.getIsLastNotSatisfy() == 1 ? Color.parseColor("#FFC12A2A") : Color.parseColor("#4A4A4A"));

//         baseViewHolder.getView(R.id.item_check).setEnabled(((SuperviseBean) data).isExpanded());

        ((CheckBox) baseViewHolder.getView(R.id.item_check)).setChecked(entity.isCheck());
        if (enable == 1) {
            baseViewHolder.getView(R.id.item_check).setVisibility(View.GONE);
        } else {
            baseViewHolder.getView(R.id.item_check).setVisibility(View.VISIBLE);
        }
    }
}
