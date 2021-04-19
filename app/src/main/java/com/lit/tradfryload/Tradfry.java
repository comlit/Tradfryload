package com.lit.tradfryload;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.device.Gateway;
import nl.stijngroenen.tradfri.util.Credentials;

public class Tradfry
{
    String gwIP;
    Gateway gateway;
    public Tradfry(String ip) throws FileNotFoundException
    {
        gwIP = ip;
        gateway = new Gateway(gwIP);
        //login(newCreds());
    }

    public Credentials newCreds() throws FileNotFoundException
    {
        System.out.println("Gettin new Creds");
        return gateway.connect("UlBKejOQzM99rnL0");
    }

    public void login(Credentials creds)
    {
        System.out.println("Loggin in");
        gateway.connect(creds);
    }

    public void run() throws FileNotFoundException
    {
        login(newCreds());
        System.out.println("Printing DeviceNames:");
        for(Device dev: gateway.getDevices())
            System.out.println(dev.getName());
        turn9off();
    }

    public ArrayList<Device> getDevices()
    {
        ArrayList<Device> list = new ArrayList<>();
        for(Device dev: gateway.getDevices())
            list.add(dev);
        return list;
    }

    public void test()
    {
        gateway.getDevices()[4].toLight().setOn(false);
    }

    public  void turnOn(int dev)
    {
        gateway.getDevices()[dev].toLight().setOn(true);
    }

    public  void turnOff(int dev)
    {
        gateway.getDevices()[dev].toLight().setOn(false);
    }

    public void turn9on(){gateway.getDevices()[9].toLight().setOn(true);};

    public void turn9off(){gateway.getDevices()[9].toLight().setOn(false);};
}
