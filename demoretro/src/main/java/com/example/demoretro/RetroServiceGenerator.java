package com.example.demoretro;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetroServiceGenerator {

    private static final String BASE_URL = "http://localhost:8081";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    // Without any security
    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    // With JWT token implementation
    public static <S> S createService(Class<S> serviceClass, final String token ) {
        if ( token != null ) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor( chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", token)
                        .build();
                return chain.proceed(request);
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

    @Bean
    @Qualifier("BillionaireServiceNoSecurity")
    public BillionaireService billionaireServiceInitNoSecurity() {
        return RetroServiceGenerator.createService(BillionaireService.class);
    }

    @Bean
    @Qualifier("BillionaireServiceJWT")
    public BillionaireService billionaireServiceInitJWT() {
        return RetroServiceGenerator.createService(BillionaireService.class);
    }
}
