package com.ming.cnbeta.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ming on 16/1/30.
 */
public class BaseModel implements Parcelable {
    protected BaseModel(Parcel in) {
    }

    public BaseModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
