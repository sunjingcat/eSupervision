package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.chad.library.adapter.base.entity.JSectionEntity;

import java.util.ArrayList;

public class BusinessProjectBean extends JSectionEntity implements Parcelable {
    private boolean isHeader;
    String title;
    String value;
    String fatherValue;
    boolean isSelect;

    protected BusinessProjectBean(Parcel in) {
        isHeader = in.readByte() != 0;
        title = in.readString();
        value = in.readString();
        fatherValue = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<BusinessProjectBean> CREATOR = new Creator<BusinessProjectBean>() {
        @Override
        public BusinessProjectBean createFromParcel(Parcel in) {
            return new BusinessProjectBean(in);
        }

        @Override
        public BusinessProjectBean[] newArray(int size) {
            return new BusinessProjectBean[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public BusinessProjectBean(boolean isHeader, String title, String value, String fatherValue) {
        this.isHeader = isHeader;
        this.title = title;
        this.value = value;
        this.fatherValue = fatherValue;
    }

    public String getFatherValue() {
        return fatherValue;
    }

    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean isHeader() {
        return isHeader;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isHeader ? 1 : 0));
        dest.writeString(title);
        dest.writeString(value);
        dest.writeString(fatherValue);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
