package com.zz.supervision.business.inspenction.adapter;


import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.bean.DetailBean;


import java.util.List;

/**
 * Created by ASUS on 2018/10/10.
 */

public class DetailAdapter extends BaseQuickAdapter<DetailBean, BaseViewHolder> {

    public DetailAdapter(@LayoutRes int layoutResId, @Nullable List<DetailBean> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder holper, final DetailBean item) {
        holper.setText(R.id.item_title,item.getTitle());
        holper.setText(R.id.item_content,item.getContent());

    }

}