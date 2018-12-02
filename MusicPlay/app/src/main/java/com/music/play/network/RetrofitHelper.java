package com.music.play.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.music.play.utils.CommonConstants;

import java.util.concurrent.TimeUnit;

/**
 * Created by Shubham_Aggarwal03 on 8/8/2018.
 */

public class RetrofitHelper {

    private static Retrofit retrofit;
    private static final int TIMEOUTINSECONDS = 10;

    public static Retrofit getInstance(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);
        String BASE_URL = prefs.getString(CommonConstants.BASE_URL_SP, "https://jsonplaceholder.typicode.com/");


        if(retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUTINSECONDS, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUTINSECONDS, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUTINSECONDS, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            return  retrofit;
        }
        return  retrofit;
    }

}
