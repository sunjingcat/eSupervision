package com.zz.supervision;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

public class CompanyBean implements Serializable {
    private String address;// ,
    private String businessPlace;// ,
    private String businessProject;// ,
    private String businessProjectText;// ,
    private String businessType;// ,
    private String businessTypeText;// ,
    private int companyType;// ,
    private String specificType;// ,
    private String specificTypeText;// ,
    private String companyTypeText;// ,
    private String contact;// ,
    private String contactInformation;// ,
    private String createBy;// ,
    private String createTime;// ,
    private String fieldTime;// ,
    private String id;// 0,
    private String legalRepresentative;// ,
    private String licenseNumber;// ,
    private String operatorName;// ,
    private String operatorFullName;// ,
    private Object params;// {},
    private String remark;// ,
    private String searchValue;// ,
    private String recordCount;// ,
    private String socialCreditCode;// ,
    private String updateBy;// ,
    private String updateTime;// ,
    private String validDate;//
    private String loginName;//
    private String  longitude;//123.63550307225523,
    private String       latitude;// 47.21882160905167,
    private String       coldstorageType1Text;// 47.21882160905167,
    private String       coldstorageType2Text;// 47.21882160905167,

    public String getColdstorageType1Text() {
        return coldstorageType1Text;
    }

    public String getColdstorageType2Text() {
        return coldstorageType2Text;
    }

    public String getLoginAccount() {
        return loginName;
    }

    public String getSpecificTypeText() {
        return specificTypeText;
    }

    public String getSpecificType() {
        return specificType;
    }

    public String getRecordCount() {
        return recordCount;
    }

    public Double getLongitude() {
        if (TextUtils.isEmpty(longitude)){
            return 0.0;
        }else {
            return Double.parseDouble(longitude);
        }
    }

    public String getOperatorFullName() {
        return operatorFullName;
    }

    public Double getLatitude() {
        if (TextUtils.isEmpty(latitude)){
            return 0.0;
        }else {
            return Double.parseDouble(latitude);
        }
    }

    public String getCompanyTypeText() {
        return companyTypeText;
    }

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

    public int getCompanyType() {
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


}
