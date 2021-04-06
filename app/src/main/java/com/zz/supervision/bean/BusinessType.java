package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BusinessType implements Parcelable {
  private String  searchValue;// null,
    private String        createBy;// admin,
    private String       createTime;// 2020-09-11 10;//44;//36,
    private String       updateBy;// null,
    private String       updateTime;// null,
    private String      remark;// null,
    private Object      params;// {},
    private String       deptId;// null,
    private int      dictCode;// 137,
    private int   dictSort;// 1,
    private String       dictLabel;// 食品销售经营者,
    private String    dictValue;// 1,
    private String    dictType;// business_type,
    private String    cssClass;// null,
    private String    listClass;// null,
    private String    isDefault;// Y,
    private int    status;// 0,
    boolean isSelect;

    protected BusinessType(Parcel in) {
        searchValue = in.readString();
        createBy = in.readString();
        createTime = in.readString();
        updateBy = in.readString();
        updateTime = in.readString();
        remark = in.readString();
        deptId = in.readString();
        dictCode = in.readInt();
        dictSort = in.readInt();
        dictLabel = in.readString();
        dictValue = in.readString();
        dictType = in.readString();
        cssClass = in.readString();
        listClass = in.readString();
        isDefault = in.readString();
        status = in.readInt();
        isSelect = in.readByte() != 0;
    }

    public static final Creator<BusinessType> CREATOR = new Creator<BusinessType>() {
        @Override
        public BusinessType createFromParcel(Parcel in) {
            return new BusinessType(in);
        }

        @Override
        public BusinessType[] newArray(int size) {
            return new BusinessType[size];
        }
    };

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getDictValue() {
        return dictValue;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public Object getParams() {
        return params;
    }

    public String getDeptId() {
        return deptId;
    }

    public int getDictCode() {
        return dictCode;
    }

    public int getDictSort() {
        return dictSort;
    }

    public String getDictLabel() {
        return dictLabel;
    }



    public String getDictType() {
        return dictType;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String getListClass() {
        return listClass;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(searchValue);
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeString(updateBy);
        dest.writeString(updateTime);
        dest.writeString(remark);
        dest.writeString(deptId);
        dest.writeInt(dictCode);
        dest.writeInt(dictSort);
        dest.writeString(dictLabel);
        dest.writeString(dictValue);
        dest.writeString(dictType);
        dest.writeString(cssClass);
        dest.writeString(listClass);
        dest.writeString(isDefault);
        dest.writeInt(status);
        dest.writeByte((byte) (isSelect ? 1 : 0));
    }
}
