package com.test.ishanishah.network;


import android.annotation.SuppressLint;
import android.content.Context;

import com.test.ishanishah.BuildConfig;
import com.test.ishanishah.R;
import com.test.ishanishah.appexceptions.NoConnectionException;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    @SuppressLint("StaticFieldLeak")
    private static WeakReference<Context> mContext;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    /**
     * generate OKhttp client
     */
    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(logging);
            }
            builder.readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .build();
            okHttpClient = builder.build();

        }
        return okHttpClient;
    }

    /**
     * generate API interface object for foreground with progress bar
     */
    public static ApiInterface getApiClient(Context con, String loaderMessage) throws NoConnectionException {
        mContext = new WeakReference<>(con);
        Retrofit.Builder builder = new Retrofit.Builder();
        String baseUrl = BuildConfig.BaseURL + "";
        builder.baseUrl(baseUrl);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(getOkHttpClient());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        retrofit = builder.build();


        boolean isForeGround = false;

        return retrofit.create(ApiInterface.class);
    }


    /**
     * generate API interface object for foreground with progress bar
     */
    public static ApiInterface getApiClientForCustomURL(Context con, String loaderMessage, String url) throws NoConnectionException {
        mContext = new WeakReference<>(con);
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.client(getOkHttpClient());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofit = builder.build();

        if (NetworkUtil.getConnectionStatus(mContext.get()) == NetworkUtil.NOT_CONNECTED) {
            throw new NoConnectionException(mContext.get().getResources().getString(R.string.no_internet));
        }

        return retrofit.create(ApiInterface.class);
    }

}
