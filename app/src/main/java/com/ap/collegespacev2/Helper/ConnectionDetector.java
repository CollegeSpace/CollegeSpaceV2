package com.ap.collegespacev2.Helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ap.collegespacev2.R;

/**
 * Created by amaneureka on 30-Jan-16.
 */
public class ConnectionDetector
{
    private Context con;
    private boolean isConnected;

    public ConnectionDetector(Context context) {
        this.con = context;
    }

    public boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public void Check() {
        isConnected = isConnectingToInternet();
        if(isConnected != true) {
            makeAlert();
        }
    }

    public boolean isMobile() {
        ConnectivityManager cm = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    public void makeAlert() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(con);
        alertDialogBuilder.setTitle(R.string.dialog_inernet_title);
        alertDialogBuilder
                .setMessage(R.string.dialog_internet_content)
                .setCancelable(false)
                .setPositiveButton(R.string.btn_repeat,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        Check();
                    }})
                .setNegativeButton(R.string.btn_close,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }});
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
