package com.example.demoretro;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

@RestController
public class HelloController {

    @GetMapping("/")
    public BillionaireResponse sayHello() {

        BillionaireService service = RetroServiceGenerator.createService(BillionaireService.class);
        Call<BillionaireResponse> callSync = service.getBillionaire();

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

        BillionaireService service = RetroServiceGenerator.createService(BillionaireService.class);
        Call<BillionaireResponse> callAsync = service.getBillionaire();

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
