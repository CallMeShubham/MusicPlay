package com.music.play.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Shubham_Aggarwal03 on 8/31/2018.
 */

public class Album implements Serializable{
    public int userId;
    public int id;
    public String title;
    public String errorMessage;
}
