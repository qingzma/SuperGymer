package com.learn.qingzhi.supergymer;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.learn.qingzhi.supergymer.Main_workflow.EquipmentActivity;
import com.learn.qingzhi.supergymer.Main_workflow.PartActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by wangxi on 08/03/2017.
 */

public class Scanner extends AppCompatActivity {
    private static int REQUEST_ENABLE_BT = 2;
    private static final String TAG = "Scanner";
    BluetoothAdapter bluetoothAdapter = null;
    ArrayList<BluetoothDevice> arrayListPairedBluetoothDevices;
    ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();
    Set<BluetoothDevice> pairedDevices = null;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        PulsatorLayout pulsator = (PulsatorLayout) findViewById(R.id.pulsator);
        pulsator.start();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //enableorDisableBT()
        enableBT();
        //enableDiscoverable();
        getPairedDevices();
        mBTDevices.clear();
        discovery();
        Log.d(TAG,"FINISH DISCOVERING");
        if(mBTDevices.size()!= 0){
            for(int i = 0;i<mBTDevices.size();i++){
                if(mBTDevices.get(i).getAddress().equals("FC:2C:0F:75:4F:1A")){
                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.scannerRelativeLayout);
                    ImageButton dumbbell = new ImageButton(this);
                    dumbbell.setImageResource(R.drawable.dumbbell);
                    dumbbell.setMaxWidth(25);
                    dumbbell.setMaxHeight(25);
                    relativeLayout.addView(dumbbell);
                    bluetoothAdapter.cancelDiscovery();
                }
            }
        }


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home:
                                Intent intent2 =new Intent();
                                intent2.setClass(Scanner.this,EquipmentActivity.class);
                                Scanner.this.startActivity(intent2);
                                break;
                            case R.id.menu_scan:
                                break;
                            case R.id.menu_user:
                                Intent intent_user = new Intent(Scanner.this,UserHistory.class);
                                //intent_user.putExtra("item",1);
                                intent_user.setClass(Scanner.this,UserHistory.class);
                                Scanner.this.startActivity(intent_user);
                                //Scanner.this.finish();
                                break;
                        }
                        return true;
                    }
                });
    }
    public void enableBT(){
        if(bluetoothAdapter ==  null){
            Log.d(TAG,"Your Device does not support Bluetooth");
        }
        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);
        }

    }
    public void disableBT(){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.disable();

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);
        }
    }

    public void enableDiscoverable(){
        Log.d(TAG,"Making device discoverable for 300 seconds");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);

        IntentFilter intentFilter = new IntentFilter(bluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2,intentFilter);
    }

    //get Paired Devices Information
    public void getPairedDevices(){
        pairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (pairedDevices.size() > 0) {
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                Log.d(TAG,device.getName() + " " + device.getAddress());
            }
        }
    }

    public void discovery(){
        Log.d(TAG,"Discovery: looking for unpaired devices");
        boolean flag = false;
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG,"Discovery:cancel discovery ");
            flag = bluetoothAdapter.startDiscovery();
            if(flag){
                Log.d(TAG,"Start Discovering");
            }
            IntentFilter discoveryDeviceIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3,discoveryDeviceIntent);
        }

        if(!bluetoothAdapter.isDiscovering()){
            flag = bluetoothAdapter.startDiscovery();
            if(flag){
                Log.d(TAG,"Start Discovering Successfully");
            }
            IntentFilter discoveryDeviceIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3,discoveryDeviceIntent);
        }
    }

    private BroadcastReceiver mBroadcastReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
               final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
               switch (state){
                   case BluetoothAdapter.STATE_OFF:
                       Log.d(TAG,"onReceive: STATE OFF");
                       break;
                   case BluetoothAdapter.STATE_TURNING_OFF:
                       Log.d(TAG,"mBroadcastReceiver1: STATE TURNING OFF");
                       break;
                   case BluetoothAdapter.STATE_ON:
                       Log.d(TAG,"mBroadcastReceiver1: STATE ON");
                       break;
                   case BluetoothAdapter.STATE_TURNING_ON:
                       Log.d(TAG,"mBroadcastReceiver1: STATE TURNING ON");
                       break;

               }

            }
        }
    };
    private BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)){
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
                switch (state){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d(TAG,"mBroadcastReceiver2: Discoverability enabled");
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d(TAG,"mBroadcastReceiver2: Discoverability disabled. Able to Receive connection");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d(TAG,"mBroadcastReceiver2: CONNECTING....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d(TAG,"mBroadcastReceiver2: CONNECTED.");
                        break;

                }

            }
        }
    };
    private BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            //Log.d(TAG,"into mBroadcastReceiver3");
            if (action.equalsIgnoreCase( BluetoothDevice.ACTION_FOUND)) {
                    // device found
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                mBTDevices.add(device);
                Log.d("TAG",device.getName() + "\n" + device.getAddress());
                if(device.getAddress().equals("FC:2C:0F:75:4F:1A")){
                    RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.scannerRelativeLayout);
                    ImageButton dumbbell = new ImageButton(context);
                    dumbbell.setImageResource(R.drawable.dumbbell);
                    dumbbell.setMaxWidth(25);
                    dumbbell.setMaxHeight(25);
                    relativeLayout.addView(dumbbell);
                    dumbbell.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, PartActivity.class);
                            intent.putExtra("equipment","Dumbbell");
                            startActivity(intent);
                        }
                    });
                    bluetoothAdapter.cancelDiscovery();
                }

            } else if (action.equalsIgnoreCase(
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                    // discoveryFinished
                Log.d(TAG,"ACTION_DISCOVERY_FINISHED");
            } else if (action.equalsIgnoreCase(
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED)) {
                    // discoveryStarted
                Log.d(TAG,"ACTION_DISCOVERY_STARTED");
            }
        }
    };
    /*
    private void checkBTPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},1001);
            }
        }else{
            Log.d(TAG,"checkBTPermissions: no need to check premission, SDK Version < LOLLIPOP");
        }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
    }


}
