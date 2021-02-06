package com.example.simpleintentsevice2021;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SimpleIntentService extends IntentService {
    public static volatile boolean shouldStop=false;
    public  static final String ACTION_1="My_action";
    public static final String PARAM_PERCENT="percent";
    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Intent broadCastIntent=new Intent();
        broadCastIntent.setAction(SimpleIntentService.ACTION_1);
        for (int i = 0; i <=100 ; i++) {
            broadCastIntent.putExtra(PARAM_PERCENT,i);
            sendBroadcast(broadCastIntent);
            SystemClock.sleep(100);
            if(shouldStop)
            {
                stopSelf();
                return;
            }
        }
    }
}