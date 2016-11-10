package com.example.budget.Network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by eaglebrosi on 10/31/16.
 */

public class SessionRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        // this gives us a generic request
        Request request = chain.request();
        // so we actually make a pretty cool request here. Very nice.
        if(UserStore.getInstance().getToken() != null){
            Request.Builder builder = request.newBuilder();
            // we put "bearer " with a space because Brent was told to! Isn't that cool!
            builder.header("Authorization", "bearer "+ UserStore.getInstance().getToken());
            request = builder.build();
        }

        return chain.proceed(request);
    }
}
