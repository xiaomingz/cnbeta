package com.ming.cnbeta.net;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by ming on 16/2/5.
 */
public class NewsClient {

    public static CnBetaApi mCnBetaApi;

    public NewsClient(){
    }

    public static void init(){
        if (mCnBetaApi == null) {
            OkHttpClient httpClient = new OkHttpClient();
            httpClient.interceptors().add(interceptor);
            mCnBetaApi = new Retrofit.Builder().baseUrl(Constant.BASEURL)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(ToStringConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()
                    .create(CnBetaApi.class);

        }
    }

    public static CnBetaApi getCnBetaApi(){
        return mCnBetaApi;
    }


    // Define the interceptor, add authentication headers
    private static Interceptor interceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Referer", "http://www.cnbeta.com/")
                    .addHeader("Origin", "http://www.cnbeta.com")
                    .addHeader("X-Requested-With", "XMLHttpRequest").build();
            return chain.proceed(newRequest);
        }
    };

}
