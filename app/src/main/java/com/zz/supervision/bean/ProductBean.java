package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductBean implements Parcelable {
    String id;
    boolean isSelect;

    String   companyId;//4,
    String   operatorName;//b,
    String   name;//a,
    String  category;//b,
    String  unit;//,
    String   varietySpecModel;//c,
    String  productionSituation;//1,
    String   productionSituationText;//正常生产

    protected ProductBean(Parcel in) {
        id = in.readString();
        isSelect = in.readByte() != 0;
        companyId = in.readString();
        operatorName = in.readString();
        name = in.readString();
        category = in.readString();
        unit = in.readString();
        varietySpecModel = in.readString();
        productionSituation = in.readString();
        productionSituationText = in.readString();
    }

    public static final Creator<ProductBean> CREATOR = new Creator<ProductBean>() {
        @Override
        public ProductBean createFromParcel(Parcel in) {
            return new ProductBean(in);
        }

        @Override
        public ProductBean[] newArray(int size) {
            return new ProductBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getUnit() {
        return unit;
    }

    public String getVarietySpecModel() {
        return varietySpecModel;
    }

    public String getProductionSituation() {
        return productionSituation;
    }

    public String getProductionSituationText() {
        return productionSituationText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeByte((byte) (isSelect ? 1 : 0));
        dest.writeString(companyId);
        dest.writeString(operatorName);
        dest.writeString(name);
        dest.writeString(category);
        dest.writeString(unit);
        dest.writeString(varietySpecModel);
        dest.writeString(productionSituation);
        dest.writeString(productionSituationText);
    }
}
