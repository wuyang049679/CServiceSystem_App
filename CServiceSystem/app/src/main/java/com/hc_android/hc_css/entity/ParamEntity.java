package com.hc_android.hc_css.entity;

import android.os.Parcel;
import android.os.Parcelable;


public class ParamEntity implements Parcelable {

    String title;
    int num;
    String param;
     boolean isChecked;

    public ParamEntity(String title) {
        this.title = title;
    }

    public ParamEntity(String title, int num) {
        this.title = title;
        this.num = num;
    }

    public ParamEntity(boolean isChecked,String title) {
        this.title = title;
        this.isChecked = isChecked;
    }

    public static final Creator<ParamEntity> CREATOR = new Creator<ParamEntity>() {
        @Override
        public ParamEntity createFromParcel(Parcel in) {
            return new ParamEntity(in);
        }

        @Override
        public ParamEntity[] newArray(int size) {
            return new ParamEntity[size];
        }
    };

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    protected ParamEntity(Parcel in) {
        title = in.readString();
        num = in.readInt();
        param = in.readString();
        isChecked = in.readByte() != 0;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeInt(num);
        parcel.writeString(param);
        parcel.writeByte((byte) (isChecked ? 1 : 0));
    }
}
