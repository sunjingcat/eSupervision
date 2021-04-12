package com.zz.supervision.bean;

import com.zz.supervision.business.equipment.BusinessStatus;

public class BeforeAddDeviceCheck {
    String id;// null,
    String checkId;// null,
    String checkModelType;// annbjy,
    String checkModelTypeText;// 安装监检/内部检验,
    String organizationalUnitId;// null,
    String organizationalUnitName;// null,
    String reportId;// null,
    String checker;// null,
    String lastCheckDate;// null,
    String checkDate;// null,
    String nextCheckDate;// null,
    String checkReduction;// null,
    String checkReductionText;// null,
    String deviceProblem;// null,
    String manageProblem;// null

    public void setId(String id) {
        this.id = id;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }

    public void setCheckModelType(String checkModelType) {
        this.checkModelType = checkModelType;
    }

    public void setCheckModelTypeText(String checkModelTypeText) {
        this.checkModelTypeText = checkModelTypeText;
    }

    public void setOrganizationalUnitId(String organizationalUnitId) {
        this.organizationalUnitId = organizationalUnitId;
    }

    public void setOrganizationalUnitName(String organizationalUnitName) {
        this.organizationalUnitName = organizationalUnitName;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }

    public void setLastCheckDate(String lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public void setNextCheckDate(String nextCheckDate) {
        this.nextCheckDate = nextCheckDate;
    }

    public void setCheckReduction(String checkReduction) {
        this.checkReduction = checkReduction;
    }

    public void setCheckReductionText(String checkReductionText) {
        this.checkReductionText = checkReductionText;
    }

    public void setDeviceProblem(String deviceProblem) {
        this.deviceProblem = deviceProblem;
    }

    public void setManageProblem(String manageProblem) {
        this.manageProblem = manageProblem;
    }

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
