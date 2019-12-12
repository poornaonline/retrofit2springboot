package com.example.demoretro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BillionaireService {

    @GET("/billionaire")
    public Call<BillionaireResponse> getBillionaire();
}
