package com.lit.tradfryload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class ChargeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        //Now check if user is charging here. This will only run when device is either plugged in or unplugged.
        Toast.makeText(context, "SDILugsdliv", Toast.LENGTH_SHORT).show();
        Intent intnt = new Intent(context, ChargeService.class);
        context.startService(intnt);
    }
}