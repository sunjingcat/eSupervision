package com.zz.supervision.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.zz.supervision.R;

public class TabUtils {
    public static void setTabs(TabLayout tabLayout, LayoutInflater inflater, String[] tabs) {
        for (int i = 0; i < tabs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.view_tab_top_item, null);
            tab.setCustomView(view);
            TextView tvTitle = view.findViewById(R.id.text);
            tvTitle.setText(tabs[i]);
            if (i == 0) {
                tvTitle.setTextSize(16);
            }
            tabLayout.addTab(tab);
        }

    }

    public static void setTabSize(TabLayout.Tab tab, int size) {
        View view = tab.getCustomView();
        TextView tvTitle = view.findViewById(R.id.text);
        if (null != tvTitle && tvTitle instanceof TextView) {
            tvTitle.setTextSize(size);
        }

    }

    public static void setTabLeft( TabLayout.Tab tab, LayoutInflater inflater, String tabs) {
        View view = inflater.inflate(R.layout.view_tab_record_left, null);
        tab.setCustomView(view);
    }

    public static void setTabRight(TabLayout.Tab tab, LayoutInflater inflater, String tabs) {

//        TabLayout.Tab tab = tabLayout.newTab();
        View view = inflater.inflate(R.layout.view_tab_record_right, null);
        tab.setCustomView(view);

//        tabLayout.addTab(tab);


    }
    public static void setDrawableLeft(Context context,TextView attention, int drawableId) {
        Drawable drawable = context.getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        attention.setCompoundDrawables(drawable, null, null, null);
    }
}
