package com.music.play.network;

import com.music.play.response.FailureResponse;

/**
 * Created by Shubham_Aggarwal03 on 9/11/2018.
 */

public interface ApiFailureHandlerInterface {
    void onFailureResponse(FailureResponse response);
}
