package com.geno.pm.pmms_sx.Bean;

public class Project {

    private boolean IsOver;

    private String YearNo;

    private String ActivityName;

    private String ProjectName;

    private String WFInstanceId;

    private String ProjectNo;

    private String ProjectType;

    public void setIsOver(boolean IsOver) {
        this.IsOver = IsOver;
    }

    public boolean getIsOver() {
        return this.IsOver;
    }

    public void setYearNo(String YearNo) {
        this.YearNo = YearNo;
    }

    public String getYearNo() {
        return this.YearNo;
    }

    public void setActivityName(String ActivityName) {
        this.ActivityName = ActivityName;
    }

    public String getActivityName() {
        return this.ActivityName;
    }

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getProjectName() {
        return this.ProjectName;
    }

    public void setWFInstanceId(String WFInstanceId) {
        this.WFInstanceId = WFInstanceId;
    }

    public String getWFInstanceId() {
        return this.WFInstanceId;
    }

    public void setProjectNo(String ProjectNo) {
        this.ProjectNo = ProjectNo;
    }

    public String getProjectNo() {
        return this.ProjectNo;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getProjectType() {
        return this.ProjectType;
    }

}
