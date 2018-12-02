package com.music.play.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.widget.Toast;

import com.music.play.R;

/**
 * Created by Shubham_Aggarwal03 on 8/2/2018.
 */

public class CommonUtils {

    public static void showToast(Context ctx, String str) {
        Toast toast = Toast.makeText(ctx, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static AlertDialog getAlert(Context ctx, String subject, String message, String buttonText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx,
                R.style.Theme_AppCompat_Light_Dialog_Alert))
                .setMessage(message).setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        return builder.create();
    }


    public static boolean isNetConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return  isConnected;
    }

}
