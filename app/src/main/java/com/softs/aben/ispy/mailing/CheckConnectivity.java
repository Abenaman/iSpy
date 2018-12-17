package com.softs.aben.ispy.mailing;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;


/**
 * Created by HCTE on 1/10/2017.
 */
public class CheckConnectivity extends BroadcastReceiver  {
    @Override
    public void onReceive(Context context, Intent arg1) {

        boolean isConnected = arg1.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
        if(isConnected){
            Toast.makeText(context, "Internet Connection Lost", Toast.LENGTH_LONG).show();
        }
        else{
            Intent service_intent = new Intent(context, MySendLogService.class);
            context.startService(service_intent);
            Toast.makeText(context, "Internet Connected", Toast.LENGTH_LONG).show();


        }
    }
}