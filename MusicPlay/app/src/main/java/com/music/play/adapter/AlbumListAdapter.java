package com.music.play.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.music.play.R;
import com.music.play.response.Album;

import java.util.List;

/**
 * Created by Shubham_Aggarwal03 on 10/9/2018.
 */

public class AlbumListAdapter extends BaseAdapter {

    private List<Album> list;
    private final Context ctx;

    public AlbumListAdapter(Context ctx, List<Album> AlbumList) {
        this.list = AlbumList;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(this.ctx);
        if(view == null) {
            view = inflater.inflate(R.layout.activity_listview
                    , viewGroup, false);
        }

        TextView userId = view.findViewById(R.id.album_user_id);
        userId.setText("User Id: "+list.get(i).userId);

        TextView id = view.findViewById(R.id.album_id);
        id.setText("Id: "+list.get(i).id);

        TextView title = view.findViewById(R.id.album_title);
        title.setText("Title:" + list.get(i).title);

        return view;
    }

}

