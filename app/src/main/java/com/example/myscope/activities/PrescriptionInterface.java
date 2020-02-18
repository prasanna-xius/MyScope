package com.example.myscope.activities;

import com.example.myscope.activities.prescription.PrescriptionManualDataClass;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PrescriptionInterface {
//     @GET("list")
//    Call<List<PrescriptionManualDataClass>> getData();

    @POST("addRecord")
    Call<SignupResponse> createPatient(@Body SignupResponse body);
}