package com.geno.pm.pmms_sx.http;

import com.geno.pm.pmms_sx.Bean.Login;
import com.geno.pm.pmms_sx.Bean.Project;
import com.geno.pm.pmms_sx.Bean.Project_Detail;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    String BASE_URL = "http://192.168.84.112:9075/sxpm-api/";

    String PROJECT_PROGRESS = "http://192.168.84.112:9075/sxpm-api/get_project_details_progress/";

    @FormUrlEncoded
    @POST(BASE_URL + "login")
    Observable<Login> login(
            @Field("userAccount")
            String userAccount,
            @Field("password")
            String password);

    @GET(BASE_URL + "get_all_project")
    Observable<List<Project>> getAllProject();

    @GET(BASE_URL + "get_project_details/{ProjectNo}")
    Observable<Project_Detail> getProjectDetail(@Path("ProjectNo") String ProjectNo);
}
