package com.matheus.gerenciaapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;

import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.matheus.gerenciaapp.MainActivity;
import com.matheus.gerenciaapp.Manager;
import com.matheus.gerenciaapp.R;

import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

public class MibActivity  extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private OID selectedNode;
    private String selectedEntryNode;
    private Manager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager = new Manager("127.0.0.1", "13579", getApplicationContext());

        setContentView(R.layout.mib_activity);

        final LinearLayout system = (LinearLayout) this.findViewById(R.id.system);
        final LinearLayout sysError = (LinearLayout) this.findViewById(R.id.sysError);
        final LinearLayout sysUptime = (LinearLayout) this.findViewById(R.id.sysUptime);
        final LinearLayout airConditioner = (LinearLayout) this.findViewById(R.id.airConditioner);
        final LinearLayout acPower = (LinearLayout) this.findViewById(R.id.acPower);
        final LinearLayout acTemperature = (LinearLayout) this.findViewById(R.id.acTemperature);
        final LinearLayout acMode = (LinearLayout) this.findViewById(R.id.acMode);
        final LinearLayout acFanTable = (LinearLayout) this.findViewById(R.id.acFanTable);
        final LinearLayout acFanEntry = (LinearLayout) this.findViewById(R.id.acFanEntry);
        final LinearLayout acFanEntrySpeed = (LinearLayout) this.findViewById(R.id.acFanEntrySpeed);
        final LinearLayout acFanEntryDirection = (LinearLayout) this.findViewById(R.id.acFanEntryDirection);
        final LinearLayout acDisplay = (LinearLayout) this.findViewById(R.id.acDisplay);
        final LinearLayout acTurbo = (LinearLayout) this.findViewById(R.id.acTurbo);

        sysError.setVisibility(View.GONE);
        sysUptime.setVisibility(View.GONE);
        acPower.setVisibility(View.GONE);
        acTemperature.setVisibility(View.GONE);
        acMode.setVisibility(View.GONE);
        acFanTable.setVisibility(View.GONE);
        acFanEntry.setVisibility(View.GONE);
        acFanEntrySpeed.setVisibility(View.GONE);
        acFanEntryDirection.setVisibility(View.GONE);
        acDisplay.setVisibility(View.GONE);
        acTurbo.setVisibility(View.GONE);

        system.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sysError.getVisibility() == View.VISIBLE) {
                    sysError.setVisibility(View.GONE);
                    sysUptime.setVisibility(View.GONE);
                } else {
                    sysError.setVisibility(View.VISIBLE);
                    sysUptime.setVisibility(View.VISIBLE);
                }
            }
        });

        airConditioner.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (acPower.getVisibility() == View.VISIBLE) {
                    acPower.setVisibility(View.GONE);
                    acTemperature.setVisibility(View.GONE);
                    acMode.setVisibility(View.GONE);
                    acFanTable.setVisibility(View.GONE);
                    acFanEntry.setVisibility(View.GONE);
                    acFanEntrySpeed.setVisibility(View.GONE);
                    acFanEntryDirection.setVisibility(View.GONE);
                    acDisplay.setVisibility(View.GONE);
                    acTurbo.setVisibility(View.GONE);
                } else {
                    acPower.setVisibility(View.VISIBLE);
                    acTemperature.setVisibility(View.VISIBLE);
                    acMode.setVisibility(View.VISIBLE);
                    acFanTable.setVisibility(View.VISIBLE);
                    acDisplay.setVisibility(View.VISIBLE);
                    acTurbo.setVisibility(View.VISIBLE);
                }
            }
        });

        acFanTable.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (acFanEntry.getVisibility() == View.VISIBLE) {
                    acFanEntry.setVisibility(View.GONE);
                    acFanEntrySpeed.setVisibility(View.GONE);
                    acFanEntryDirection.setVisibility(View.GONE);
                } else {
                    acFanEntry.setVisibility(View.VISIBLE);
                }
            }
        });

        acFanEntry.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (acFanEntrySpeed.getVisibility() == View.VISIBLE) {
                    acFanEntrySpeed.setVisibility(View.GONE);
                    acFanEntryDirection.setVisibility(View.GONE);
                } else {
                    acFanEntrySpeed.setVisibility(View.VISIBLE);
                    acFanEntryDirection.setVisibility(View.VISIBLE);
                }
            }
        });

        sysError.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.sysError;
                showPopup(v);
            }
        });

        sysUptime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.sysUptime;
                showPopup(v);
            }
        });

        acPower.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.acPower;
                showPopup(v);
            }
        });

        acTemperature.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.acTemperature;
                showPopup(v);
            }
        });

        acMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.acMode;
                showPopup(v);
            }
        });

        acDisplay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.acDisplay;
                showPopup(v);
            }
        });

        acTurbo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedNode = MainActivity.mib.acTurbo;
                showPopup(v);
            }
        });

        acFanEntrySpeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedEntryNode = "speed";
                showSelectionPopup(v);
            }
        });

        acFanEntryDirection.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectedEntryNode = "direction";
                showSelectionPopup(v);
            }
        });
    }

    public void showSelectionPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.layout.popup_selection, popup.getMenu());
        popup.setOnMenuItemClickListener(new SelectionPopupListener(v));
        popup.show();
    }

    private class SelectionPopupListener implements PopupMenu.OnMenuItemClickListener {

        View v;
        public SelectionPopupListener(View v) {
            this.v = v;
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.instanceOne:
                    selectedNode = selectedEntryNode.equals("speed") ? MainActivity.mib.acFanEntrySpeed_1 : MainActivity.mib.acFanEntryDirection_1;
                    showPopup(v);
                    return true;
                case R.id.instanceTwo:
                    selectedNode = selectedEntryNode.equals("speed") ? MainActivity.mib.acFanEntrySpeed_2 : MainActivity.mib.acFanEntryDirection_2;
                    showPopup(v);
                    return true;
                case R.id.instanceThree:
                    selectedNode = selectedEntryNode.equals("speed") ? MainActivity.mib.acFanEntrySpeed_3 : MainActivity.mib.acFanEntryDirection_3;
                    showPopup(v);
                    return true;
                default:
                    return false;
            }
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.layout.popup_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        final Intent intent = new Intent(this, ConsoleActivity.class);

        switch (item.getItemId()) {
            case R.id.get:
                manager.sendGetRequest(selectedNode);
                startActivity(intent);
                return true;

            case R.id.getNext:
                manager.sendGetNextRequest(selectedNode);
                startActivity(intent);
                return true;

            case R.id.set:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Enter value");
                View viewInflated = LayoutInflater.from(this).inflate(R.layout.text_input_value, (ViewGroup) findViewById(android.R.id.content), false);

                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        int value = Integer.parseInt(input.getText().toString());
                        VariableBinding targetVB = new VariableBinding();
                        targetVB.setOid(selectedNode);
                        targetVB.setVariable(new Integer32(value));
                        manager.sendSetRequest(targetVB);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
                return true;
            default:
                return false;
        }
    }
}
