package com.zz.supervision.bean;

import android.text.TextUtils;

import com.contrarywind.interfaces.IPickerViewData;

import java.io.Serializable;
import java.util.ArrayList;

public class DictBean implements IPickerViewData {
    private String cssClass;    //string
    private Integer dictCode;//'	integer
    private String dictLabel;//	string
    private Integer dictSort;//integer
    private String dictType;//	string
    private String dictValue;//	string
    private String isDefault;//	string
    private String listClass;//	string
    private String status;//string
    private ArrayList<DictBean> clist;//string

    public ArrayList<DictBean> getClist() {
        return clist;
    }

    public String getCssClass() {
        return cssClass;
    }

    public Integer getDictCode() {
        return dictCode;
    }

    public String getDictLabel() {
        return dictLabel;
    }

    public Integer getDictSort() {
        return dictSort;
    }

    public String getDictType() {
        return dictType;
    }

    public String getDictValue() {
        return dictValue;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public String getListClass() {
        return listClass;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String getPickerViewText() {
        if(TextUtils.isEmpty(dictLabel)){
            return "";
        }
        return  dictLabel;
    }
}
