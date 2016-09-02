package com.geno.pm.pmms_sx.Bean;


public class Login {
    private LogStatus status;

    private Data data;

    public void setStatus(LogStatus status){
        this.status = status;
    }
    public LogStatus getStatus(){
        return this.status;
    }
    public void setData(Data data){
        this.data = data;
    }
    public Data getData(){
        return this.data;
    }
}
