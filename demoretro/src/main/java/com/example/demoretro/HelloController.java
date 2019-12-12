package com.example.demoretro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

@RestController
public class HelloController {

    @Autowired @Qualifier("BillionaireServiceNoSecurity")
    BillionaireService billionaireService;

    @GetMapping("/")
    public BillionaireResponse sayHello() {

        Call<BillionaireResponse> callSync = billionaireService.getBillionaire();

        Response<BillionaireResponse> response = null;
        try {
            response = callSync.execute();
        } catch (IOException e) {
            e.printStackTrace();
            return new BillionaireResponse();
        }
        BillionaireResponse poorna = response.body();
        return poorna;

    }

    @GetMapping("/2")
    public BillionaireResponse sayHello2() {

        Call<BillionaireResponse> callAsync = billionaireService.getBillionaire();

        callAsync.enqueue(new Callback<BillionaireResponse>() {
            @Override
            public void onResponse(Call<BillionaireResponse> call, Response<BillionaireResponse> response) {
                BillionaireResponse billionaireResponse = response.body();
                System.out.println(billionaireResponse.firstName + " " + billionaireResponse.lastName);
            }

            @Override
            public void onFailure(Call<BillionaireResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return new BillionaireResponse();
    }
}
