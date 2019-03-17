package com.test.ishanishah.network;

import com.test.ishanishah.user.model.UserData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("users")
    Observable<List<UserData>> getUsers(@Query("since") int count);
}
