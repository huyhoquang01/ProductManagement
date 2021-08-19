package com.example.aidnetworking.myretrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private String BASE_URL = "http://192.168.1.2/manager/views/";
//    private final Retrofit retrofit = buildRetrofit();

    private Retrofit buildRetrofit() {
//        Interceptor interceptor = chain -> {
//            String access_token = AccessTokenManager.getInstance(null).getToken().getAccess_token();
//            Request request = chain.request()
//                    .newBuilder()
//                    .addHeader("Authorization", "Bearer " + access_token)
//                    .build();
//            return chain.proceed(request);
//        };
//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(interceptor);
//        OkHttpClient client = builder.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public <T> T createService(Class<T> service){
        return buildRetrofit().create(service);
    }
    public <T> T createService2Pik(Class<T> service, String url){
        BASE_URL = url;
        return buildRetrofit().create(service);
    }
}
