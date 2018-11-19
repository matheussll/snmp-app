package com.matheus.gerenciaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.matheus.gerenciaapp.activities.ConsoleActivity;
import com.matheus.gerenciaapp.activities.DeviceActivity;
import com.matheus.gerenciaapp.activities.MibActivity;
import com.matheus.gerenciaapp.mib.MIBTree;

public class MainActivity extends AppCompatActivity {
    public static MIBTree mib = new MIBTree();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button b1 = findViewById(R.id.b1);
        final Button b2 = findViewById(R.id.b2);
        final Button b3 = findViewById(R.id.b3);

        startService(new Intent(this, Agent.class));

        final Intent consoleIntent = new Intent(this, ConsoleActivity.class);
        final Intent mibIntent = new Intent(this, MibActivity.class);
        final Intent deviceIntent = new Intent(this, DeviceActivity.class);


        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(mibIntent);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(deviceIntent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(consoleIntent);
            }
        });
    }

}
