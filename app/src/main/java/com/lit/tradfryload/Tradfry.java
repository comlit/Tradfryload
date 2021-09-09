package com.lit.tradfryload;

import android.os.AsyncTask;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.device.Gateway;
import nl.stijngroenen.tradfri.util.Credentials;

public class Tradfry
{
    String gwIP = "192.168.178.27";
    String code = "UlBKejOQzM99rnL0";
    Gateway gateway;

    public Tradfry()
    {
        gateway = new Gateway("192.168.178.27");
    }

    public void test()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Gateway gateway = new Gateway(gwIP);
                Credentials credentials = gateway.connect(code);
                String identity = credentials.getIdentity();
                String key = credentials.getKey();
                System.out.println("Identity: " + identity + " Key: " + key);
                gateway.getDevice(65560).toPlug().setOn(false);
            }
        });
    }

    public void init(String ident, String key)
    {
        Credentials cr = new Credentials();
        cr.setIdentity(ident);
        cr.setKey(key);
        gateway.connect(cr);
    }

    public void turnOff()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                gateway.getDevice(65560).toPlug().setOn(false);
            }
        });
    }

    public void turnOn()
    {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                gateway.getDevice(65560).toPlug().setOn(true);
            }
        });
    }

    public Credentials newCreds()
    {
        Gateway gateway = new Gateway(gwIP);
        Credentials credentials = gateway.connect(code);
        return credentials;
    }
}
