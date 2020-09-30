package com.zz.supervision.business.risk.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chad.library.adapter.base.entity.node.BaseExpandNode;
import com.chad.library.adapter.base.entity.node.BaseNode;
import com.chad.library.adapter.base.provider.BaseNodeProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.RiskSuperviseBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RiskStaticRootNodeProvider extends BaseNodeProvider {
    RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick;

    public RiskStaticRootNodeProvider(RiskSuperviseAdapter.OnProviderOnClick onProviderOnClick) {
        this.onProviderOnClick = onProviderOnClick;
    }

    @Override
    public int getItemViewType() {
        return 0;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_risk_second;
    }

    @Override
    public void convert(@NotNull BaseViewHolder baseViewHolder, BaseNode data) {
        RadioGroup item_rg = (RadioGroup) baseViewHolder.getView(R.id.item_rg);
        List<RiskSuperviseBean.ChildRisk> childRisks = ((RiskSuperviseBean.RiskItem) data).getChildRisks();
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
//        ((RadioButton) baseViewHolder.getView(R.id.item_check_yes)).setChecked(entity.isCheck());
    }
}
