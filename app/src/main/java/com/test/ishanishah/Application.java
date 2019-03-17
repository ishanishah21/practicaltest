package com.test.ishanishah;

import com.test.ishanishah.appexceptions.NoConnectionException;
import com.test.ishanishah.network.ApiClient;
import com.test.ishanishah.network.ApiInterface;

public class Application extends android.app.Application {
    private static ApiInterface apiInterface;
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            apiInterface = ApiClient.getApiClient(this, "hide_loader");
        } catch (NoConnectionException e) {
            e.printStackTrace();
            apiInterface = null;
        }
    }

    public static ApiInterface getApiInterface() {
        return apiInterface;
    }

    public static void setApiInterface(ApiInterface apiInterface) {
        Application.apiInterface = apiInterface;
    }
}
