package com.matheus.gerenciaapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.matheus.gerenciaapp.MainActivity;
import com.matheus.gerenciaapp.Manager;
import com.matheus.gerenciaapp.R;
import com.matheus.gerenciaapp.interfaces.ManagerCallback;

import org.snmp4j.smi.VariableBinding;

public class DeviceActivity extends AppCompatActivity implements ManagerCallback {

    private TextView power;
    private TextView mode;
    private TextView temperature;
    private TextView turbo;
    private TextView display;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_activity);

        power = findViewById(R.id.devicePower);
        mode = findViewById(R.id.deviceMode);
        temperature = findViewById(R.id.deviceTemperature);
        turbo = findViewById(R.id.deviceTurbo);
        display = findViewById(R.id.deviceDisplay);

        Manager manager = new Manager("127.0.0.1", "13579", getApplicationContext());
        manager.sendGetRequest(MainActivity.mib.acPower, this);
        manager.sendGetRequest(MainActivity.mib.acMode, this);
        manager.sendGetRequest(MainActivity.mib.acTemperature, this);
        manager.sendGetRequest(MainActivity.mib.acTurbo, this);
        manager.sendGetRequest(MainActivity.mib.acDisplay, this);

    }

    @Override
    public void onRequestFinished() {
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
}
