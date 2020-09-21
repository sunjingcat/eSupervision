package com.zz.supervision.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollBean implements SectionEntity {
    boolean isHeader;
    String header;
    ScrollItemBean scrollItemBean;
    public ScrollBean(boolean isHeader, String header) {
        this.isHeader = isHeader;
        this.header = header;
    }

    public ScrollBean(ScrollItemBean bean) {
        scrollItemBean = bean;
    }

    public ScrollItemBean getScrollItemBean() {
        return scrollItemBean;
    }

    public String getHeader() {
        return header;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class ScrollItemBean {
        private String text;
        private String type;

        public ScrollItemBean(String text, String type) {
            this.text = text;
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
