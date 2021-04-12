package com.zz.supervision.bean;

import android.text.TextUtils;

import java.io.Serializable;

public class EquipmentBean implements Serializable {
    String  deviceCode;// 锅0001,
    String  id;// 锅0001,
    String  deviceModel;// 锅炉001-1009,
    String    companyId;// 1,
    String    operatorName;// 天津特种设备有限公司,
    String   socialCreditCode;// 82009812428DF122,
    String    deviceType1;// 1,
    String   deviceType2;// 1.3,
    String    deviceType3;// 1.3.1,
      String    deviceType1Text;// 1,
    String   deviceType2Text;// 1.3,
    String    deviceType3Text;// 1.3.1,
    String   deviceName;// 锅炉一号,
    int   registStatus;// 1,
    String   registStatusText;// 1,
    String   registOrganizationId;// 1,
    String     registOrganizationName;// 注册机构,
    String registTime;// 2020-11-11,
    String   registRecorder;// zs,
    String   registNumber;// 22222,
    String   registCode;// 37020002,
    int usageStatus;// 1,
    String usageStatusText;// 1,
    String   usageUpdateDate;// 2020-11-11,
    String  manufacturerName;// 特种设备制造有限公司,
    String  manufacturerDate;// 2015-06-03,
    String   projectNumber;// 00004000210,
    String  installationCompany;// 特种设备安装有限公司,
    String   completionDate;// 2020-12-10,
    String  totalLength;// null,
    String   maintenanceCompany;// null,
    String  licensePlate;// null,
    String   mapAddress;// null,
    String   longitude;// 117.2106064812852,
    String   latitude;// 39.09113112858562,
    String    alarmStatus;// null,
    String   alarmStatusText;// null,
    String   nextCheckDate;// null

    public String getDeviceType1Text() {
        return deviceType1Text;
    }

    public String getDeviceType2Text() {
        return deviceType2Text;
    }

    public String getDeviceType3Text() {
        return deviceType3Text;
    }

    public String getRegistStatusText() {
        return registStatusText;
    }

    public String getUsageStatusText() {
        return usageStatusText;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public String getId() {
        return id;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getSocialCreditCode() {
        return socialCreditCode;
    }

    public String getDeviceType1() {
        return deviceType1;
    }

    public String getDeviceType2() {
        return deviceType2;
    }

    public String getDeviceType3() {
        return deviceType3;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public int getRegistStatus() {
        return registStatus;
    }

    public String getRegistOrganizationId() {
        return registOrganizationId;
    }

    public String getRegistOrganizationName() {
        return registOrganizationName;
    }

    public String getRegistTime() {
        return registTime;
    }

    public String getRegistRecorder() {
        return registRecorder;
    }

    public String getRegistNumber() {
        return registNumber;
    }

    public String getRegistCode() {
        return registCode;
    }

    public int getUsageStatus() {
        return usageStatus;
    }

    public String getUsageUpdateDate() {
        return usageUpdateDate;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public String getManufacturerDate() {
        return manufacturerDate;
    }

    public String getProjectNumber() {
        return projectNumber;
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

    public String getMaintenanceCompany() {
        return maintenanceCompany;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getMapAddress() {
        return mapAddress;
    }

    public Double getLongitude() {
        if (TextUtils.isEmpty(longitude)){
            return 0.0;
        }else {
            return Double.parseDouble(longitude);
        }
    }


    public Double getLatitude() {
        if (TextUtils.isEmpty(latitude)){
            return 0.0;
        }else {
            return Double.parseDouble(latitude);
        }
    }
    public String getAlarmStatus() {
        return alarmStatus;
    }

    public String getAlarmStatusText() {
        return alarmStatusText;
    }

    public String getNextCheckDate() {
        return nextCheckDate;
    }
}
