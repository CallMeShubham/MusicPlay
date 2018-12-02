package com.music.play.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.music.play.R;
import com.music.play.network.RetrofitAPI;
import com.music.play.network.RetrofitHelper;
import com.music.play.response.Album;
import com.music.play.response.FailureResponse;
import com.music.play.utils.ServiceBus;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Shubham_Aggarwal03 on 8/2/2018.
 */

public class MusicPlayIntentService extends IntentService{

    public static final String EXTRA_REQUEST = "extra_request";
    public static final int REQUEST_GET_ALBUM_LIST = 1;
    private Retrofit retrofit;
    private RetrofitAPI retrofitAPI;
    private ServiceBus bus;

    public MusicPlayIntentService() {
        super("MusicPlayIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bus = ServiceBus.getInstance();
        retrofit = RetrofitHelper.getInstance(this);
        retrofitAPI = retrofit.create(RetrofitAPI.class);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        switch (intent.getIntExtra(EXTRA_REQUEST, -1)) {
            case REQUEST_GET_ALBUM_LIST:
                getAlbumsList();
                break;
        }
    }

    public void getAlbumsList() {
        Call<ArrayList<Album>> call = retrofitAPI.getAlbumsList();
        call.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                if(response.isSuccessful()) {
                    bus.post(response.body());
                } else {
                    FailureResponse failureResponse = new FailureResponse();
                    failureResponse.failureMessage = getString(R.string.no_album_list_found);
                    failureResponse.responseBody = response.errorBody();
                    failureResponse.exception = null;
                    bus.post(failureResponse);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                FailureResponse failureResponse = new FailureResponse();
                failureResponse.failureMessage = getString(R.string.no_album_list_found);
                failureResponse.responseBody = null;
                failureResponse.exception = t;
                bus.post(failureResponse);
            }
        });
    }

}
