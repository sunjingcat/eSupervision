package com.zz.supervision.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidu.mapapi.model.LatLng;

public class PLocation implements Parcelable {
    LatLng location;
    String address;

    public PLocation() {
    }

    public PLocation(Parcel in) {
        location = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
    }

    public static final Creator<PLocation> CREATOR = new Creator<PLocation>() {
        @Override
        public PLocation createFromParcel(Parcel in) {
            return new PLocation(in);
        }

        @Override
        public PLocation[] newArray(int size) {
            return new PLocation[size];
        }
    };

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(location, flags);
        dest.writeString(address);
    }
}