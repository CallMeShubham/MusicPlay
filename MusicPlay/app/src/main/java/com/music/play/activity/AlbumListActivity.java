package com.music.play.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.music.play.R;
import com.music.play.adapter.AlbumListAdapter;
import com.music.play.response.Album;
import com.music.play.response.FailureResponse;
import com.squareup.otto.Subscribe;
import com.music.play.network.ApiFailureHandlerInterface;
import com.music.play.service.MusicPlayIntentService;
import com.music.play.utils.CommonConstants;
import com.music.play.utils.CommonUtils;
import com.music.play.utils.ServiceBus;

import java.util.ArrayList;
import java.util.Collections;

public class AlbumListActivity extends AppCompatActivity implements ApiFailureHandlerInterface{

    private SharedPreferences prefs;
    private ProgressDialog progressDialog;
    private ServiceBus bus;
    private AlertDialog dialog;
    private ListView list;
    private Gson mGson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog = new ProgressDialog(this);
        dialog = CommonUtils.getAlert(this, "", "", getString(R.string.ok));
        setContentView(R.layout.activity_albums_list);
        bus = ServiceBus.getInstance();
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list = findViewById(R.id.albums_list);
        mGson = new Gson();
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final ArrayList<Album> albumList = mGson.fromJson(prefs.getString(CommonConstants.ALBUM_LIST_SP, null), new TypeToken<ArrayList<Album>>() {}.getType());
        if(albumList != null) {
            list.setAdapter(new AlbumListAdapter(this, albumList));
        } else {
            getAlbumList();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getAlbumList() {
        if(!CommonUtils.isNetConnected(this)) {
            showBadInternetConnectionAlert(this);
            return;
        }
        progressDialog.show();
        Intent i = new Intent(this, MusicPlayIntentService.class);
        i.putExtra(MusicPlayIntentService.EXTRA_REQUEST,
                MusicPlayIntentService.REQUEST_GET_ALBUM_LIST);
        startService(i);
    }

    private void showBadInternetConnectionAlert(Context context) {
        dialog.setMessage(context.getString(R.string.bad_internet_connection));
        if(!dialog.isShowing())
            dialog.show();
    }

    @Subscribe
    public void albumListAvailable(ArrayList<Album> albumList) {
        progressDialog.dismiss();
        if(albumList != null) {
            Collections.sort(albumList, Album.comparator);
            list.setAdapter(new AlbumListAdapter(this, albumList));
            prefs.edit().putString(CommonConstants.ALBUM_LIST_SP,mGson.toJson(albumList)).commit();
        }

    }

    @Override
    public void onFailureResponse(FailureResponse response) {
        if(progressDialog != null)
            progressDialog.dismiss();
        dialog.setMessage(response.failureMessage);
        if(!dialog.isShowing())
            dialog.show();
    }
}
