package com.zz.supervision.bean;

public class InspectEdit {
    String title;
    Content content;

    public InspectEdit(String title) {
        this.title = title;
    }

    class Content{
        String other;
        String cus;

        public String getOther() {
            return other;
        }

        public String getCus() {
            return cus;
        }

        public void setOther(String other) {
            this.other = other;
        }

        public void setCus(String cus) {
            this.cus = cus;
        }
    }
}
