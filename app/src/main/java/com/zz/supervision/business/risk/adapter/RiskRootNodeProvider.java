package com.zz.supervision.business.risk.adapter;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.CheckBox;

import androidx.core.content.ContextCompat;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;
import com.zz.supervision.bean.SuperviseBean;
import com.zz.supervision.widget.CenterAlignImageSpan;

import org.jetbrains.annotations.NotNull;

public class RiskRootNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;
    int enable;

    public RiskRootNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick,int enable) {
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
        RiskSuperviseBean.RiskItem entity = (RiskSuperviseBean.RiskItem) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.setText(R.id.itemContent, entity.getItemRemark()+"");
         baseViewHolder.getView(R.id.item_check).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entity.setCheck(!entity.isCheck());
                onProviderOnClick.onItemOnclick(data, 0);

            }
        });

        String showText =entity.getContent();
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

//        baseViewHolder.setImageResource(R.id.image_fold, !entity.isExpanded() ? R.drawable.image_down : R.drawable.image_top);

        baseViewHolder.getView(R.id.ll_itemName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProviderOnClick.onItemOnclick(data, 10);
            }
        });
        ((CheckBox) baseViewHolder.getView(R.id.item_check)).setChecked(entity.isCheck());

        if (enable==1){
            baseViewHolder.getView(R.id.item_check).setVisibility(View.GONE);
        }else {
            baseViewHolder.getView(R.id.item_check).setVisibility(View.VISIBLE);
        }
    }
}
