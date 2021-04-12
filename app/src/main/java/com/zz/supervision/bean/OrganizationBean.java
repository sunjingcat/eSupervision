package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class OrganizationBean implements Parcelable {
    String id;// 1,
    String operatorName;// 注册机构,
    String organizationCode;// null,
    String address;// null,
    String contact;// null,
    String contactInformation;// null
    boolean isSelect;// null


    protected OrganizationBean(Parcel in) {
        id = in.readString();
        operatorName = in.readString();
        organizationCode = in.readString();
        address = in.readString();
        contact = in.readString();
        contactInformation = in.readString();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<OrganizationBean> CREATOR = new Creator<OrganizationBean>() {
        @Override
        public OrganizationBean createFromParcel(Parcel in) {
            return new OrganizationBean(in);
        }

        @Override
        public OrganizationBean[] newArray(int size) {
            return new OrganizationBean[size];
        }
    };

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public String getId() {
        return id;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public String getAddress() {
        return address;
    }

    public String getContact() {
        return contact;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(operatorName);
        dest.writeString(organizationCode);
        dest.writeString(address);
        dest.writeString(contact);
        dest.writeString(contactInformation);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
