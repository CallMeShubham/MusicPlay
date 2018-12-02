package com.music.play.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Shubham_Aggarwal03 on 8/31/2018.
 */

public class Album implements Serializable{
    public int userId;
    public int id;
    public String title;

    public static Comparator<Album> comparator = new Comparator<Album>() {
        @Override
        public int compare(Album t1, Album t2) {
            String albumName1 = t1.title;
            String albumName2 = t2.title;
            return albumName1.compareTo(albumName2);
        }
    };

}
