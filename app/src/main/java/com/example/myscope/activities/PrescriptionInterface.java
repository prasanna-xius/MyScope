package com.example.myscope.activities;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.QueryName;

public interface PrescriptionInterface {
//     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();

@Headers({"Content-Type: application/json"})
    @POST("addRecord")
    Call<SignupResponse> createPatient(@Body SignupResponse body);

    @Headers("Content-Type: application/json")
    @GET("list")
    Call<List<SignupResponse>> getAllResponse(@QueryMap  HashMap<String,String> resp);

    @Headers("Content-Type: application/json")
    @GET("list/{mobile_no}")
    Call<List<SignupResponse>> getloginResponse(@Path("mobile_no") String mobile_no);
}