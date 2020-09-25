package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LawEnforcerBean implements Parcelable {
    String id;
    String name;
    boolean isSelect;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    protected LawEnforcerBean(Parcel in) {
        id = in.readString();
        name = in.readString();
        isSelect = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LawEnforcerBean> CREATOR = new Creator<LawEnforcerBean>() {
        @Override
        public LawEnforcerBean createFromParcel(Parcel in) {
            return new LawEnforcerBean(in);
        }

        @Override
        public LawEnforcerBean[] newArray(int size) {
            return new LawEnforcerBean[size];
        }
    };
}
