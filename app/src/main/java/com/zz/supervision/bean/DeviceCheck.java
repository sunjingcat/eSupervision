package com.zz.supervision.bean;

import java.util.List;

public class DeviceCheck {
    String id;// 4,
    String deviceId;// 1,
    String partId;// 22,
    String inspectionOrganizationId;// 1,
    String inspectionOrganizationName;// 特种设备检测机构主体001,
    String organizationalUnitId;// 1,
    String organizationalUnitName;// 黑河市市场监督管理局爱辉分局,
    String checkNature;// null,
    String checkNatureText;// ,
    String firstCheckDate;// ,
    String anquanfaCheckStatus;// null,
    String anquanfaCheckStatusText;// ,
    String guoluCheckStatus;// null,
    String guoluCheckStatusText;// ,
    String xiansuqiCheckStatus;// null,
    String xiansuqiCheckStatusText;// ,
    String checkCategory;// null,
    String checkCategoryText;// ,
    String processStatus;// null,
    String processStatusText;// ,
    String processDate;// ,
    List<CheckDetail> tzsbDeviceCheckDetailList;// ,

    public void setId(String id) {
        this.id = id;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setPartId(String partId) {
        this.partId = partId;
    }

    public void setInspectionOrganizationId(String inspectionOrganizationId) {
        this.inspectionOrganizationId = inspectionOrganizationId;
    }

    public void setInspectionOrganizationName(String inspectionOrganizationName) {
        this.inspectionOrganizationName = inspectionOrganizationName;
    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public void setCheckNature(String checkNature) {
        this.checkNature = checkNature;
    }

    public void setCheckNatureText(String checkNatureText) {
        this.checkNatureText = checkNatureText;
    }

    public void setFirstCheckDate(String firstCheckDate) {
        this.firstCheckDate = firstCheckDate;
    }

    public void setAnquanfaCheckStatus(String anquanfaCheckStatus) {
        this.anquanfaCheckStatus = anquanfaCheckStatus;
    }

    public void setAnquanfaCheckStatusText(String anquanfaCheckStatusText) {
        this.anquanfaCheckStatusText = anquanfaCheckStatusText;
    }

    public void setGuoluCheckStatus(String guoluCheckStatus) {
        this.guoluCheckStatus = guoluCheckStatus;
    }

    public void setGuoluCheckStatusText(String guoluCheckStatusText) {
        this.guoluCheckStatusText = guoluCheckStatusText;
    }

    public void setXiansuqiCheckStatus(String xiansuqiCheckStatus) {
        this.xiansuqiCheckStatus = xiansuqiCheckStatus;
    }

    public void setXiansuqiCheckStatusText(String xiansuqiCheckStatusText) {
        this.xiansuqiCheckStatusText = xiansuqiCheckStatusText;
    }

    public void setCheckCategory(String checkCategory) {
        this.checkCategory = checkCategory;
    }

    public void setCheckCategoryText(String checkCategoryText) {
        this.checkCategoryText = checkCategoryText;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public void setProcessStatusText(String processStatusText) {
        this.processStatusText = processStatusText;
    }

    public void setProcessDate(String processDate) {
        this.processDate = processDate;
    }

    public void setTzsbDeviceCheckDetailList(List<CheckDetail> tzsbDeviceCheckDetailList) {
        this.tzsbDeviceCheckDetailList = tzsbDeviceCheckDetailList;
    }

    public List<CheckDetail> getTzsbDeviceCheckDetailList() {
        return tzsbDeviceCheckDetailList;
    }

    public String getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getPartId() {
        return partId;
    }

    public String getInspectionOrganizationId() {
        return inspectionOrganizationId;
    }

    public String getInspectionOrganizationName() {
        return inspectionOrganizationName;
    }

    public String getOrganizationalUnitId() {
        return organizationalUnitId;
    }

    public String getOrganizationalUnitName() {
        return organizationalUnitName;
    }

    public String getCheckNature() {
        return checkNature;
    }

    public String getCheckNatureText() {
        return checkNatureText;
    }

    public String getFirstCheckDate() {
        return firstCheckDate;
    }

    public String getAnquanfaCheckStatus() {
        return anquanfaCheckStatus;
    }

    public String getAnquanfaCheckStatusText() {
        return anquanfaCheckStatusText;
    }

    public String getGuoluCheckStatus() {
        return guoluCheckStatus;
    }

    public String getGuoluCheckStatusText() {
        return guoluCheckStatusText;
    }

    public String getXiansuqiCheckStatus() {
        return xiansuqiCheckStatus;
    }

    public String getXiansuqiCheckStatusText() {
        return xiansuqiCheckStatusText;
    }

    public String getCheckCategory() {
        return checkCategory;
    }

    public String getCheckCategoryText() {
        return checkCategoryText;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public String getProcessStatusText() {
        return processStatusText;
    }

    public String getProcessDate() {
        return processDate;
    }

   public class CheckDetail {
        String id;// 9,
        String checkId;// 4,
        String checkModelType;// String ndjyString ,
        String checkModelTypeText;// String ndjyString ,
        String organizationalUnitId;// 1,
        String organizationalUnitName;// String 黑河市市场监督管理局爱辉分局String ,
        String reportId;// String String ,
        String checker;// String String ,
        String lastCheckDate;// String String ,
        String checkDate;// String 2020-12-11String ,
        String nextCheckDate;// String 2021-03-04String ,
        String checkReduction;// null,
        String checkReductionText;// String String ,
        String deviceProblem;// String String ,
        String manageProblem;// String String

       public String getId() {
           return id;
       }

       public String getCheckId() {
           return checkId;
       }

       public String getCheckModelType() {
           return checkModelType;
       }

       public String getCheckModelTypeText() {
           return checkModelTypeText;
       }

       public String getOrganizationalUnitId() {
           return organizationalUnitId;
       }

       public String getOrganizationalUnitName() {
           return organizationalUnitName;
       }

       public String getReportId() {
           return reportId;
       }

       public String getChecker() {
           return checker;
       }

       public String getLastCheckDate() {
           return lastCheckDate;
       }

       public String getCheckDate() {
           return checkDate;
       }

       public String getNextCheckDate() {
           return nextCheckDate;
       }

       public String getCheckReduction() {
           return checkReduction;
       }

       public String getCheckReductionText() {
           return checkReductionText;
       }

       public String getDeviceProblem() {
           return deviceProblem;
       }

       public String getManageProblem() {
           return manageProblem;
       }
   }
}