package com.zz.supervision.bean;

import android.text.TextUtils;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.ArrayList;

public class CityBean implements IPickerViewData {
    private String degree;// 0,
    private String deptId;// 0,
    private String id;// 0,
    private String name;// ,
    private String pid;// 0,
    private String remark;// ,
    private ArrayList<CityBean> cList;//string

    public String getDegree() {
        return degree;
    }

    public String getDeptId() {
        return deptId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPid() {
        return pid;
    }

    public String getRemark() {
        return remark;
    }


    public ArrayList<CityBean> getClist() {
        return cList;
    }


    @Override
    public String getPickerViewText() {
        if (TextUtils.isEmpty(name)) {
            return "";
        }
        return name;
    }
}
