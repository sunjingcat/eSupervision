package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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
        return R.layout.item_risk_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        // 数据类型需要自己强转
        RiskSuperviseBean.ChildRisk entity = (RiskSuperviseBean.ChildRisk) data;
        baseViewHolder.setText(R.id.itemName, entity.getContent());
        baseViewHolder.getView(R.id.item_isImportant).setVisibility(View.GONE);
        RadioGroup item_rg = (RadioGroup) baseViewHolder.getView(R.id.item_rg);
        List<RiskSuperviseBean.ChildRisk2> childRisks = ((RiskSuperviseBean.ChildRisk) data).getChildRisks();
        for (int i = 0; i < childRisks.size(); i++) {
            //radioButton
            RadioButton radioButton = new RadioButton(context);
            int dimension = (int) (getContext().getResources().getDimension(R.dimen.dp_10)+0.5f);//会自动转化为像素值
            radioButton.setPadding(dimension,0,0,0);
            radioButton.setButtonDrawable(R.drawable.check_item);
            radioButton.setText(childRisks.get(i).getContent());
            radioButton.setTextColor(getContext().getResources().getColor(R.color.white));
            //必须有ID，否则默认选中的选项会一直是选中状态
            radioButton.setId(i);
            if (i==childRisks.size()-1){
                //默认选中
                radioButton.setChecked(true);
            }

            //layoutParams 设置margin值
            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i!=0){
                int i1 = (int) (getContext().getResources().getDimension(R.dimen.dp_40) + 0.5f);
                layoutParams.setMargins(i1,0,0,0);
            }else {
                layoutParams.setMargins(0,0,0,0);
            }
            //注意这里addView()里传入layoutParams
            item_rg.addView(radioButton,layoutParams);
        }
        ((RadioButton) baseViewHolder.getView(R.id.item_check_yes)).setChecked(entity.isCheck());
    }
}
