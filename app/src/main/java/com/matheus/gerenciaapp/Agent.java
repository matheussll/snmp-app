package com.matheus.gerenciaapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.matheus.gerenciaapp.mib.MIBTree;

import org.snmp4j.CommandResponder;
import org.snmp4j.CommandResponderEvent;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import java.io.IOException;


public class Agent extends Service implements CommandResponder {

    private String SNMP_PORT = "13579";
    private Snmp snmp;
    public MIBTree mib = MainActivity.mib;

    @Override
    public void onCreate(){
        new AgentListener().start();

    }

    @Override
    public synchronized void processPdu(CommandResponderEvent commandResponderEvent) {
        PDU command = (PDU) commandResponderEvent.getPDU().clone();
        if (command != null) {
            if (command.getType() == PDU.GET){
                handleGetRequest(command);
            } else if(command.getType() == PDU.GETNEXT){
                handleGetNextRequest(command);
            } else if (command.getType() == PDU.SET) {
                handleSetRequest(command);
            }
            Address address = commandResponderEvent.getPeerAddress();
            sendResponse(address, command);
        }
    }

    private void sendResponse(Address address, PDU command) {
        command.setType(PDU.RESPONSE);
        CommunityTarget target = new CommunityTarget();
        target.setCommunity(new OctetString("public"));
        target.setAddress(address);
        target.setRetries(0);
        target.setTimeout(1000);
        target.setVersion(SnmpConstants.version1);

        try{
            snmp.send(command, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleGetRequest(PDU command) {
        VariableBinding varBind;
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            varBind.setVariable(mib.get(varBind.getOid()).getVariable());
        }

    }

    private void handleGetNextRequest(PDU command) {
        VariableBinding varBind;
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            VariableBinding responseVB = mib.getnext(varBind.getOid());
            varBind.setOid(responseVB.getOid());
            varBind.setVariable(responseVB.getVariable());
        }
    }

    private void handleSetRequest(PDU command) {
        VariableBinding varBind;
        for(int i = 0; i < command.size(); i++){
            varBind = command.get(i);
            mib.set(varBind);
        }
    }

    private class AgentListener extends Thread{
        public void run(){
            try {

                UdpAddress address = new UdpAddress("127.0.0.1/" + SNMP_PORT);
                TransportMapping transport = new DefaultUdpTransportMapping(address);

                snmp = new Snmp(transport);
                snmp.addNotificationListener(transport, address, Agent.this);
                snmp.listen();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
