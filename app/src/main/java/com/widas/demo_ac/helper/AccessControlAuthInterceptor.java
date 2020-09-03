package com.widas.demo_ac.helper;

import com.widas.demo_ac.common.SessionManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessControlAuthInterceptor implements Interceptor {

    private final String ACCESS_TOKEN = "access_token";
    SessionManager sessionManager = null;
    private String mAccessToken = null;

    public AccessControlAuthInterceptor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        mAccessToken = sessionManager.getAccessToken();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authtokenaddedRequest = null;
        Response response;

        if (originalRequest.header(ACCESS_TOKEN) == null) {
            authtokenaddedRequest = originalRequest.newBuilder()
                    .header(ACCESS_TOKEN, mAccessToken)
                    .build();
        } else {
            authtokenaddedRequest = originalRequest;
        }
        response = chain.proceed(authtokenaddedRequest);

        return response;
    }
}
