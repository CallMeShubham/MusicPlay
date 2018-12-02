package com.music.play.response;

import okhttp3.ResponseBody;

/**
 * Created by Shubham_Aggarwal03 on 9/11/2018.
 */

public class FailureResponse {

    public String failureMessage;
    public ResponseBody responseBody;
    public Throwable exception;
}
