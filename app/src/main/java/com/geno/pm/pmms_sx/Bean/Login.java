package com.geno.pm.pmms_sx.Bean;


public class Login {

    private Data data;

    private Filter filter;

    private LogStatus status;

    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return this.data;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Filter getFilter() {
        return this.filter;
    }

    public void setStatus(LogStatus status) {
        this.status = status;
    }

    public LogStatus getStatus() {
        return this.status;
    }

}
