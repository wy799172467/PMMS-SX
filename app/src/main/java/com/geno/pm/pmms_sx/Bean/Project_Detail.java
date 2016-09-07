package com.geno.pm.pmms_sx.Bean;

public class Project_Detail {

    @FieldMeta(1)
    private String ProjectName;

    @FieldMeta(2)
    private String ProjectType;

    @FieldMeta(3)
    private String ProjectNo;

    @FieldMeta(4)
    private String ProjectCategory;

    @FieldMeta(5)
    private String YearNo;

    @FieldMeta(6)
    private String Invest;

    @FieldMeta(7)
    private String FundsSourceName;

    @FieldMeta(8)
    private String Scale;

    @FieldMeta(9)
    private String StartTime;

    @FieldMeta(10)
    private String FinishTime;

    public void setProjectName(String ProjectName) {
        this.ProjectName = ProjectName;
    }

    public String getProjectName() {
        return this.ProjectName;
    }

    public void setProjectType(String ProjectType) {
        this.ProjectType = ProjectType;
    }

    public String getProjectType() {
        return this.ProjectType;
    }

    public void setProjectNo(String ProjectNo) {
        this.ProjectNo = ProjectNo;
    }

    public String getProjectNo() {
        return this.ProjectNo;
    }

    public void setProjectCategory(String ProjectCategory) {
        this.ProjectCategory = ProjectCategory;
    }

    public String getProjectCategory() {
        return this.ProjectCategory;
    }

    public void setYearNo(String YearNo) {
        this.YearNo = YearNo;
    }

    public String getYearNo() {
        return this.YearNo;
    }

    public void setInvest(String Invest) {
        this.Invest = Invest;
    }

    public String getInvest() {
        return this.Invest;
    }

    public void setFundsSourceName(String FundsSourceName) {
        this.FundsSourceName = FundsSourceName;
    }

    public String getFundsSourceName() {
        return this.FundsSourceName;
    }

    public void setScale(String Scale) {
        this.Scale = Scale;
    }

    public String getScale() {
        return this.Scale;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public String getStartTime() {
        return this.StartTime;
    }

    public void setFinishTime(String FinishTime) {
        this.FinishTime = FinishTime;
    }

    public String getFinishTime() {
        return this.FinishTime;
    }

}
