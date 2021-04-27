package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;

public class EquipmentBean implements Parcelable {
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
    String   alarmStatusColor;// null,
    String   nextCheckDate;// null

    String companyInternalNum;
    String factoryNum;
    String locationName;
    String certifyingAuthority;
    String authorityDate;
    String maintenanceContact;
    String emergencyCall;

    protected EquipmentBean(Parcel in) {
        deviceCode = in.readString();
        id = in.readString();
        deviceModel = in.readString();
        companyId = in.readString();
        operatorName = in.readString();
        socialCreditCode = in.readString();
        deviceType1 = in.readString();
        deviceType2 = in.readString();
        deviceType3 = in.readString();
        deviceType1Text = in.readString();
        deviceType2Text = in.readString();
        deviceType3Text = in.readString();
        deviceName = in.readString();
        registStatus = in.readInt();
        registStatusText = in.readString();
        registOrganizationId = in.readString();
        registOrganizationName = in.readString();
        registTime = in.readString();
        registRecorder = in.readString();
        registNumber = in.readString();
        registCode = in.readString();
        usageStatus = in.readInt();
        usageStatusText = in.readString();
        usageUpdateDate = in.readString();
        manufacturerName = in.readString();
        manufacturerDate = in.readString();
        projectNumber = in.readString();
        installationCompany = in.readString();
        completionDate = in.readString();
        totalLength = in.readString();
        maintenanceCompany = in.readString();
        licensePlate = in.readString();
        mapAddress = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        alarmStatus = in.readString();
        alarmStatusText = in.readString();
        alarmStatusColor = in.readString();
        nextCheckDate = in.readString();
        companyInternalNum = in.readString();
        factoryNum = in.readString();
        locationName = in.readString();
        certifyingAuthority = in.readString();
        authorityDate = in.readString();
        maintenanceContact = in.readString();
        emergencyCall = in.readString();
        select = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(deviceCode);
        dest.writeString(id);
        dest.writeString(deviceModel);
        dest.writeString(companyId);
        dest.writeString(operatorName);
        dest.writeString(socialCreditCode);
        dest.writeString(deviceType1);
        dest.writeString(deviceType2);
        dest.writeString(deviceType3);
        dest.writeString(deviceType1Text);
        dest.writeString(deviceType2Text);
        dest.writeString(deviceType3Text);
        dest.writeString(deviceName);
        dest.writeInt(registStatus);
        dest.writeString(registStatusText);
        dest.writeString(registOrganizationId);
        dest.writeString(registOrganizationName);
        dest.writeString(registTime);
        dest.writeString(registRecorder);
        dest.writeString(registNumber);
        dest.writeString(registCode);
        dest.writeInt(usageStatus);
        dest.writeString(usageStatusText);
        dest.writeString(usageUpdateDate);
        dest.writeString(manufacturerName);
        dest.writeString(manufacturerDate);
        dest.writeString(projectNumber);
        dest.writeString(installationCompany);
        dest.writeString(completionDate);
        dest.writeString(totalLength);
        dest.writeString(maintenanceCompany);
        dest.writeString(licensePlate);
        dest.writeString(mapAddress);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(alarmStatus);
        dest.writeString(alarmStatusText);
        dest.writeString(alarmStatusColor);
        dest.writeString(nextCheckDate);
        dest.writeString(companyInternalNum);
        dest.writeString(factoryNum);
        dest.writeString(locationName);
        dest.writeString(certifyingAuthority);
        dest.writeString(authorityDate);
        dest.writeString(maintenanceContact);
        dest.writeString(emergencyCall);
        dest.writeByte((byte) (select ? 1 : 0));
    }

    public static final Creator<EquipmentBean> CREATOR = new Creator<EquipmentBean>() {
        @Override
        public EquipmentBean createFromParcel(Parcel in) {
            return new EquipmentBean(in);
        }

        @Override
        public EquipmentBean[] newArray(int size) {
            return new EquipmentBean[size];
        }
    };

    public String getMaintenanceContact() {
        return maintenanceContact;
    }

    public String getEmergencyCall() {
        return emergencyCall;
    }

    public String getCompanyInternalNum() {
        return companyInternalNum;
    }

    public String getFactoryNum() {
        return factoryNum;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getCertifyingAuthority() {
        return certifyingAuthority;
    }

    public String getAuthorityDate() {
        return authorityDate;
    }



    boolean   select;// null

    public String getAlarmStatusColor() {
        return alarmStatusColor;
    }




    public void setSelect(boolean select) {
        this.select = select;
    }

    public boolean isSelect() {
        return select;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }


}
