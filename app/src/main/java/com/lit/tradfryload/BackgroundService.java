package com.lit.tradfryload;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class BackgroundService extends Service
{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //Add broadcast receiver for plugging in and out here
        ChargeDetection chargeDetector = new ChargeDetection(); //This is the name of the class at the bottom of this code.

        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        //filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        this.registerReceiver(chargeDetector, filter);

        return super.onStartCommand(intent, flags, startId);
    }
}

class ChargeDetection extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        //Now check if user is charging here. This will only run when device is either plugged in or unplugged.
        Toast.makeText(context, "SDILugsdliv", Toast.LENGTH_SHORT).show();
        Intent intnt = new Intent(context, ChargeService.class);
        context.startService(intnt);
    }
}
