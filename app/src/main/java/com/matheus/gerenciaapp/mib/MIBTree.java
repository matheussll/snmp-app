package com.matheus.gerenciaapp.mib;

import android.util.Log;

import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import java.util.*;


public class MIBTree {

    public static final OID sysError = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 1});
    public static final OID sysUptime = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 1, 2});


    public static final OID acPower = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 1});
    public static final OID acTemperature = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 2});

    public static final OID acMode = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 3});

    public static final OID acFanTable = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4});
    public static final OID acFanEntry = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1});

    public static final OID acFanEntrySpeed = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 1});
    public static final OID acFanEntrySpeed_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 1, 1});
    public static final OID acFanEntrySpeed_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 1, 2});
    public static final OID acFanEntrySpeed_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 1, 3});

    public static final OID acFanEntryDirection = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 2});
    public static final OID acFanEntryDirection_1 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 2, 1});
    public static final OID acFanEntryDirection_2 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 2, 2});
    public static final OID acFanEntryDirection_3 = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 4, 1, 2, 3});

    public static final OID acDisplay = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 5});
    public static final OID acTurbo = new OID(new int[]{1, 3, 6, 1, 4, 1, 1, 2, 6});

    //public static MIBTree mib = new MIBTree();
    public List<MIBNode> mibNodes = new ArrayList<MIBNode>();

    private MIBNode root;
    public MIBNode sysErrorNode;
    public MIBNode sysUptimeNode;

    public MIBNode acFanEntrySpeedNode_1, acFanEntrySpeedNode_2, acFanEntrySpeedNode_3;
    public MIBNode acFanEntryDirectionNode_1, acFanEntryDirectionNode_2, acFanEntryDirectionNode_3;

    public MIBNode acPowerNode;
    public MIBNode acTemperatureNode;
    public MIBNode acModeNode;
    public MIBNode acDisplayNode;
    public MIBNode acTurboNode;

    private class MIBNode {
        public VariableBinding variableBinding;
        public MIBNode parent = null;
        public List<MIBNode> children = null;


        public MIBNode(VariableBinding vb, Integer32 data, MIBNode parent, List<MIBNode> children) {
            this.variableBinding = vb;
            this.variableBinding.setVariable(data);
            this.parent = parent;
            this.children = children;
        }
    }


    public MIBTree() {

        root = null;

        sysErrorNode = new MIBNode(new VariableBinding(sysError), new Integer32(), root, null);
        sysUptimeNode = new MIBNode(new VariableBinding(sysUptime), new Integer32(), root, null);

        // table
        acFanEntrySpeedNode_1 = new MIBNode(new VariableBinding(acFanEntrySpeed_1), new Integer32(), root, null);
        acFanEntryDirectionNode_1 = new MIBNode(new VariableBinding(acFanEntryDirection_1), new Integer32(), root, null);
        acFanEntrySpeedNode_2 = new MIBNode(new VariableBinding(acFanEntrySpeed_2), new Integer32(), root, null);
        acFanEntryDirectionNode_2 = new MIBNode(new VariableBinding(acFanEntryDirection_2), new Integer32(), root, null);
        acFanEntrySpeedNode_3 = new MIBNode(new VariableBinding(acFanEntrySpeed_3), new Integer32(), root, null);
        acFanEntryDirectionNode_3 = new MIBNode(new VariableBinding(acFanEntryDirection_3), new Integer32(), root, null);

        acPowerNode = new MIBNode(new VariableBinding(acPower), new Integer32(), root, null);
        acTemperatureNode = new MIBNode(new VariableBinding(acTemperature), new Integer32(), root, null);
        acModeNode = new MIBNode(new VariableBinding(acMode), new Integer32(), root, null);
        acDisplayNode = new MIBNode(new VariableBinding(acDisplay), new Integer32(), root, null);
        acTurboNode = new MIBNode(new VariableBinding(acTurbo), new Integer32(), root, null);


        mibNodes.add(sysErrorNode);
        mibNodes.add(sysUptimeNode);

        mibNodes.add(acFanEntrySpeedNode_1);
        mibNodes.add(acFanEntryDirectionNode_1);
        mibNodes.add(acFanEntrySpeedNode_2);
        mibNodes.add(acFanEntryDirectionNode_2);
        mibNodes.add(acFanEntrySpeedNode_3);
        mibNodes.add(acFanEntryDirectionNode_3);

        mibNodes.add(acPowerNode);
        mibNodes.add(acTemperatureNode);
        mibNodes.add(acModeNode);
        mibNodes.add(acDisplayNode);
        mibNodes.add(acTurboNode);
    }

    public VariableBinding get(OID oid) {
        Log.i("gerencia-app", "MIBTree get variableBinding: " + oid);
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                response = mibNodes.get(i).variableBinding;
            }
        }
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding getnext(OID oid) {
        Log.i("gerencia-app", "MIBTree get variableBinding: " + oid);
        //ListIterator<MIBNode> listIterator = mibNodes.listIterator();
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            //listIterator.next();
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                if (i == (mibNodes.size() - 1)) {
                    response = mibNodes.get(0).variableBinding;
                } else {
                    response = mibNodes.get(i + 1).variableBinding;
                }
            }
        }
        Log.i("gerencia-app", "getnext response: " + response);
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding set(VariableBinding vb) {
        Log.i("gerencia-app", "MIBTree set variableBinding: " + vb);
        VariableBinding response = new VariableBinding();
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(vb.getOid())) {
                mibNodes.get(i).variableBinding.setVariable(vb.getVariable());
                response = mibNodes.get(i).variableBinding;
            }
        }
        return response;
        //return mibNodes.get(0).variableBinding;
    }

    public VariableBinding getValue(OID oid) {
        for (int i = 0; i < mibNodes.size(); i++) {
            if (mibNodes.get(i).variableBinding.getOid().equals(oid)) {
                return mibNodes.get(i).variableBinding;
            }
        }
        return new VariableBinding();
    }
}
