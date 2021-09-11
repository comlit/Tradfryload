package com.lit.tradfryload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import nl.stijngroenen.tradfri.device.Device;
import nl.stijngroenen.tradfri.util.Credentials;

public class MainActivity extends AppCompatActivity
{
    Button testbtn, newbtn;
    TextView text;
    SeekBar bar;
    Switch witch;
    SharedPreferences prefs;
    SharedPreferences.Editor edit;
    int prog;
    boolean onoff;
    String ident, key, nochGutBis;
    Tradfry trad;


    public MainActivity() throws FileNotFoundException
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initPrefs();
        initTrad();


        initListeners();
        Intent intent = new Intent(this, BackgroundService.class);
        //startService(intent);
    }

    public void initTrad()
    {
        trad = new Tradfry();
        if(ident == "" || key == "" || LocalDateTime.parse(nochGutBis).isBefore(LocalDateTime.now()))
            newCreds();
        trad.init(ident, key);
        nochGutBis = LocalDateTime.now().plusDays(40).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public void newCreds()
    {
        Credentials cred = trad.newCreds();
        ident = cred.getIdentity();
        key = cred.getKey();
        System.out.println("Hole neue Credentials!!!!!!!! \n\n\n");
    }

    public void initPrefs()
    {
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        edit = prefs.edit();
        prog = prefs.getInt("pimmel", 80);
        onoff = prefs.getBoolean("onoff", true);
        ident = prefs.getString("identity", "");
        key = prefs.getString("key", "");
        nochGutBis = prefs.getString("nochGutBis", "01/01/2000");

        text.setText("Laden bis: " + prog + "%");
        bar.setProgress(prog);
        witch.setChecked(onoff);
    }

    public void savePrefs()
    {
        edit.putInt("pimmel", prog);
        edit.putBoolean("onoff", onoff);
        edit.putString("identity", ident);
        edit.putString("key", key);
        edit.putString("nochGutBis", nochGutBis);
        edit.apply();
    }

    public void initListeners()
    {
        newbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trad.turnOn();
            }
        });
        testbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                trad.turnOff();
            }
        });

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                text.setText("Laden bis: " + Integer.toString(progress) + "%");
                prog = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        witch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onoff = isChecked;
            }
        });
    }

    public void initViews()
    {
        testbtn = findViewById(R.id.button);
        text = findViewById(R.id.textView2);
        bar = findViewById(R.id.seekBar);
        witch = findViewById(R.id.switch2);
        newbtn = findViewById(R.id.button2);
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        savePrefs();
    }

}