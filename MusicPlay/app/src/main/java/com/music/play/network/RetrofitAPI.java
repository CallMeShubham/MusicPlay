package com.music.play.network;

import com.music.play.response.Album;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Shubham_Aggarwal03 on 8/8/2018.
 */

public interface RetrofitAPI {

    @GET("albums")
    Call<ArrayList<Album>> getAlbumsList();

}
