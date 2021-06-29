package com.zz.supervision.bean;

public class SyxhRectificationOrder {
  private String  id;//12,
    private String     recordType;//spxs,
    private String     recordId;//520,
    private String     year;//2021,
    private String    orderNum;//002,
    private String    illegalActivity;//qw,
    private String    lawContent;//w,
    private String     accordContent;//e,
    private String    reformMeasure;//r,
    private String     reformTimeType;//1,
    private String    reformTimeTypeText;//是,
    private String     reformTime;//,
    private String     contact;//qwp,
    private String      contactInformation;//1511111111
    private String officerSign1;//null,
    private String     officerSign2;//null,
    private String    companySign;//null

    private String     serviceDocument;// 责令改正通知书齐富市监食责改【2021】99003\n当场行政处罚决定书202199003,
    private String     orderName;// 食责改【2021】99003,
    private String     decisionName;// 202199003,
    private String     legalRepresentative;// 太苦了,
    private String     businessPlace;// 特哦你看,
    private String     inspectionTime;// 2021年06月29日,
    private String      officerSign;// data;//image/png;base64,null,
    private String      observerSign;// data;//image/png;base64,null
    public String getId() {
        return id;
    }

    public String getOfficerSign1() {
        return officerSign1;
    }

    public String getOfficerSign2() {
        return officerSign2;
    }

    public String getCompanySign() {
        return companySign;
    }

    public String getRecordType() {
        return recordType;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getYear() {
        return year;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public String getIllegalActivity() {
        return illegalActivity;
    }

    public String getLawContent() {
        return lawContent;
    }

    public String getAccordContent() {
        return accordContent;
    }

    public String getReformMeasure() {
        return reformMeasure;
    }

    public String getReformTimeType() {
        return reformTimeType;
    }

    public String getReformTimeTypeText() {
        return reformTimeTypeText;
    }

    public String getReformTime() {
        return reformTime;
    }

    public String getContact() {
        return contact;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public String getServiceDocument() {
        return serviceDocument;
    }

    public String getOrderName() {
        return orderName;
    }

    public String getDecisionName() {
        return decisionName;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public String getBusinessPlace() {
        return businessPlace;
    }

    public String getInspectionTime() {
        return inspectionTime;
    }

    public String getOfficerSign() {
        return officerSign;
    }

    public String getObserverSign() {
        return observerSign;
    }
}
