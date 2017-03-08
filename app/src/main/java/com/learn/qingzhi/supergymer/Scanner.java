package com.learn.qingzhi.supergymer;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.learn.qingzhi.supergymer.Main_workflow.EquipmentActivity;

import java.util.ArrayList;
import java.util.Set;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by wangxi on 08/03/2017.
 */

public class Scanner extends AppCompatActivity {
    private static int REQUEST_ENABLE_BT = 2;
    //private static int TAG = 100;
    private static final String TAG = "Scanner";

    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //arrayListPairedBluetoothDevices = new ArrayList<BluetoothDevice>();
        if (bluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(),"Your Device does not support Bluetooth",Toast.LENGTH_LONG).show();
        }else if(!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        btnDiscovery();
        /*
        bluetoothAdapter.startDiscovery();
        // Register the BroadcastReceiver
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            Toast.makeText(getApplicationContext(),"size: "+ pairedDevices.size(),Toast.LENGTH_LONG).show();
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                if(device.getName() != null && device.getName().equals("Dumbbell")){
                    //Toast.makeText(getApplicationContext(),"Paired Dumbell",Toast.LENGTH_LONG).show();
                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.scannerLayout);
                    FloatingActionButton fab = new FloatingActionButton(getApplicationContext());
                    fab.setImageResource(R.drawable.btdevice_dumbbell);
                    fab.setSize(FloatingActionButton.SIZE_NORMAL);
                    relativeLayout.addView(fab);
                    Toast.makeText(getApplicationContext(),"Dumbbell Founded",Toast.LENGTH_LONG).show();
                }
            }
        }
        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
        */
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Intent home = new Intent(Scanner.this,EquipmentActivity.class);
                                home.putExtra("item",0);
                                startActivity(home);
                                break;
                            case R.id.menu_scan:
                                //Toast.makeText(getApplicationContext(),"menu_scan",Toast.LENGTH_SHORT).show();
                                //Intent scanner = new Intent(Scanner.this,Scanner.class);
                                //startActivity(scanner);
                                break;
                            case R.id.menu_user:
                                Intent intent_user = new Intent(Scanner.this,UserHistory.class);
                                intent_user.putExtra("item",1);
                                startActivity(intent_user);
                                break;
                        }
                        return true;
                    }
                });
    }

    public void btnDiscovery(){
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            //Log.d(TAG,"btnDiscovery:cancel discovery ");

            bluetoothAdapter.startDiscovery();
            IntentFilter discoveryDeviceIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver1,discoveryDeviceIntent);
        }

        if(!bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.startDiscovery();
            IntentFilter discoveryDeviceIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver1,discoveryDeviceIntent);
        }
    }

    private BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG,"OnReceive: " + device.getName()+": "+device.getAddress());
            }
        }
    };

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        //ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>();
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                if(device.getName() != null && device.getName().equals("Kontakt")){
                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.scannerLayout);
                    FloatingActionButton fab = new FloatingActionButton(getApplicationContext());
                    fab.setImageResource(R.drawable.btdevice_dumbbell);
                    fab.setSize(FloatingActionButton.SIZE_NORMAL);
                    relativeLayout.addView(fab);
                    Toast.makeText(getApplicationContext(),"Dumbbell Founded",Toast.LENGTH_LONG).show();
                }
                //Toast.makeText(getApplicationContext(), device.getName(), Toast.LENGTH_SHORT).show();
            }
        }
    };


}
