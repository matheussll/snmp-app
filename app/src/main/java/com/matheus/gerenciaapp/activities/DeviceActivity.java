package com.matheus.gerenciaapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.matheus.gerenciaapp.MainActivity;
import com.matheus.gerenciaapp.Manager;
import com.matheus.gerenciaapp.R;

import org.snmp4j.smi.VariableBinding;

public class DeviceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity);

        TextView power = findViewById(R.id.devicePower);
        TextView mode = findViewById(R.id.deviceMode);
        TextView temperature = findViewById(R.id.deviceTemperature);
        TextView turbo = findViewById(R.id.deviceTurbo);
        TextView display = findViewById(R.id.deviceDisplay);

        Manager manager = new Manager("127.0.0.1", "13579", getApplicationContext());
        manager.sendGetRequest(MainActivity.mib.acPower);
        manager.sendGetRequest(MainActivity.mib.acMode);
        manager.sendGetRequest(MainActivity.mib.acTemperature);
        manager.sendGetRequest(MainActivity.mib.acTurbo);
        manager.sendGetRequest(MainActivity.mib.acDisplay);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(VariableBinding variable: Manager.allParsedValues) {
                    if (variable.getOid().toString().equals(MainActivity.mib.acPower.toString())) {
                        power.setText("Ligado: " + variable.getVariable().toString());
                    } else if (variable.getOid().toString().equals(MainActivity.mib.acMode.toString())) {
                        mode.setText("Modo: " + variable.getVariable().toString());
                    } else if (variable.getOid().toString().equals(MainActivity.mib.acTemperature.toString())) {
                        temperature.setText("Temperatura: " + variable.getVariable().toString());
                    } else if (variable.getOid().toString().equals(MainActivity.mib.acTurbo.toString())) {
                        turbo.setText("Turbo: " + variable.getVariable().toString());
                    } else if (variable.getOid().toString().equals(MainActivity.mib.acDisplay.toString())) {
                        display.setText("Display: " + variable.getVariable().toString());
                    }
                }
            }
        }, 2000);
    }
}
