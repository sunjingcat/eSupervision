package com.zz.supervision.bean;

public class PipePartBean {
  String  id;// 1,
    String      deviceId;// 2,
    String        manufacturerName;// 制造单位,
    String       manufacturerDate;// 2021-04-08,
    String     pipeNumber;// 管道编号040802,
    String      pipeName;// 管道名称040802,
    String      installationCompany;// 安装单位,
    String     completionDate;// 2020-01-01,
    String     totalLength;// 10.0,
    DeviceCheck     tzsbDeviceCheck;// null

    public String getId() {
        return id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getManufacturerDate() {
        return manufacturerDate;
    }

    public String getPipeNumber() {
        return pipeNumber;
    }

    public String getPipeName() {
        return pipeName;
    }

    public String getInstallationCompany() {
        return installationCompany;
    }

    public String getCompletionDate() {
        return completionDate;
    }

    public String getTotalLength() {
        return totalLength;
    }

    public DeviceCheck getTzsbDeviceCheck() {
        return tzsbDeviceCheck;
    }
}
