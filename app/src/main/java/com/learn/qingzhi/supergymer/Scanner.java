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

        //IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        //registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        try{
            wait(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        //enableorDisableBT()
        enableDisableBT();
        try{
            wait(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        enableDiscoverable();
        try{
            wait(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        discovery();


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
                                Scanner.this.finish();
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
                                Scanner.this.finish();
                                break;
                        }
                        return true;
                    }
                });
    }
    public void enableDisableBT(){
        if(bluetoothAdapter ==  null){
            Log.d(TAG,"Your Device does not support Bluetooth");
        }
        if(!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

            IntentFilter BTIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(mBroadcastReceiver1,BTIntent);
        }
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

    public void discovery(){
        Log.d(TAG,"Discovery: looking for unpaired devices");
        if(bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG,"Discovery:cancel discovery ");

            bluetoothAdapter.startDiscovery();
            IntentFilter discoveryDeviceIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver3,discoveryDeviceIntent);
        }

        if(!bluetoothAdapter.isDiscovering()){
            bluetoothAdapter.startDiscovery();
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
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d(TAG,"OnReceive: " + device.getName()+": "+device.getAddress());
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver1);
    }

}
