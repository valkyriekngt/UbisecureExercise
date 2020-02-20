package com.freetime.dva.ubisecureexercise.Interface;

import com.freetime.dva.ubisecureexercise.models.JsonTrainObject;

import retrofit2.Call;
import retrofit2.http.PUT;

public interface IMyApiEndpoint {
    @PUT("/trains/123/locations")
    Call<JsonTrainObject> getTrain();
}
