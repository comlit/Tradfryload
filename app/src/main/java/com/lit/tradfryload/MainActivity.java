package com.lit.tradfryload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.FileNotFoundException;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.device.Gateway;
import nl.stijngroenen.tradfri.util.Credentials;

public class MainActivity extends AppCompatActivity
{
    Button testbtn;
    SharedPreferences prefs;
    Credentials creds;
    Gateway gateway;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testbtn = findViewById(R.id.button);

        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        creds = new Credentials(prefs.getString("identity", null), prefs.getString("key", null));
        if(creds.getIdentity() == null || creds.getKey() == null)
        {
            try {
                creds = Tradfry.newCreds();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("identity", creds.getIdentity());
            editor.putString("key", creds.getKey());
            editor.apply();
        }

        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                gateway.connect(creds);
                gateway.getDevices();
                System.out.println("DeviceNames:");
                for(Device dev: gateway.getDevices())
                    System.out.println(dev.getName());
            }
        });
    }
}