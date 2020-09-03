package com.widas.demo_ac.AccessControl.service;

import com.widas.demo_ac.AppApplication;
import com.widas.demo_ac.common.SessionManager;
import com.widas.demo_ac.helper.AccessControlAuthInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AuthService {

    private final SessionManager sessionManager;

    public AuthService() {
        sessionManager = new SessionManager(AppApplication.getInstance().getApplicationContext());
    }

    private AuthApi createClient(String serviceUrl) {
        OkHttpClient okHttpClient = null;
        AuthApi authorizationApi = null;
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AccessControlAuthInterceptor(sessionManager))
                .readTimeout(160, TimeUnit.SECONDS)
                .connectTimeout(160, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();
        authorizationApi = retrofit.create(AuthApi.class);
        return authorizationApi;
    }

    public AuthApi getInstance( String URL) {
        return createClient(URL/*"https://nightlybuild.cidaas.de/access-control-srv/"*/);
    }
}
