package com.zz.supervision.bean;

import java.io.Serializable;

public class RecordBean implements Serializable {
    private String searchValue;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
    private Object params;
    private String deptId;
    private String reason;
    private int id;
    private int type;
    private String typeText;
    private String operatorName;
    private String licenseNumber;
    private String problemCount;
    private int yearCount;
    private String inspectionResult;
    private String inspectionResultText;
    private String level;
    private String lawEnforcer1Name;
    private String lawEnforcer2Name;
    private String inspectionTime;
    private int status;
    private String statusText;
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public String getSearchValue() {
        return searchValue;
    }

    public String getProblemCount() {
        return problemCount;
    }

    public String getReason() {
        return reason;
    }

    public String getInspectionResultText() {
        return inspectionResultText;
    }

    public String getTypeText() {
        return typeText;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
    public String getRemark() {
        return remark;
    }

    public void setParams(Object params) {
        this.params = params;
    }
    public Object getParams() {
        return params;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    public String getDeptId() {
        return deptId;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    public String getOperatorName() {
        return operatorName;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setYearCount(int yearCount) {
        this.yearCount = yearCount;
    }
    public int getYearCount() {
        return yearCount;
    }

    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }
    public String getInspectionResult() {
        return inspectionResult;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public String getLevel() {
        return level;
    }

    public void setLawEnforcer1Name(String lawEnforcer1Name) {
        this.lawEnforcer1Name = lawEnforcer1Name;
    }
    public String getLawEnforcer1Name() {
        return lawEnforcer1Name;
    }

    public void setLawEnforcer2Name(String lawEnforcer2Name) {
        this.lawEnforcer2Name = lawEnforcer2Name;
    }
    public String getLawEnforcer2Name() {
        return lawEnforcer2Name;
    }

    public void setInspectionTime(String inspectionTime) {
        this.inspectionTime = inspectionTime;
    }
    public String getInspectionTime() {
        return inspectionTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    public String getStatusText() {
        return statusText;
    }
}
