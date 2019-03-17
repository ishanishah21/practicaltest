package com.test.ishanishah.user.viewmodel;

import android.util.Log;

import com.google.gson.Gson;
import com.test.ishanishah.Application;
import com.test.ishanishah.user.model.UserData;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserListViewModel extends ViewModel {


    public void changePage(int page, final MutableLiveData<List<UserData>> listMutableLiveData) {
        Application.getApiInterface().getUsers(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<UserData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<UserData> userData) {
                        Log.e("::->", "::" + new Gson().toJson(userData));
                        listMutableLiveData.postValue(userData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        listMutableLiveData.postValue(new ArrayList<UserData>());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
