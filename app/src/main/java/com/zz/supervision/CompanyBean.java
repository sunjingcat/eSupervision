package com.zz.supervision;

import android.os.Parcel;
import android.os.Parcelable;

public class CompanyBean implements Parcelable {
    private String address;// ,
    private String businessPlace;// ,
    private String businessProject;// ,
    private String businessProjectText;// ,
    private String businessType;// ,
    private String businessTypeText;// ,
    private String companyType;// ,
    private String contact;// ,
    private String contactInformation;// ,
    private String createBy;// ,
    private String createTime;// ,
    private String fieldTime;// ,
    private String id;// 0,
    private String legalRepresentative;// ,
    private String licenseNumber;// ,
    private String operatorName;// ,
    private Object params;// {},
    private String remark;// ,
    private String searchValue;// ,
    private String socialCreditCode;// ,
    private String updateBy;// ,
    private String updateTime;// ,
    private String validDate;//

    protected CompanyBean(Parcel in) {
        address = in.readString();
        businessPlace = in.readString();
        businessProject = in.readString();
        businessProjectText = in.readString();
        businessType = in.readString();
        companyType = in.readString();
        contact = in.readString();
        contactInformation = in.readString();
        createBy = in.readString();
        createTime = in.readString();
        fieldTime = in.readString();
        id = in.readString();
        legalRepresentative = in.readString();
        licenseNumber = in.readString();
        operatorName = in.readString();
        remark = in.readString();
        searchValue = in.readString();
        socialCreditCode = in.readString();
        updateBy = in.readString();
        updateTime = in.readString();
        validDate = in.readString();
    }

    public static final Creator<CompanyBean> CREATOR = new Creator<CompanyBean>() {
        @Override
        public CompanyBean createFromParcel(Parcel in) {
            return new CompanyBean(in);
        }

        @Override
        public CompanyBean[] newArray(int size) {
            return new CompanyBean[size];
        }
    };

    public String getBusinessTypeText() {
        return businessTypeText;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public String getBusinessProject() {
        return businessProject;
    }

    public String getBusinessProjectText() {
        return businessProjectText;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getCompanyType() {
        return companyType;
    }

    public String getContact() {
        return contact;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getFieldTime() {
        return fieldTime;
    }

    public String getId() {
        return id;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public Object getParams() {
        return params;
    }

    public String getRemark() {
        return remark;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getValidDate() {
        return validDate;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(businessPlace);
        dest.writeString(businessProject);
        dest.writeString(businessProjectText);
        dest.writeString(businessType);
        dest.writeString(companyType);
        dest.writeString(contact);
        dest.writeString(contactInformation);
        dest.writeString(createBy);
        dest.writeString(createTime);
        dest.writeString(fieldTime);
        dest.writeString(id);
        dest.writeString(legalRepresentative);
        dest.writeString(licenseNumber);
        dest.writeString(operatorName);
        dest.writeString(remark);
        dest.writeString(searchValue);
        dest.writeString(socialCreditCode);
        dest.writeString(updateBy);
        dest.writeString(updateTime);
        dest.writeString(validDate);
    }
}
