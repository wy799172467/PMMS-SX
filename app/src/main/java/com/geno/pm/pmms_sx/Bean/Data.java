package com.geno.pm.pmms_sx.Bean;


import android.os.Parcel;
import android.os.Parcelable;

public class Data implements Parcelable {
    private String userAccount;

    private String Department;

    private String name;

    protected Data(Parcel in) {
        userAccount = in.readString();
        Department = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userAccount);
        dest.writeString(Department);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public void setUserAccount(String userAccount){
        this.userAccount = userAccount;
    }
    public String getUserAccount(){
        return this.userAccount;
    }
    public void setDepartment(String Department){
        this.Department = Department;
    }
    public String getDepartment(){
        return this.Department;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
