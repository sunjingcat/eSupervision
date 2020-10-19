package com.zz.supervision.business.company.adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zz.supervision.R;
import com.zz.supervision.utils.BASE64;
import com.zz.supervision.utils.GlideUtils;
import com.zz.supervision.utils.ImagePreview;


import java.util.List;

/**
 * Created by ASUS on 2018/9/26.
 */

public class ImageItemAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImageItemAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final String item) {
        GlideUtils.loadImage(getContext(), item, (ImageView) holder.getView(R.id.image));

        holder.getView(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = item;
//                Bitmap s1 = GlideUtils.base64ToBitmap(str);
//                String s = BASE64.saveBitmap(getContext(),s1);
                ImagePreview.preview(getContext(), str);
            }
        });
    }
}
