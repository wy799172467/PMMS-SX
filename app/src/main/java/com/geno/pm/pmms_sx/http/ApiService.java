package com.geno.pm.pmms_sx.http;

import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.Bean.Project;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import rx.Observable;

public interface ApiService {

    String BASE_URL = "http://192.168.34.81:8075/sxpm-api/";

    String PROJECT_STATUS = "http://192.168.34.81:8075/sxpm-api/test/11";

    @FormUrlEncoded
    @POST(BASE_URL + "login")
    Observable<Login> login(
            @Field("userAccount")
            String userAccount,
            @Field("password")
            String password);

    @GET(BASE_URL + "get_all_project")
    Observable<List<Project>> getAllProject();
}
