package com.limi88.financialplanner.api;



import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hehao on 16/9/6.
 */
public class LoggingInterceptor implements Interceptor {
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";

    @Override
    public Response intercept(Chain chain) {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d("Api",String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long t2 = System.nanoTime();
        if (response!=null) {
            Log.d("Api", String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        }

        return response;
    }
}
