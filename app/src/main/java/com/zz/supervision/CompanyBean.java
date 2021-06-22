package com.zz.supervision;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

/*"data": [{
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "0",
		"companyTypeText": "全部",
		"count": "78"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "1",
		"companyTypeText": "食品",
		"count": "59"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "2",
		"companyTypeText": "冷链",
		"count": "5"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "3",
		"companyTypeText": "药品",
		"count": "5"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "4",
		"companyTypeText": "医疗器械",
		"count": "1"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "5",
		"companyTypeText": "化妆品",
		"count": "4"
	}, {
		"searchValue;
		"createBy;
		"createTime;
		"updateBy;
		"updateTime;
		"remark;
		"params": {},
		"deptId;
		"companyType": "6",
		"companyTypeText": "特种设备",
		"count": "4"
	}]*/
public class CompanyBean implements Serializable {
    private String address;// ,
    private String businessPlace;// ,
    private String businessProject;// ,
    private String businessProjectText;// ,
    private String businessScope;// ,
    private String businessScopeText;// ,
    private String warehouseAddress;// ,
    private String qualityContact;// ,
    private String businessType;// ,
    private String businessTypeText;// ,
    private int alarmStatus;// ,
    private String alarmStatusText;// ,
    private int companyType;// ,
    private String specificType;// ,
    private String specificTypeText;// ,
    private String companyTypeText;// ,
    private String contact;// ,
    private String contactInformation;// ,
    private String createBy;// ,
    private String createTime;// ,
    private String fieldTime;// ,
    private String idNum;// ,
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
    private String alarmStatusColor;//

    private String  longitude;//123.63550307225523,
    private String       latitude;// 47.21882160905167,
    private String       coldstorageType1Text;// 47.21882160905167,
    private String       coldstorageType2Text;// 47.21882160905167,

    private String provinceId;
    private String cityId;
    private String countyId;
    private String provinceName;
    private String cityName;
    private String countyName;
    private String legalPhone;
    private String economicNature;
    private String peopleTotal;
    private String techniciansTotal;

    private String annualOutput;
    private String annualSales;
    private String annualTaxPayment;
    private String annualProfit;
    private String scale;
    private String scaleText;

    private String companyTypeTex;
    private String category;
    private String      categoryText;
    private String     categoryRemark;
    private String     mapAddress;
    private String      operatorNameCertificate;
    private String     socialCreditCodeCertificate;
    private String      legalRepresentativeCertificate;
    private String        addressCertificate;
    private String       businessPlaceCertificate;
    private String      economicNatureCertificate;
    private String        peopleTotalCertificate;
    private String        techniciansTotalCertificate;
    private String     mainProducts;
    private String     recordCoun;
    

    public String getIdNum() {
        return idNum;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public String getProvinceName() {
        if (TextUtils.isEmpty(provinceName))return "";
        return provinceName;
    }

    public String getCityName() {
        if (TextUtils.isEmpty(cityName))return "";
        return cityName;
    }

    public String getCountyName() {
        if (TextUtils.isEmpty(countyName))return "";
        return countyName;
    }

    public int getAlarmStatus() {
        return alarmStatus;
    }

    public String getAlarmStatusColor() {
        return alarmStatusColor;
    }

    public String getAlarmStatusText() {
        return alarmStatusText;
    }

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

    public String getBusinessScope() {
        return businessScope;
    }

    public String getBusinessScopeText() {
        return businessScopeText;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public String getQualityContact() {
        return qualityContact;
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

    public String getLoginName() {
        return loginName;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public String getEconomicNature() {
        return economicNature;
    }

    public String getPeopleTotal() {
        if (peopleTotal==null){
            return "";
        }
        return peopleTotal;
    }

    public String getTechniciansTotal() {
        if (techniciansTotal==null){
            return "";
        }
        return techniciansTotal;
    }

    public String getAnnualOutput() {
        if (annualOutput==null){
            return "";
        }
        return annualOutput;
    }

    public String getAnnualSales() {
        if (annualSales==null){
            return "";
        }
        return annualSales;
    }

    public String getAnnualTaxPayment() {
        if (annualTaxPayment==null){
            return "";
        }
        return annualTaxPayment;
    }

    public String getAnnualProfit() {
        if (annualProfit==null){
            return "";
        }
        return annualProfit;
    }

    public String getScale() {
        return scale;
    }

    public String getScaleText() {
        return scaleText;
    }

    public String getCompanyTypeTex() {
        return companyTypeTex;
    }

    public String getCategory() {
        if (category==null){
            return "";
        }
        return category;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public String getCategoryRemark() {
        return categoryRemark;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public String getOperatorNameCertificate() {

        return operatorNameCertificate;
    }

    public String getSocialCreditCodeCertificate() {
        return socialCreditCodeCertificate;
    }

    public String getLegalRepresentativeCertificate() {
        return legalRepresentativeCertificate;
    }

    public String getAddressCertificate() {
        return addressCertificate;
    }

    public String getBusinessPlaceCertificat() {
        return businessPlaceCertificate;
    }

    public String getEconomicNatureCertificate() {
        return economicNatureCertificate;
    }

    public String getPeopleTotalCertificate() {
        if (peopleTotalCertificate==null){
            return "";
        }
        return peopleTotalCertificate;
    }

    public String getTechniciansTotalCertificate() {
        if (techniciansTotalCertificate==null){
            return "";
        }
        return techniciansTotalCertificate;
    }

    public String getMainProducts() {
        return mainProducts;
    }

    public String getRecordCoun() {
        return recordCoun;
    }
}
