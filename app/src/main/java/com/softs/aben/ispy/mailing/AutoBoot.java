package com.softs.aben.ispy.mailing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.softs.aben.ispy.LoginActivity;

/**
 * Created by HCTE on 1/10/2017.
 */
public class AutoBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}