package com.matheus.gerenciaapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.matheus.gerenciaapp.Manager;
import com.matheus.gerenciaapp.R;
import com.matheus.gerenciaapp.adapters.ConsoleAdapter;

import java.util.ArrayList;

public class ConsoleActivity  extends AppCompatActivity {

    public ConsoleAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.console_activity);

        ArrayList<String> responses = (ArrayList<String>) Manager.stringResponse;

        mAdapter = new ConsoleAdapter(responses);

        RecyclerView rvConsole = findViewById(R.id.consoleList);
        rvConsole.setAdapter(mAdapter);
        rvConsole.setLayoutManager(new LinearLayoutManager(this));
    }
}
