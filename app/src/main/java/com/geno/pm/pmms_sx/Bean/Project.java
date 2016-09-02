package com.geno.pm.pmms_sx.Bean;


import android.os.Parcel;
import android.os.Parcelable;

public class Project implements Parcelable {
    private String ProjectName;

    private String WFInstanceId;

    private String ActivityName;

    private boolean IsOver;

    private String ProjectNo;

    private String YearNo;

    private String ProjectType;

    protected Project(Parcel in) {
        ProjectName = in.readString();
        WFInstanceId = in.readString();
        ActivityName = in.readString();
        IsOver = in.readByte() != 0;
        ProjectNo = in.readString();
        YearNo = in.readString();
        ProjectType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ProjectName);
        dest.writeString(WFInstanceId);
        dest.writeString(ActivityName);
        dest.writeByte((byte) (IsOver ? 1 : 0));
        dest.writeString(ProjectNo);
        dest.writeString(YearNo);
        dest.writeString(ProjectType);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Project> CREATOR = new Creator<Project>() {
        @Override
        public Project createFromParcel(Parcel in) {
            return new Project(in);
        }

        @Override
        public Project[] newArray(int size) {
            return new Project[size];
        }
    };

    public void setProjectName(String ProjectName){
        this.ProjectName = ProjectName;
    }
    public String getProjectName(){
        return this.ProjectName;
    }
    public void setWFInstanceId(String WFInstanceId){
        this.WFInstanceId = WFInstanceId;
    }
    public String getWFInstanceId(){
        return this.WFInstanceId;
    }
    public void setActivityName(String ActivityName){
        this.ActivityName = ActivityName;
    }
    public String getActivityName(){
        return this.ActivityName;
    }
    public void setIsOver(boolean IsOver){
        this.IsOver = IsOver;
    }
    public boolean getIsOver(){
        return this.IsOver;
    }
    public void setProjectNo(String ProjectNo){
        this.ProjectNo = ProjectNo;
    }
    public String getProjectNo(){
        return this.ProjectNo;
    }
    public void setYearNo(String YearNo){
        this.YearNo = YearNo;
    }
    public String getYearNo(){
        return this.YearNo;
    }
    public void setProjectType(String ProjectType){
        this.ProjectType = ProjectType;
    }
    public String getProjectType(){
        return this.ProjectType;
    }
}
