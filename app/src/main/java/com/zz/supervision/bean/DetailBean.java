package com.zz.supervision.bean;

public class DetailBean {
    String title;
    String content;
    boolean haveTop;

    public DetailBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public DetailBean(String title, String content, boolean haveTop) {
        this.title = title;
        this.content = content;
        this.haveTop = haveTop;
    }

    public boolean isHaveTop() {
        return haveTop;
    }

    public void setHaveTop(boolean haveTop) {
        this.haveTop = haveTop;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
