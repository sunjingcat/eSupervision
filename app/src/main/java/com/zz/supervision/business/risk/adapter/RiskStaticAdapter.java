package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.DetailBean;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RiskStaticAdapter extends BaseQuickAdapter<RiskSuperviseBean.RiskItem, BaseViewHolder> {


    public RiskStaticAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RiskSuperviseBean.RiskItem riskItem) {
        // 数据类型需要自己强转
        RiskSuperviseBean.RiskItem entity = riskItem;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(View.GONE);
        RadioGroup item_rg = (RadioGroup) baseViewHolder.getView(R.id.item_rg);
        List<RiskSuperviseBean.ChildRisk> childRisks = entity.getChildRisks();
        for (int i = 0; i < childRisks.size(); i++) {
            //radioButton
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setPadding(10, 15, 0, 0);
            radioButton.setButtonDrawable(R.drawable.check_item);
            radioButton.setText(childRisks.get(i).getContent());
            radioButton.setTextColor(getContext().getResources().getColor(R.color.colorTextBlack33));
            //必须有ID，否则默认选中的选项会一直是选中状态
            radioButton.setId(i);
            if (i == childRisks.size() - 1) {
                //默认选中
                radioButton.setChecked(true);
                childRisks.get(i).setCheck(true);
            }
            //注意这里addView()里传入layoutParams
            item_rg.addView(radioButton, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }
}
