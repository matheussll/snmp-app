package com.matheus.gerenciaapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.matheus.gerenciaapp.activities.ConsoleActivity;
import com.matheus.gerenciaapp.adapters.ConsoleAdapter;
import com.matheus.gerenciaapp.interfaces.ManagerCallback;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.event.ResponseListener;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class Manager extends AppCompatActivity{

    private String ip;
    private String port;
    private CommunityTarget target;
    public Context context;
    public static List<String> stringResponse = new ArrayList<>();
    public static List<String> stringAction = new ArrayList<>();
    private int index = 0;

    public Manager(String ip, String port, Context context){

        target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setVersion(SnmpConstants.version1);
        target.setTimeout(1000);
        target.setRetries(1);
        target.setAddress(new UdpAddress(ip + "/" + port));

        this.context = context;

    }

    public static Vector<Vector<? extends VariableBinding>> lastResponse = new Vector<>();

    public static Vector<VariableBinding> allParsedValues = new Vector<>();

    public void sendGetRequest(final OID oid) {
        sendGetRequest(oid, null);
    }
    public void sendGetRequest(final OID oid, @Nullable ManagerCallback callback) {

        final PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GET);

        new Thread(new Runnable(){
            @Override
            public void run() {

                Snmp snmp = null;
                try {
                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    ResponseListener responseListener = new ResponseListener() {
                        @Override
                        public void onResponse(ResponseEvent responseEvent) {
                            ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                            PDU response = responseEvent.getResponse();

                            if(response != null){
                                if(response.getErrorStatus() == PDU.noError){
                                    lastResponse.add(response.getVariableBindings());
                                    stringAction.add("get");
                                    showInfo();

                                    allParsedValues.add(response.getVariableBindings().lastElement());

                                    Vector<VariableBinding> newVariables = new Vector<>();

                                    Collections.reverse(allParsedValues);

                                    for(VariableBinding variable: allParsedValues) {
                                        boolean exists = false;
                                        for (VariableBinding newVar: newVariables) {
                                            if (variable.getOid().toString().equals(newVar.getOid().toString())) {
                                                exists = true;
                                            }
                                        }
                                        if (!exists) {
                                            newVariables.add(variable);
                                        }
                                    }

                                    allParsedValues = newVariables;

                                    if (callback != null) {
                                        callback.onRequestFinished();
                                    }
                                } else{
                                    Log.i("gerencia-app", "sendGetRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                }
                            }
                        }
                    };

                    snmp.send(pdu, target, null, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendGetNextRequest(final OID oid) {

        final PDU pdu = new PDU();
        pdu.add(new VariableBinding(new OID(oid)));
        pdu.setType(PDU.GETNEXT);

        new Thread(new Runnable(){
            @Override
            public void run() {

                Snmp snmp = null;
                try {
                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    ResponseListener responseListener = new ResponseListener() {
                        @Override
                        public void onResponse(ResponseEvent responseEvent) {
                            ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                            PDU response = responseEvent.getResponse();
                            if(response != null){
                                if(response.getErrorStatus() == PDU.noError){
                                    lastResponse.add(response.getVariableBindings());
                                    stringAction.add("getnext");
                                    showInfo();
                                } else{
                                    Log.i("gerencia-app", "sendGetNextRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                }
                            }
                        }
                    };

                    snmp.send(pdu, target, null, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendSetRequest(final VariableBinding vb) {
        final PDU pdu = new PDU();
        pdu.add(vb);
        pdu.setType(PDU.SET);

        new Thread(new Runnable(){
            @Override
            public void run() {

                Snmp snmp = null;
                try {
                    snmp = new Snmp(new DefaultUdpTransportMapping());
                    snmp.listen();

                    ResponseListener responseListener = new ResponseListener() {
                        @Override
                        public void onResponse(ResponseEvent responseEvent) {
                            ((Snmp) responseEvent.getSource()).cancel(responseEvent.getRequest(), this);
                            PDU response = responseEvent.getResponse();
                            if(response != null){
                                if(response.getErrorStatus() == PDU.noError){
                                    lastResponse.add(response.getVariableBindings());
                                    stringAction.add("set");
                                    showInfo();
                                } else{
                                    Log.i("gerencia-app", "sendSetRequest onResponse PDU with error - response: " + response.getVariableBindings());
                                }
                            }
                        }
                    };

                    snmp.send(pdu, target, null, responseListener);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void showInfo(){
        parseResponse();
        index = stringResponse.size();
        Log.i("gerencia-app", "allParsedValues " + allParsedValues);
    }



    private void parseResponse() {
        ArrayList<String> resp = new ArrayList<>();
        for(int i = 0; i < lastResponse.size(); i++){
            String oid = lastResponse.get(i).get(0).getOid().toString();
            String value = lastResponse.get(i).get(0).getVariable().toString();
            resp.add(stringAction.get(i) + " " + oid + " = " + value);
        }
        stringResponse = resp;
    }
}


