package com.geno.pm.pmms_sx.Bean;

import java.util.List;

public class Filter {

    private List<Filter_Year> year;

    private List<Filter_Status> status;

    private List<Filter_Project> project_type;

    public void setYear(List<Filter_Year> year) {
        this.year = year;
    }

    public List<Filter_Year> getYear() {
        return this.year;
    }

    public void setStatus(List<Filter_Status> status) {
        this.status = status;
    }

    public List<Filter_Status> getStatus() {
        return this.status;
    }

    public void setProject_type(List<Filter_Project> project_type) {
        this.project_type = project_type;
    }

    public List<Filter_Project> getProject_type() {
        return this.project_type;
    }

}
